package com.senai.SA.controller;

import com.senai.SA.dto.EstacionamentoRequisicaoDto;
import com.senai.SA.service.EstacionamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EstacionamentoController {
    private final EstacionamentoService estacionamentoService;

    @PostMapping("/estacionamento")
    public String cadastrarEstacionamento(@Valid @ModelAttribute EstacionamentoRequisicaoDto estacionamentoDto){

        estacionamentoService.cadastrarEstacionamento(estacionamentoDto);
        return "redirect:/estacionamentolista";
    }
    @PostMapping("/estacionamento/{id}")
    public String atualizarEstacionamento(@Valid @ModelAttribute EstacionamentoRequisicaoDto estacionamentoDto,
                                          @PathVariable("id") int id){

        estacionamentoService.estacionamentoAtualizar(id,estacionamentoDto);
        return "redirect:/estacionamentolista";
    }

    @DeleteMapping("/estacionamento/{id}")
    public ResponseEntity<Void> deletarEstacionamento(@PathVariable("id") int estacionamentoid){
        estacionamentoService.deletarEstacionamento(estacionamentoid);
        return ResponseEntity.noContent().build();
    }
}
