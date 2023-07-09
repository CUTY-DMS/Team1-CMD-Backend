package com.example.cmd.domain.service;

import com.example.cmd.domain.controller.dto.request.LoginRequest;
import com.example.cmd.domain.controller.dto.request.SignupRequest;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordConverter passwordConverter;
    private final UserFacade userFacade;

    @Transactional
    public void signUp(SignupRequest signupRequest) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UsernameNotFoundException("이미 존재하는 이메일입니다.");
        }

        userRepository.save(
                User.builder()
                        .username(signupRequest.getUsername())
                        .email(signupRequest.getEmail())
                        .majorField(signupRequest.getMajorField())
                        .password(signupRequest.getPassword())
                        .birth(signupRequest.getBirth())
                        .classIdNumber(signupRequest.getClassIdNumber())
                        .clubName(signupRequest.getClubName())
                        .role(Role.USER)
                        .build()


        );
    }

    public Token login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent()
                && isPasswordMatching(loginRequest.getPassword(), user.get().getPassword())) {
            Token token = jwtTokenProvider.createToken(user.get().getEmail());
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

    public List<UserInfoResponse> myPage() {

        User currentUser = userFacade.currentUser();

        return userRepository.findAll()
                .stream()
                .map(UserInfoResponse::new)
                .collect(Collectors.toList());
    }

}
