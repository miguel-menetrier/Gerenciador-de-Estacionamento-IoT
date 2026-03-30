package com.senai.SA.controller;


import com.senai.SA.dto.LoginDto;
import com.senai.SA.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService service;

    @GetMapping("/login")
    public String viewLogin(Model model) {

        LoginDto loginDto = new LoginDto();

        model.addAttribute("loginDto", loginDto);

        return "login";
    }


}
