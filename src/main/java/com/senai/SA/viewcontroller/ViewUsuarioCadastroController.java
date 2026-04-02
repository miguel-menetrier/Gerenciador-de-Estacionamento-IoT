package com.senai.SA.viewcontroller;


import com.senai.SA.dto.UsuarioRequisicaoDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class ViewUsuarioCadastroController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/usuariocadastro")
    public String viewCadastrousuario(Model model) {
        model.addAttribute("dataAtual", LocalDate.now());
        model.addAttribute("usuarioDto", new UsuarioRequisicaoDto());
        return "usuariocadastro";
    }

}
