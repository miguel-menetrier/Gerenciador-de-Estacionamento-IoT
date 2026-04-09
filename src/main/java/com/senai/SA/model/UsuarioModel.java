package com.senai.SA.model;

import com.senai.SA.infra.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(nullable = false, name = "USUARIONOME")
    private String nome;

    @Column(nullable = false, unique = true, name = "USUARIOEMAIL")
    private String email;

    @Column(nullable = false, name = "USUARIOSENHA")
    private String senha;

    @Column(nullable = false, name = "USUARIODTNASC")
    private LocalDate dataNascimento;

    // ✅ NEW FIELD
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role;

    // ✅ AUTHORITIES IMPLEMENTATION
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    // Optional (recommended for full compatibility)
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