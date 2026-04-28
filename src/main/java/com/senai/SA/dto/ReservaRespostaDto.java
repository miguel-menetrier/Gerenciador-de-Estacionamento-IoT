package com.senai.SA.dto;


import com.senai.SA.model.Reservamodel;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ReservaRespostaDto {
    private int id;
    private LocalTime tempoTotal;
    private LocalDate reservaDatainicio;
    private LocalDate reservadataFim;
    private LocalTime horariochegada;
    private LocalTime horarioSaida;
    private String placaCarro;
    private String nomeCarro;
    private Float precoHora;
    private Float precoTotal;
    private String estacionamento;
    private String usuario;

    public ReservaRespostaDto() {
    }

    public ReservaRespostaDto(Reservamodel reservamodel) {
        this.id = reservamodel.getId();
        this.reservaDatainicio = reservamodel.getReservaDatainicio();
        this.reservadataFim = reservamodel.getReservadataFim();
        this.horariochegada = reservamodel.getHorariochegada();
        this.horarioSaida = reservamodel.getHorarioSaida();
        this.nomeCarro = reservamodel.getNomeCarro();
        this.placaCarro = reservamodel.getPlacaCarro();
        this.precoHora = reservamodel.getPrecoHora();
        this.precoTotal = reservamodel.getPrecoTotal();
        this.tempoTotal = reservamodel.getTempoTotal();

        if (reservamodel.getUsuarioId() != null) {
            this.usuario = reservamodel.getUsuarioId().getNome();
        }
        if (reservamodel.getEstacionamentoId() != null) {
            this.estacionamento = reservamodel.getEstacionamentoId().getNumero();
        }
    }
}
