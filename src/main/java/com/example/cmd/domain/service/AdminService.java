package com.example.cmd.domain.service;

import com.example.cmd.domain.controller.dto.request.*;
import com.example.cmd.domain.controller.dto.response.*;
import com.example.cmd.domain.entity.*;
import com.example.cmd.domain.repository.NotificationRepository;
import com.example.cmd.domain.repository.AdminRepository;
import com.example.cmd.domain.repository.ScheduleRepository;
import com.example.cmd.domain.repository.UserRepository;
import com.example.cmd.domain.service.exception.admin.AdminNotFoundException;
import com.example.cmd.domain.service.exception.admin.CodeMismatchException;
import com.example.cmd.domain.service.exception.admin.PasswordMismatch;
import com.example.cmd.domain.service.exception.notification.NotificationNotFoundException;
import com.example.cmd.domain.service.exception.user.EmailAlreadyExistException;
import com.example.cmd.domain.service.exception.user.UserNotFoundException;
import com.example.cmd.domain.service.facade.AdminFacade;
import com.example.cmd.global.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.cmd.domain.entity.Noti.CLASS;
import static com.example.cmd.domain.entity.Noti.TEACHER;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final NotificationRepository notificationRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminFacade adminFacade;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordConverter passwordConverter;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void write(NotificationWriteRequest notificationWriteRequest) {
        Admin currentAdmin = adminFacade.getCurrentAdmin();

        Optional<Admin> userOptional = adminRepository.findByEmail(currentAdmin.getEmail());
        if (userOptional.isPresent()) {
            Admin admin = userOptional.get();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
            String formattedDateTime = now.format(formatter);
            if (notificationRepository.findByAdminAndDateTime(admin, formattedDateTime).isPresent()) {
                System.out.println("fast");
                throw new InputMismatchException("Too fast click");
            }
            notificationRepository.save(
                    Notification.builder()
                            .title(notificationWriteRequest.getTitle())
                            .contents(notificationWriteRequest.getContents())
                            .admin(admin)
                            .classes(notificationWriteRequest.getClasses())
                            .grade(notificationWriteRequest.getGrade())
                            .dateTime(formattedDateTime)
                            .noti(notificationWriteRequest.getNoti())
                            .build()
            );
        } else {
            throw new NoSuchElementException("사용자를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void delete(NotificationDeleteRequest notificationDeleteRequest) {
        Admin currentAdmin = adminFacade.getCurrentAdmin();
        Optional<Notification> optionalNotification = notificationRepository.findByAdminAndDateTime(currentAdmin, notificationDeleteRequest.getDateTime());
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notificationRepository.deleteById(notification.getId());

        } else {
            throw NotificationNotFoundException.EXCEPTION;
        }
    }

    @Transactional
    public void fix(NotificationFixRequest notificationFixRequest) {
        Admin currentAdmin = adminFacade.getCurrentAdmin();
        Optional<Notification> optionalNotification = notificationRepository.findByAdminAndDateTime(currentAdmin, notificationFixRequest.getDateTime());
        if (optionalNotification.isPresent()) {
            Notification updatedNotification = Notification.builder()
                    .id(optionalNotification.get().getId())
                    .title(notificationFixRequest.getTitle())
                    .contents(notificationFixRequest.getContents())
                    .admin(optionalNotification.get().getAdmin())
                    .dateTime(optionalNotification.get().getDateTime())
                    .build();

            notificationRepository.save(updatedNotification);
            // 저장

        }
    }

    @Transactional
    public void adminSignup(AdminSignupRequest adminSignupRequest) {
        System.out.println("signupRequest = " + adminSignupRequest);
        if (adminRepository.existsByEmail(adminSignupRequest.getEmail())) {
            System.out.println("중복");
            throw EmailAlreadyExistException.EXCEPTION;
        }
        if (!Objects.equals(adminSignupRequest.getCode(), "abcd1234")) {
            throw CodeMismatchException.EXCEPTION;
        }
        System.out.println("signupRequest.getUsername() = " + adminSignupRequest.getName());
        adminRepository.save(
                Admin.builder()
                        .email(adminSignupRequest.getEmail())
                        .name(adminSignupRequest.getName())
                        .birth(adminSignupRequest.getBirth())
                        .teachClass(adminSignupRequest.getTeachClass())
                        .teachGrade(adminSignupRequest.getTeachGrade())
                        .password(adminSignupRequest.getPassword())
                        .role(Role.ROLE_ADMIN)
                        .build()
        );

    }

    @Transactional
    public TokenResponse adminLogin(LoginRequest loginRequest) {
        Optional<Admin> admin = adminRepository.findByEmail(loginRequest.getEmail());
        System.out.println("found Email");
        if (admin.isPresent()
                && isPasswordMatching(loginRequest.getPassword(), admin.get().getPassword())) {
            TokenResponse token = jwtTokenProvider.createToken(admin.get().getEmail());
            System.out.println("login success");
            return token;
        } else {
            throw AdminNotFoundException.EXCEPTION;
        }
    }

    private boolean isPasswordMatching(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<UserListResponse> getStudentList() {

        Admin currentAdmin = adminFacade.getCurrentAdmin();

        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getClassId)) // 학번에 따라 정렬
                .map(UserListResponse::new)
                .collect(Collectors.toList());
    }

    public UserInfoResponse student(String userEmail){
        Admin currentAdmin = adminFacade.getCurrentAdmin();

       User user =   userRepository.findByEmail(userEmail)
                .orElseThrow(()->UserNotFoundException.EXCEPTION);

        return new UserInfoResponse(user);
    }

    @Transactional
    public void adminInfoChange(AdminInfoChangeRequest adminInfoChangeRequest) {
        Admin currentAdmin = adminFacade.getCurrentAdmin();

        Optional<Admin> adminList = adminRepository.findByEmail(currentAdmin.getEmail());
        if (adminList.isEmpty()) {
            throw AdminNotFoundException.EXCEPTION;
        }

        Admin admin = adminList.get();

        String name = adminInfoChangeRequest.getName();
        Long birth = adminInfoChangeRequest.getBirth();
        Long teachClass = adminInfoChangeRequest.getTeachClass();
        Long teachGrade = adminInfoChangeRequest.getTeachGrade();


        admin.modifyAdminInfo(name, birth, teachClass, teachGrade);
        adminRepository.save(admin);
    }
