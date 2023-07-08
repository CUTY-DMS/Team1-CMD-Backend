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
    public void signUp(SignupRequest signupRequest) {
        Optional<User> byUser = userRepository.findByEmail(signupRequest.getUsername());
        if (byUser.isEmpty()) {
            User user = User.builder()
                    .email(signupRequest.getEmail())
                    .username(signupRequest.getUsername())
                    .password(signupRequest.getPassword())
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        }
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

    public UserInfoResponse myPage() {
        User currentUser = userFacade.currentUser();
        return new UserInfoResponse(currentUser);
    }
}
