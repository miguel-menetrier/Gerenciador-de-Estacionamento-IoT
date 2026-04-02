package com.senai.SA.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class EstacionamentoRespostaDto {


    private int id;
    private String estacionamentoNumero;
    private String status;
}
