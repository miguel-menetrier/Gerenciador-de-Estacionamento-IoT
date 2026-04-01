package com.senai.SA.viewcontroller;



import com.senai.SA.dto.EstacionamentoDto;
import com.senai.SA.service.EstacionamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewEstacionamentoListaController {
    private final EstacionamentoService service;

    @GetMapping("/estacionamentolista")
    public String viewUsuarioLista(Model model) {

        List<EstacionamentoDto> listaDto = service.listarEstacionamentos();
        model.addAttribute("listaDto", listaDto);

        return "usuariolista";
    }
}
