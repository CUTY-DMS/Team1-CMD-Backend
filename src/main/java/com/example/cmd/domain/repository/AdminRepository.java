package com.example.cmd.domain.repository;

import com.example.cmd.domain.entity.Admin;
import com.example.cmd.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,String> {
    Boolean existsByEmail(String email);
    Optional<Admin> findByName(String name);
    Optional<Admin> findByEmail(String email);
}
