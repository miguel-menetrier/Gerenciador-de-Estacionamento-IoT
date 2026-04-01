package com.senai.SA.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false,name = "USUARIONOME")
    private String nome;

    @Column(nullable = false, unique = true,name = "USUARIOEMAIL")
    private String email;

    @Column(nullable = false,name = "USUARIOSENHA")
    private String senha;

    @Column(nullable = false,name = "USUARIODTNASC")
    private LocalDate dataNascimento;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

}
