package com.senai.SA.viewcontroller;

import com.senai.SA.dto.EstacionamentoRespostaDto;
import com.senai.SA.dto.UsuarioRespostaDto;
import com.senai.SA.infra.Status;
import com.senai.SA.service.EstacionamentoService;
import com.senai.SA.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ViewEstacionamentoAtualizarController {

    private final  EstacionamentoService estacionamentoService;

    @GetMapping("/estacionamentoatualizar/{id}")
    public String viewAtualizar(@PathVariable("id") int id, Model model) {
        EstacionamentoRespostaDto estacionamentoDto = estacionamentoService.estacionamentobyId(id);

        model.addAttribute("dataAtual", LocalDate.now());
        model.addAttribute("statusList", Status.values());
        model.addAttribute("estacionamentoDto", estacionamentoDto);

        return "estacionamentoatualizar";
    }
}
