package com.senai.SA.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ReservaRequisicaoDto {


    private LocalTime tempoTotal;

    @NotNull(message = "A reserva tem te ter uma data de inicio")
    private LocalDateTime reservaDatainicio;

    @NotNull(message = "A reserva tem que ter uma data de fim")
    private LocalDateTime reservadataFim;

    private LocalDateTime horariochegada;
    private LocalDateTime horarioSaida;

    @NotBlank(message = "A placa de carro não pode ser nula")
    @Size(min = 7, max = 7, message = "A placa de carro deve conter 7 caracteres")
    private String placaCarro;

    @NotBlank(message = "o nome do carro não pode estar vazio")
    private String nomeCarro;

    @NotBlank(message = "o preço do carro não pode ser nulo")
    private Float precoHora;

    private Float precoTotal;

    @NotNull(message = "A reserva tem que estar vinculada a um estacionamento")
    private int estacionamentoId;

    @NotNull(message = "A reserva tem que estar vinculada a um usuario")
    private int usuarioId;
}
