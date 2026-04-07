package com.senai.SA.controller;


import com.senai.SA.dto.UsuarioRequisicaoDto;
import com.senai.SA.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public String atualizarUsuario(@Valid @ModelAttribute("usuarioDto") UsuarioRequisicaoDto usuarioDto, @PathVariable Long id) {
        service.atualizarUsuario(usuarioDto, id);
        return "redirect:/usuariolista";
    }


    @PostMapping("/perfil/{id}")
    public String atualizarPerfil(@Valid @ModelAttribute("usuarioDto") UsuarioRequisicaoDto usuarioDto, @PathVariable Long id) {
        service.atualizarUsuario(usuarioDto, id);
        return "redirect:/home";
    }


    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cadastro")
    public String cadastrar(@Valid @ModelAttribute("usuarioDto") UsuarioRequisicaoDto usuarioDto) {
        service.cadastrarUsuario(usuarioDto);
        return "redirect:/login?sucesso=true";

    }

}
