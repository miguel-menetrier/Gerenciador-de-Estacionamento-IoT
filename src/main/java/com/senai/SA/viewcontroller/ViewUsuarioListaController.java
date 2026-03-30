package com.senai.SA.viewcontroller;


import com.senai.SA.dto.UsuarioRespostaDto;
import com.senai.SA.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewUsuarioListaController {
    private final UsuarioService service;

    @GetMapping("/usuariolista")
    public String viewUsuarioLista(Model model) {

        List<UsuarioRespostaDto> listaDto = service.listarUsuarios();
        model.addAttribute("listaDto", listaDto);

        return "usuariolista";
    }
}
