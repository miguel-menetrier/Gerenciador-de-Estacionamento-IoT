package com.senai.SA.dto;


import com.senai.SA.infra.Role;
import com.senai.SA.model.UsuarioModel;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioRespostaDto {

    private Long id;

    private String nome;

    private String email;

    private String senha;

    private LocalDate dataNascimento;

    private Role role;

    public UsuarioRespostaDto(UsuarioModel dados) {
        this.id = dados.getId();
        this.nome = dados.getNome();
        this.email = dados.getEmail();
        this.senha = dados.getSenha();
        this.dataNascimento = dados.getDataNascimento();
    }
}
