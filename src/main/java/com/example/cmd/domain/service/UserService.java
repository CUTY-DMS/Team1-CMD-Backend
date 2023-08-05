package com.example.cmd.domain.service;

import com.example.cmd.domain.controller.dto.request.*;
import com.example.cmd.domain.controller.dto.response.NotificationListResponse;
import com.example.cmd.domain.controller.dto.response.ScheduleResponse;
import com.example.cmd.domain.controller.dto.response.UserInfoResponse;
import com.example.cmd.domain.entity.*;
import com.example.cmd.domain.repository.NotificationRepository;
import com.example.cmd.domain.repository.ScheduleRepository;
import com.example.cmd.domain.repository.UserRepository;
import com.example.cmd.domain.service.exception.admin.PasswordMismatch;
import com.example.cmd.domain.service.exception.user.EmailAlreadyExistException;
import com.example.cmd.domain.service.exception.user.UserNotFoundException;
import com.example.cmd.domain.service.facade.UserFacade;
import com.example.cmd.domain.controller.dto.response.TokenResponse;
import com.example.cmd.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.cmd.domain.entity.Noti.ALL;
import static com.example.cmd.domain.entity.Noti.CLASS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordConverter passwordConverter;
    private final UserFacade userFacade;
    private final NotificationRepository notificationRepository;
    private final ScheduleRepository scheduleRepository;


    @Transactional
    public void userSignUp(UserSignupRequest signupRequest) {

        System.out.println("signupRequest = " + signupRequest);
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            System.out.println("중복");
            throw EmailAlreadyExistException.EXCEPTION;
        }
        System.out.println("signupRequest.getUsername() = " + signupRequest.getName());
        Long grade = signupRequest.getClassId() / 1000;
        Long classes = (signupRequest.getClassId() / 100) % 10;
        Long number = signupRequest.getClassId() % 100;
        userRepository.save(
                User.builder()
                        .email(signupRequest.getEmail())
                        .name(signupRequest.getName())
                        .password(signupRequest.getPassword())
                        .majorField(signupRequest.getMajorField())
                        .birth(signupRequest.getBirth())
                        .classes(classes)
                        .grade(grade)
                        .number(number)
                        .classId(signupRequest.getClassId())
                        .clubName(signupRequest.getClubName())
                        .role(Role.ROLE_USER)
                        .build()
        );
    }

    @Transactional
    public TokenResponse userLogin(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent()
                && isPasswordMatching(loginRequest.getPassword(), user.get().getPassword())) {
            TokenResponse token = jwtTokenProvider.createToken(user.get().getEmail());
            System.out.println("login success");
            return token;
        } else {
            throw UserNotFoundException.EXCEPTION;
        }
    }


    private boolean isPasswordMatching(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public UserInfoResponse myPage() {
        User currentUser = userFacade.getCurrentUser();
        Optional<User> userList = userRepository.findByEmail(currentUser.getEmail());
        if (userList.isEmpty()) {
            // 예외 처리 또는 null 등의 처리를 수행해야 합니다.
            throw UserNotFoundException.EXCEPTION; // 또는 원하는 방식으로 예외 처리
        }
        User user = userList.get(); // 첫 번째 결과를 사용하거나, 적절한 방식으로 결과를 선택하세요.
        return new UserInfoResponse(user);
    }

    @Transactional
    public void modifyUserInfo(UserInfoRequest userInfoRequest) {
        User currentUser = userFacade.getCurrentUser();

        Optional<User> userList = userRepository.findByEmail(currentUser.getEmail());
        if (userList.isEmpty()) {
            throw UserNotFoundException.EXCEPTION;
        }

        User user = userList.get();
        String name = userInfoRequest.getName();
        Long birth = userInfoRequest.getBirth();
        Long classId = userInfoRequest.getClassId();
        String majorField = userInfoRequest.getMajorField();
        String clubName = userInfoRequest.getClubName();

        user.modifyUserInfo(name, birth, classId, majorField, clubName);
        userRepository.save(user);
    }

    public List<NotificationListResponse> findNotification() {

        User currentUser = userFacade.getCurrentUser();

        return notificationRepository.findByNoti(ALL)
                .stream()
                .map(notification -> new NotificationListResponse(notification))
                .collect(Collectors.toList());

    }

    public List<NotificationListResponse> findClassNotification() {

        User currentUser = userFacade.getCurrentUser();

        return notificationRepository.findByNotiAndClassesAndGrade(CLASS, currentUser.getClasses(), currentUser.getGrade())
                .stream()
                .map(NotificationListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void passwordChange(PasswordChangeRequest passwordChangeRequest) {

        User currentUser = userFacade.getCurrentUser();
        if (!isPasswordMatching(passwordChangeRequest.getOldPassword(), currentUser.getPassword())) {
            throw PasswordMismatch.EXCEPTION;
        }
        if (!Objects.equals(passwordChangeRequest.getNewPassword(), passwordChangeRequest.getReNewPassword())) {
            throw PasswordMismatch.EXCEPTION;
        }
        currentUser.passwordChange(passwordConverter.encode(passwordChangeRequest.getNewPassword()));

    }


    public List<ScheduleResponse> getSchedule(int year, int month){

        User currentUser = userFacade.getCurrentUser();

        return scheduleRepository.findByMonthAndYearAndGradeAndClasses(month,year, currentUser.getGrade(), currentUser.getClasses())
                .stream()
                .sorted(Comparator.comparing(Schedule::getDay)) // 오름차순 12
                .map(ScheduleResponse::new)
                .collect(Collectors.toList());
    }
}