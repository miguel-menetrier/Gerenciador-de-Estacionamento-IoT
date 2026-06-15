package com.senai.SA.service;

import com.senai.SA.infra.Status;
import com.senai.SA.model.EstacionamentoModel;
import com.senai.SA.repository.EstacionamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaSchedulerService {

    private final EstacionamentoRepository estacionamentoRepository;

    public ReservaSchedulerService(EstacionamentoRepository estacionamentoRepository) {
        this.estacionamentoRepository = estacionamentoRepository;
    }

    // runs every 60 seconds
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateReservations() {

        LocalDateTime now = LocalDateTime.now();

        List<EstacionamentoModel> all = estacionamentoRepository.findAll();

        for (EstacionamentoModel e : all) {

            boolean isReserved = checkIfReserved(e.getId(), now);

            if (isReserved) {
                // HIGHEST PRIORITY
                e.setStatus(Status.Reservado);
            } else {
                // DO NOT blindly force Livre — only release if not occupied by sensor
                if (e.getStatus() == Status.Reservado) {
                    e.setStatus(Status.Livre);
                }
            }

            estacionamentoRepository.save(e);
        }

        System.out.println("Reservation check executed at " + now);
    }

    // ==========================
    // RESERVATION CHECK LOGIC
    // ==========================
    private boolean checkIfReserved(Integer estacionamentoId, LocalDateTime now) {

        Integer result = estacionamentoRepository
                .existsReservationActive(estacionamentoId, now);

        return result != null && result == 1;
    }

}