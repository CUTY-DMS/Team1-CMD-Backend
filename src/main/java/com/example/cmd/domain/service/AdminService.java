package com.example.cmd.domain.service;

import com.example.cmd.domain.controller.dto.request.*;
import com.example.cmd.domain.entity.*;
import com.example.cmd.domain.repository.NotificationRepository;
import com.example.cmd.domain.repository.AdminRepository;
import com.example.cmd.domain.repository.UserRepository;
import com.example.cmd.domain.repository.VerificationCodeRepository;
import com.example.cmd.domain.service.facade.AdminFacade;
import com.example.cmd.domain.service.facade.UserFacade;
import com.example.cmd.global.security.Token;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.cmd.global.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final VerificationCodeRepository verificationCodeRepository;
    private final JavaMailSender javaMailSender;
    private final NotificationRepository notificationRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminFacade adminFacade;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

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
    // 인증번호 8자리 무작위 생성


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
        if (admin.isPresent()
                && isPasswordMatching(loginRequest.getPassword(), admin.get().getPassword())) {
            TokenResponse token = jwtTokenProvider.createAccessToken(admin.get().getEmail(), admin.get().getRole());
            System.out.println("user.get().getEmail() = " + admin.get().getEmail());
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

    public List<UserListResponse> getStudentList(StudentListRequest studentListRequest) {
        Long grade = studentListRequest.getGradeClass() / 10;
        Long classes = studentListRequest.getGradeClass() % 10;
        List<UserListResponse> users = userRepository.findAllByGradeAndClasses(grade, classes);
        if (users.isEmpty()) {
            throw UserNotFoundException.EXCEPTION;
        }

        return users;
    }


    @Transactional
    public Admin adminInfoChange(AdminInfoChangeRequest adminInfoChangeRequest) {
        Admin currentAdmin = adminFacade.getCurrentAdmin();

        if (isPasswordMatching(adminInfoChangeRequest.getPassword(), currentAdmin.getPassword())) {
            throw PasswordMismatch.EXCEPTION;
        }

        currentAdmin.modifyAdminInfo(
                adminInfoChangeRequest.getName(),
                adminInfoChangeRequest.getBirth(),
                adminInfoChangeRequest.getTeachClass(),
                adminInfoChangeRequest.getTeachGrade());
        return currentAdmin;

    }

    @Transactional
    public void passwordChange(PasswordChangeRequest passwordChangeRequest) {

        Admin currentAdmin = adminFacade.getCurrentAdmin();
        if (isPasswordMatching(passwordChangeRequest.getOldPassword(), currentAdmin.getPassword())) {
            throw PasswordMismatch.EXCEPTION;
        }
        if (!Objects.equals(passwordChangeRequest.getNewPassword(), passwordChangeRequest.getReNewPassword())) {
            throw PasswordMismatch.EXCEPTION;
        }
        adminRepository.save(
                Admin.builder()
                        .password(passwordChangeRequest.getNewPassword())
                        .build()
        );
    }

    public Admin adminInfo() {//나중에 어드민인포에 뭐 필요한지 보고 그거만 보내도록 수정할듯?지금은 뭐만 보내는지 몰라서
        return adminFacade.getCurrentAdmin();
    }

    public void sendVerificationCode() {
        Admin currentAdmin = adminFacade.getCurrentAdmin();
        String code = generateVerificationCode();
        sendEmail(currentAdmin.getEmail(), code);

        verificationCodeRepository.save(
                VerificationCode.builder()
                .code(code)
                .admin(currentAdmin)
                .build()
        );



    }

    private void sendEmail(String toAddress, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toAddress);
        message.setSubject("인증 코드");
        message.setText("인증 코드: " + verificationCode);

        // 이메일 전송
        javaMailSender.send(message);
    }

    private static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}