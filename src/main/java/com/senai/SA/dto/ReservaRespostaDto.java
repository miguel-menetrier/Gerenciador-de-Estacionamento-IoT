package com.senai.SA.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ReservaRespostaDto {
    private int id;
    private LocalTime tempoTotal;
    private LocalDateTime reservaDatainicio;
    private LocalDateTime reservadataFim;
    private LocalDateTime horariochegada;
    private LocalDateTime horarioSaida;
    private String placaCarro;
    private String nomeCarro;
    private Float precoHora;
    private Float precoTotal;
    private String estacionamento;
    private String usuario;
}
