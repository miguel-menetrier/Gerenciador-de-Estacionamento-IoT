package com.senai.SA.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "O campo de login é obrigatório!!")
    @Email(message = "informe um email válido!!")
    private String username;
    @NotBlank(message = "O campo da senha é obrigatório!!")
    private String password;


}
