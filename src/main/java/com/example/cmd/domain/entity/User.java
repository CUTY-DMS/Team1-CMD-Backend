package com.example.cmd.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @Column(name = "user_email")
    private String email;

    @Column(name = "user_name")
    private String name;

    @Convert(converter = PasswordConverter.class)
    @Column(name = "user_password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long grade;

    private Long classes;

    private Long number;
    @Column(name = "user_birth")
    private Long birth;

    private String majorField;

    private String clubName;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void modifyUserInfo(String name, Long birth, Long grade, String majorField, String clubName) { //title과 content 값을 바꿔주려고 이 메소드를 사용함?
        this.name = name;
        this.birth = birth;
        this.grade = grade;
        this.majorField = majorField;
        this.clubName = clubName;
    }

    public User(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.grade = user.getGrade();
        this.classes = user.getClasses();
        this.number= user.getNumber();
        this.birth = user.getBirth();
        this.majorField = user.getMajorField();
        this.clubName = user.getClubName();
    }

}
