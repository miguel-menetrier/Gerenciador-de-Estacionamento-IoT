package com.senai.SA.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "reservas")
public class Reservamodel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "RESERVAHORASTOTAL")
    private LocalTime tempoTotal;

    @Column(name = "RESERVADATAINICIO")
    private LocalDateTime reservaDatainicio;
    @Column(name = "RESERVASDATAFIM")
    private LocalDateTime reservadataFim;

    @Column(name = "RESERVAHORARIOCHEGADA")
    private LocalDateTime horariochegada;

    @Column(name = "RESERVAHORARIOSAIDA")
    private LocalDateTime horarioSaida;

    @Column(name = "RESERVAPLACACARRO")
    private String placaCarro;
    @Column(name = "RESERVANOMECARRO")
    private String nomeCarro;
    @Column(name = "RESERVAPRECOPORHORA")
    private Float precoHora;
    @Column(name = "RESERVAPRECOTOTAL")
    private Float precoTotal;

    @ManyToOne
    @JoinColumn(name = "Estacionamento_ID")
    private EstacionamentoModel estacionamentoId;

    @ManyToOne
    @JoinColumn(name = "Usuario_ID")
    private UsuarioModel usuarioId;

}