@Transactional
    public void passwordChange(PasswordChangeRequest passwordChangeRequest) {

        Admin currentAdmin = adminFacade.getCurrentAdmin();
        if (!isPasswordMatching(passwordChangeRequest.getOldPassword(), currentAdmin.getPassword())) {
            throw PasswordMismatch.EXCEPTION;
        }
        if (!Objects.equals(passwordChangeRequest.getNewPassword(), passwordChangeRequest.getReNewPassword())) {
            throw PasswordMismatch.EXCEPTION;
        }

    currentAdmin.passwordChange(passwordConverter.encode(passwordChangeRequest.getNewPassword()));

    }

    public Admin adminInfo() {//나중에 어드민인포에 뭐 필요한지 보고 그거만 보내도록 수정할듯?지금은 뭐만 보내는지 몰라서
        return adminFacade.getCurrentAdmin();
    }

    public void findPassword(String email){
        Admin currentAdmin = adminFacade.getCurrentAdmin();
    Optional<Admin> admin = adminRepository.findByEmail(email);

    }

    public List<NotificationListResponse> getNotification() {

        Admin currentAdmin = adminFacade.getCurrentAdmin();

        return notificationRepository.findAll()
                .stream()
                .map(notification -> new NotificationListResponse(notification))
                .collect(Collectors.toList());

    }

    public List<NotificationListResponse> getClassNotification() {

        Admin currentAdmin = adminFacade.getCurrentAdmin();

        return notificationRepository.findByNotiAndAdmin_TeachClassAndAdmin_TeachGrade(CLASS, currentAdmin.getTeachClass(), currentAdmin.getTeachGrade())
                .stream()
                .map(NotificationListResponse::new)
                .collect(Collectors.toList());
    }

    public List<NotificationListResponse> findAdminNotification() {

        Admin currentAdmin = adminFacade.getCurrentAdmin();

        return notificationRepository.findByNoti(TEACHER)
                .stream()
                .map(NotificationListResponse::new)
                .collect(Collectors.toList());
    }

    public void writeSchedule(ScheduleWriteRequest scheduleWriteRequest) {

        Admin currentAdmin = adminFacade.getCurrentAdmin();

        Schedule schedule = scheduleRepository.save(
                Schedule.builder()
                        .title(scheduleWriteRequest.getTitle())
                        .date(scheduleWriteRequest.getDate())
                        .build()
        );
    }

    public void deleteSchedule(Long scheduleId) {

        Admin currentAdmin = adminFacade.getCurrentAdmin();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> UserNotFoundException.EXCEPTION);

        scheduleRepository.deleteById(scheduleId);
    }

    public List<ScheduleResponse> getSchedule(){

        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponse::new)
                .collect(Collectors.toList());
    }


}