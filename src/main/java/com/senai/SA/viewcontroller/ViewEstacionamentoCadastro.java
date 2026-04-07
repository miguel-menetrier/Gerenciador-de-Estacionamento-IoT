package com.senai.SA.viewcontroller;

import com.senai.SA.dto.EstacionamentoRequisicaoDto;
import com.senai.SA.infra.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewEstacionamentoCadastro {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/estacionamentocadastro")
    public String cadastrarEstacionamento(Model model){
        model.addAttribute("statusList", Status.values());
        model.addAttribute("estacionamentoDto", new EstacionamentoRequisicaoDto());
        return "estacionamentocadastro";


    }


}
