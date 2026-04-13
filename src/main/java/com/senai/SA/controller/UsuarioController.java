package com.senai.SA.controller;


import com.senai.SA.dto.UsuarioRequisicaoDto;
import com.senai.SA.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UsuarioController {

    private final UsuarioService service;


    @PostMapping("/usuario")
    public String cadastrarUsuario(@Valid @ModelAttribute("usuarioDto") UsuarioRequisicaoDto usuarioDto) {
        service.cadastrarUsuario(usuarioDto);
        return "redirect:/usuariolista";

    }

    @PostMapping("/usuario/{id}")
    public String atualizarUsuario(@Valid @ModelAttribute("usuarioDto") UsuarioRequisicaoDto usuarioDto, @PathVariable int id) {
        service.atualizarUsuario(usuarioDto, id);
        return "redirect:/usuariolista";
    }


    @PostMapping("/perfil/{id}")
    public String atualizarPerfil(@Valid @ModelAttribute("usuarioDto") UsuarioRequisicaoDto usuarioDto, @PathVariable int id, HttpServletRequest request,
                                  HttpServletResponse response) {
        service.atualizarUsuario(usuarioDto, id);

        //Perform logout
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?perfilatualizado";
    }


    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable int id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cadastro")
    public String cadastrar(@Valid @ModelAttribute("usuarioDto") UsuarioRequisicaoDto usuarioDto) {
        service.cadastrarUsuario(usuarioDto);
        return "redirect:/login?sucesso=true";

    }

}
