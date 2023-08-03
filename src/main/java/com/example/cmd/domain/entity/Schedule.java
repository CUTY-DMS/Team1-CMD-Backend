package com.example.cmd.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {//제목, 내용, 날짜, 글쓴이

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String date;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "admin_email")
    private Admin admin;
}
