package com.example.cmd.domain.service.facade;

import com.example.cmd.domain.entity.Admin;
import com.example.cmd.domain.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminFacade {

    private final AdminRepository adminRepository;

    public Admin getCurrentAdmin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("usernameeda = " + email);
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }
}
