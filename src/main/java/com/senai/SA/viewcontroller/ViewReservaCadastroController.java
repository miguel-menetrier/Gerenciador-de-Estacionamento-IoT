package com.senai.SA.viewcontroller;

import com.senai.SA.dto.ReservaRequisicaoDto;
import com.senai.SA.service.EstacionamentoService;
import com.senai.SA.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewReservaCadastroController {
    private final UsuarioService usuarioService;
    private final EstacionamentoService estacionamentoService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reservacadastro")
    public String cadastrarReserva(Model model) {
        model.addAttribute("usuarioList",usuarioService.listarUsuarios());
        model.addAttribute("estacionamentoList",estacionamentoService.listarEstacionamentos());
        model.addAttribute("reservaDto", new ReservaRequisicaoDto());
        return "reservacadastro";

    }


}
