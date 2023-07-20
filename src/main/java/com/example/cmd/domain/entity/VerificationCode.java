package com.example.cmd.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class VerificationCode {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String code;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "admin_email")
        private Admin admin;
}
