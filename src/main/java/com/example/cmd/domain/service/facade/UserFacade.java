package com.example.cmd.domain.service.facade;

import com.example.cmd.domain.entity.Admin;
import com.example.cmd.domain.entity.User;
import com.example.cmd.domain.repository.AdminRepository;
import com.example.cmd.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserRepository userRepository;
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("email = " + email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }


}
