package ru.pomogator.serverpomogator.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.pomogator.serverpomogator.domain.model.Role;
import ru.pomogator.serverpomogator.domain.model.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class JwtUser implements UserDetails {
    User user;


//    private final Long id;
//    private String email;
//    private final String password;
//    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
}
