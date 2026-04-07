package com.senai.SA.viewcontroller;

import com.senai.SA.dto.UsuarioRespostaDto;
import com.senai.SA.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ViewUsuarioAtualizarController {
        private final UsuarioService service;


        @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/usuarioatualizar/{id}")
        public String viewAtualizar(@PathVariable Long id, Model model) {
            UsuarioRespostaDto usuarioDto = service.buscarUsuarioById(id);

            model.addAttribute("dataAtual", LocalDate.now());
            model.addAttribute("usuarioDto", usuarioDto);
            return "usuarioatualizar";
        }
    }

