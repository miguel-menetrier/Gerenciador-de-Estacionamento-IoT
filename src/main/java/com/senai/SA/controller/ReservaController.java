package com.senai.SA.controller;

import com.senai.SA.dto.ReservaRequisicaoDto;
import com.senai.SA.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService service;

    @PostMapping("/reserva")
    public String cadastrarReserva(ReservaRequisicaoDto dto) {
        service.cadastrarReserva(dto);
        return "redirect:/reservalista";
    }

    @PostMapping("/reserva/{id}")
    public String atualizarReserva(@PathVariable int id, ReservaRequisicaoDto dto) {
        service.atualizarReserva(id, dto);
        return "redirect:/reservalista";
    }

    @DeleteMapping("/reserva/{id}")
    public ResponseEntity<Void> deletarReserva(@PathVariable int id) {
        service.deletarReserva(id);
        return ResponseEntity.noContent().build();
    }

}
