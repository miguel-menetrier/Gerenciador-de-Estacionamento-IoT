package com.senai.SA.controller;

import com.senai.SA.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService service;

    @DeleteMapping("/reserva/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable int id) {
        service.deletarReserva(id);
        return ResponseEntity.noContent().build();
    }

}
