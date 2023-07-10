package com.example.cmd.Initializer;

import com.example.cmd.domain.entity.Admin;
import com.example.cmd.domain.entity.User;
import com.example.cmd.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AdminAccountInitializer implements CommandLineRunner {

    private final UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = User.builder()
                    .email("admin@example.com")
                    .name("admin1")
                    .admin(Admin.ADMIN)
                    .password("admin123")
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
            userRepository.save(admin);

            System.out.println("테스트용 관리자 계정 생성:");
            System.out.println("이메일: admin@example.com");
            System.out.println("비밀번호: admin123");
        }
    }
}