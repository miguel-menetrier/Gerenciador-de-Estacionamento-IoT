package com.senai.SA.viewcontroller;

import com.senai.SA.model.UsuarioModel;
import com.senai.SA.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewHomeController {
    private final UsuarioService usuarioService;


    @GetMapping("/home")
    public String viewHome(Model model, @AuthenticationPrincipal UsuarioModel usuario) {
        model.addAttribute("qtdUsuario", usuarioService.qtdUsuarios());
        model.addAttribute("nomeUsuario", usuario.getNome());
        return "home";
    }
}
