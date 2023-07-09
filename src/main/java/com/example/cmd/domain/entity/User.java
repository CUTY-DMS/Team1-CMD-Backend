package com.example.cmd.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;

@Entity
@RequiredArgsConstructor
@Getter
@Builder
@AllArgsConstructor

public class User implements UserDetails {

    @Id
    private String email;

    private String username;

    @Convert(converter = PasswordConverter.class)
    private String password;

    private Role role;

    private Long classIdNumber;
<<<<<<< HEAD

    private Long birth;

    private String majorField;

    private String clubName;


=======

    private Long birth;

    private String majorField;

    private String clubName;

    public User(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.classIdNumber = user.getClassIdNumber();
        this.birth = user.getBirth();
        this.majorField = user.getMajorField();
        this.clubName = user.getClubName();
    }

>>>>>>> main
    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
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

    public User(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.classIdNumber = user.getClassIdNumber();
        this.birth = user.getBirth();
        this.majorField = user.getMajorField();
        this.clubName = user.getClubName();
    }

}
