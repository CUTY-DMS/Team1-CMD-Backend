package com.example.cmd.domain.repository;

import com.example.cmd.domain.controller.dto.response.UserListResponse;
import com.example.cmd.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {


    Boolean existsByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);

    List<UserListResponse> findAllByGradeAndClasses(Long grade, Long classes);
}
