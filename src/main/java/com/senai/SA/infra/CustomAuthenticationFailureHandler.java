package com.senai.SA.infra;


import com.senai.SA.dto.MensagemErrorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        // 1. Defina a URL de redirecionamento apenas com a flag de erro.
        String redirectUrl = "/login?erro";

        // 2. Crie o DTO de erro, assim como você faz no GlobalExceptionHandler.
        MensagemErrorDto error = new MensagemErrorDto(exception.getMessage());

        // 3. Crie e configure um "FlashMap" para passar o DTO para a próxima página.
        // Isso armazena o "error" na sessão HTTP temporariamente, apenas para a próxima requisição.
        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
        FlashMap flashMap = new FlashMap();

        // A chave "erroDto" é a mesma que seu login.html espera!
        flashMap.put("erroDto", error);

        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        // 4. Redirecione para a URL
        response.sendRedirect(redirectUrl);
    }

}
