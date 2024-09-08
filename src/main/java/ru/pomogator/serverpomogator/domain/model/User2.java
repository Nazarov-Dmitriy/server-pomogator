//package ru.pomogator.serverpomogator.domain.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.ColumnDefault;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@Data
//@Builder
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "USERS")
//public class User2 implements UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false,  unique = true)
//    private String login;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "role", nullable = false)
//    private Role role;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String surname;
//
//    @Column(nullable = false)
//    private String patronymic;
//
//    @Column()
//    @ColumnDefault("null")
//    private Integer certificate;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public String getUsername() {
//        return this.login;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
//
