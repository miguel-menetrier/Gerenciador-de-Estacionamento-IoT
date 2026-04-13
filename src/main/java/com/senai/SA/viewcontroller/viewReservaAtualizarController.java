package com.senai.SA.viewcontroller;


import com.senai.SA.dto.ReservaRequisicaoDto;
import com.senai.SA.dto.ReservaRespostaDto;
import com.senai.SA.dto.UsuarioRespostaDto;
import com.senai.SA.service.ReservaService;
import com.senai.SA.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class viewReservaAtualizarController {


    private final ReservaService service;
    private final UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("reservaatualizar/{id}")
    public String viewReservaAtualziar(@PathVariable("id") int id, Model model) {

        ReservaRespostaDto reservaRespostaDto = service.buscarReservaPorId(id);
        List<UsuarioRespostaDto> usuariolista = usuarioService.listarUsuarios();

        model.addAttribute("usuarioLista", usuariolista);
        model.addAttribute("reservaDto", reservaRespostaDto);

        return "reservaatualizar";
    }
}
