package com.example.cmd.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Admin implements UserDetails {

    @Id
    @Column(name = "admin_email")
    private String email;

    @Column(name = "admin_name")
    private String name;

    @Convert(converter = PasswordConverter.class)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private final List<Notification> notificationList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long teachGrade;

    private Long teachClass;

    @Column(name = "admin_birth")
    private Long birth;

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

    public void passwordChange(String password) {
        this.password = password;
    }

    public void modifyAdminInfo(String name, Long birth, Long teachClass, Long teachGrade) { //title과 content 값을 바꿔주려고 이 메소드를 사용함?
        this.name = name;
        this.birth = birth;
        this.teachClass = teachClass;
        this.teachGrade = teachGrade;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    public Admin(Admin admin) {
        this.email = admin.getEmail();
        this.name = admin.getName();
        this.password = admin.getPassword();
        this.role = admin.getRole();
        this.teachGrade = admin.getTeachGrade();
        this.teachClass = admin.getTeachClass();
        this.birth = admin.getBirth();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
