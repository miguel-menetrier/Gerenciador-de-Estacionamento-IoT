package com.senai.SA.model;

import com.senai.SA.infra.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "estacionamento")
public class EstacionamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "ESTACIONAMENTONUMERO")
    private String numero;

    @Column(name = "ESTACIONAMENTOSTATUS")
    private Status status;


}
