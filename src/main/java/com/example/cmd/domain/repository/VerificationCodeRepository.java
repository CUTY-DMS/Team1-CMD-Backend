package com.example.cmd.domain.repository;

import com.example.cmd.domain.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository< VerificationCode,Long> {


}
