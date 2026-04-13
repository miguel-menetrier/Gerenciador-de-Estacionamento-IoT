package com.senai.SA.viewcontroller;

import com.senai.SA.dto.ReservaRespostaDto;
import com.senai.SA.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewReservaListaController {
    private final ReservaService service;

    @GetMapping("/reservalista")
    public String listReserva(Model model){
        List<ReservaRespostaDto>listaDto = service.listarReservas();
        model.addAttribute("listaDto",listaDto);

        return "reservalista";
    }

}
