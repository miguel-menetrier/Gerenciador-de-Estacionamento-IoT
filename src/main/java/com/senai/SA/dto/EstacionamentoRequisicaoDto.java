package com.senai.SA.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class EstacionamentoRequisicaoDto {

    @Size(message = "O campo do número do estacionamento só pode ter no minimo 10 caracteres e no máximo 100", min = 4, max = 45)
    @NotBlank(message = "O campo nome é obrigatorio")
    private String estacionamentoNumero;

    @Size(message = "O campo nome só pode ter no minimo 3 caracteres e no máximo 100", min = 3, max = 100)
    @NotBlank(message = "O campo status é obrigatorio")
    private String status;

    @NotNull(message = "O preço por hora não pode ser nulo")
    private Float precohora;
}
