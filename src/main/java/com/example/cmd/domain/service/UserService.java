package com.example.cmd.domain.service;

import com.example.cmd.domain.controller.dto.request.LoginRequest;
import com.example.cmd.domain.controller.dto.request.UserSignupRequest;
import com.example.cmd.domain.controller.dto.response.UserInfoResponse;
import com.example.cmd.domain.entity.PasswordConverter;
import com.example.cmd.domain.entity.Role;
import com.example.cmd.domain.entity.User;
import com.example.cmd.domain.repository.UserRepository;
import com.example.cmd.domain.service.facade.UserFacade;
import com.example.cmd.global.security.Token;
import com.example.cmd.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordConverter passwordConverter;
    private final UserFacade userFacade;

    @Transactional
    public void userSignUp(UserSignupRequest signupRequest) {
        System.out.println("signupRequest = " + signupRequest);
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            System.out.println("중복");
            throw new UsernameNotFoundException("이미 존재하는 이메일입니다.");
        }
        System.out.println("signupRequest.getUsername() = " + signupRequest.getName());
        userRepository.save(
                User.builder()
                        .email(signupRequest.getEmail())
                        .name(signupRequest.getName())
                        .password(signupRequest.getPassword())
                        .majorField(signupRequest.getMajorField())
                        .birth(signupRequest.getBirth())
                        .classIdNumber(signupRequest.getClassIdNumber())
                        .clubName(signupRequest.getClubName())
                        .role(Role.ROLE_USER)
                        .build()
        );
    }

    @Transactional
    public Token userLogin(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent()
                && isPasswordMatching(loginRequest.getPassword(), user.get().getPassword())) {
            Token token = jwtTokenProvider.createToken(user.get().getEmail(), user.get().getRole());
            System.out.println("user.get().getEmail() = " + user.get().getEmail());
            System.out.println("login success");
            return token;
        } else {
            throw new UsernameNotFoundException("로그인에 실패하였습니다.");
        }
    }



    private boolean isPasswordMatching(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

/*    public List<UserInfoResponse> myPage() {

        User currentUser = userFacade.getCurrentUser();

        return userRepository.findByEmail(currentUser.getEmail())
                .stream()
                .map(UserInfoResponse::new)
                .collect(Collectors.toList());
    }*/
    public UserInfoResponse myPage() {
        User currentUser = userFacade.getCurrentUser();
        Optional<User> userList = userRepository.findByEmail(currentUser.getEmail());
        if (userList.isEmpty()) {
            // 예외 처리 또는 null 등의 처리를 수행해야 합니다.
            return null; // 또는 원하는 방식으로 예외 처리
        }
        User user = userList.get(); // 첫 번째 결과를 사용하거나, 적절한 방식으로 결과를 선택하세요.
        return new UserInfoResponse(user);
    }

    @Transactional
    public void modifyUserInfo(UserInfoRequest userInfoRequest) {

        User currentUser = userFacade.getCurrentUser();
        Optional<User> userList = userRepository.findByEmail(currentUser.getEmail());
        if (userList.isEmpty()) {
            throw new RuntimeException("Email Not Found");
        }

        User user = userList.get();


        String name = userInfoRequest.getName();
        Long birth = userInfoRequest.getBirth();
        Long classIdNumber = userInfoRequest.getClassIdNumber();
        String majorField = userInfoRequest.getMajorField();
        String clubName = userInfoRequest.getClubName();

        user.modifyUserInfo(name, birth, classIdNumber, majorField, clubName);
        userRepository.save(user);
    }

}
