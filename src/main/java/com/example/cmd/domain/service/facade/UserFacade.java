package com.example.cmd.domain.service.facade;

import com.example.cmd.domain.entity.User;
import com.example.cmd.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserRepository userRepository;

<<<<<<< HEAD

=======
>>>>>>> main
    public User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
<<<<<<< HEAD
                .orElseThrow(() -> new RuntimeException("User not found"));


    }
}

=======
                .orElseThrow();
    }
}
>>>>>>> main
