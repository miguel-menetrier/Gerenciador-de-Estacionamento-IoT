package com.senai.SA.dto;

import com.senai.SA.model.Reservamodel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ReservaRequisicaoDto {


    private LocalTime tempoTotal;

    @NotNull(message = "A reserva tem te ter uma data de inicio")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate reservaDatainicio;

    @NotNull(message = "A reserva tem que ter uma data de fim")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate reservadataFim;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime horariochegada;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime horarioSaida;

    @NotBlank(message = "A placa de carro não pode ser nula")
    @Size(min = 7, max = 7, message = "A placa de carro deve conter 7 caracteres")
    private String placaCarro;

    @NotBlank(message = "o nome do carro não pode estar vazio")
    private String nomeCarro;

    @NotBlank(message = "o preço do carro não pode ser nulo")
    private Float precoHora;

    private Float precoTotal;

    @NotNull(message = "A reserva tem que estar vinculada a um estacionamento")
    private Integer estacionamentoId;

    @NotNull(message = "A reserva tem que estar vinculada a um usuario")
    private Integer usuarioId;


}
