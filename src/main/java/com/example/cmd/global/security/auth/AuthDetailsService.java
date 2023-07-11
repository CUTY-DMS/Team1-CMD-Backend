package com.example.cmd.global.security.auth;


import com.example.cmd.domain.entity.Admin;
import com.example.cmd.domain.entity.User;
import com.example.cmd.domain.repository.AdminRepository;
import com.example.cmd.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (adminRepository.existsByEmail(email)) {
            // 관리자 계정인 경우 Admin 객체 생성
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("관리자를 찾을 수 없습니다."));
            return new Admin(admin);
        } else {
            // 일반 사용자 계정인 경우 User 객체 생성
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            return new User(user);
        }
    }
}
