package com.senai.SA.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioRequisicaoDto {

    @Size(message = "O campo nome só pode ter no minimo 10 caracteres e no máximo 100", min = 10, max = 100)
    @NotBlank(message = "O campo nome é obrigatorio")
    private String nome;

    @Email(message = "Digite um email válido")
    @Size(message = "O campo email só pode ter no minimo 10 caracteres e no máximo 100", min = 10, max = 100)
    @NotBlank(message = "O campo do email é obrigatório")
    private String email;

    @Pattern(regexp = "^$|^(?=.*[a-zA-Z])(?=.*\\d).{5,30}$", message = "A senha, se fornecida, deve conter letras, números e ter entre 5 e 30 caracteres")
    private String senha;

    @NotNull(message = "O campo da data de nascimento é obrigatório")
    @PastOrPresent(message = "Data de nascimento não pode ser no futuro")
    private LocalDate dataNascimento;
}
