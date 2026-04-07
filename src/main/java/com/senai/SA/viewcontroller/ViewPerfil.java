package com.senai.SA.viewcontroller;

import com.senai.SA.dto.UsuarioRespostaDto;
import com.senai.SA.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ViewPerfil {
    private final UsuarioService service;


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/perfil")
    public String getUserProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        model.addAttribute("dataAtual", LocalDate.now());
        model.addAttribute("usuarioDto", userDetails);

        return "perfil";
    }

}

