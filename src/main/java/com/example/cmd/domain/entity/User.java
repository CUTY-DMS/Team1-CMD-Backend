package com.example.cmd.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;
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
    private String email;

    private String name;

    @Convert(converter = PasswordConverter.class)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private final List<Notification> notificationList = new ArrayList<>();

   /* @Enumerated(EnumType.STRING)
    private Role role;
*/
    private Long classIdNumber;

    private Long birth;

    private String majorField;

    private String clubName;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    // ...



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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

    public User(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.classIdNumber = user.getClassIdNumber();
        this.birth = user.getBirth();
        this.majorField = user.getMajorField();
        this.clubName = user.getClubName();
    }

}
