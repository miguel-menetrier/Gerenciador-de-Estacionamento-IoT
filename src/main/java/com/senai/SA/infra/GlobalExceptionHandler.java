package com.senai.SA.infra;

import com.senai.SA.dto.MensagemErrorDto;
import com.senai.SA.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExistEmailException.class)
    private String EmailEmUso(ExistEmailException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        String referer = request.getHeader("Referer");

        if (referer == null) {
            String requestUri = request.getRequestURI();

            if (requestUri.equals("/usuario")) {

                referer = "/usuariocadastro";
            } else if (requestUri.equals("/cadastro")) {
                referer = "/cadastro";
            }

        }

        String redirectURL = referer.contains("?") ? referer + "&erro" : referer + "?erro";

        MensagemErrorDto error = new MensagemErrorDto(ex.getMessage());

        redirectAttributes.addFlashAttribute("erroDto", error);

        return "redirect:" + redirectURL;
    }

    @ExceptionHandler(DataNascimentoInvalidaException.class)
    private String dataInvalida(DataNascimentoInvalidaException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        String referer = request.getHeader("Referer");

        if (referer == null) {
            String requestUri = request.getRequestURI();

            if (requestUri.equals("/usuario")) {

                referer = "/usuariocadastro";
            } else if (requestUri.equals("/cadastro")) {
                referer = "/cadastro";
            }

        }

        String redirectURL = referer.contains("?") ? referer + "&erro" : referer + "?erro";

        MensagemErrorDto error = new MensagemErrorDto(ex.getMessage());

        redirectAttributes.addFlashAttribute("erroDto", error);

        return "redirect:" + redirectURL;
    }

    @ExceptionHandler(NotFoundException.class)
    private String notFound(NotFoundException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        String referer = request.getHeader("Referer");

        String redirectURL = referer.contains("?") ? referer + "&erro" : referer + "?erro";

        MensagemErrorDto error = new MensagemErrorDto(ex.getMessage());

        redirectAttributes.addFlashAttribute("erroDto", error);

        return "redirect:" + redirectURL;
    }
    @ExceptionHandler(HorarioFinalException.class)
    private String HorarioFinalInvalido(HorarioFinalException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        //Ira pegar a url que ele estava antes EX: /cadastro
        String referer = request.getHeader("Referer");
        // Adiciona o parâmetro ?erro ou &erro dependendo da URL
        String redirectURL = referer.contains("?") ? referer + "&erro" : referer + "?erro";

        MensagemErrorDto error = new MensagemErrorDto(ex.getMessage());

        redirectAttributes.addFlashAttribute("erroDto", error);
        return "redirect:" + redirectURL;
    }
    @ExceptionHandler(HorarioInvalido.class)
    private String HorarioInvalido(HorarioInvalido ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        //Ira pegar a url que ele estava antes EX: /cadastro
        String referer = request.getHeader("Referer");

        // Adiciona o parâmetro ?erro ou &erro dependendo da URL
        String redirectURL = referer.contains("?") ? referer + "&erro" : referer + "?erro";

        MensagemErrorDto error = new MensagemErrorDto(ex.getMessage());

        redirectAttributes.addFlashAttribute("erroDto", error);
        return "redirect:" + redirectURL;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex,
                                             RedirectAttributes redirectAttributes,
                                             HttpServletRequest request) {

        // 1. Pega a URL de onde o usuário veio (ex: /usuariocadastro)
        String referer = request.getHeader("Referer");
        if (referer == null) {
            referer = "/cadastro";
        }
        String redirectURL = referer.contains("?") ? referer + "&erro" : referer + "?erro";

        // 2. Extrai TODOS os erros de validação em uma lista de strings
        List<String> validationMessages = ex.getBindingResult()
                .getFieldErrors() // Pega os erros de cada campo
                .stream()
                .map(FieldError::getDefaultMessage) // Extrai a mensagem (ex: "O campo nome é obrigatorio")
                .collect(Collectors.toList());

        // 3. Adiciona a lista de erros como Flash Attribute
        redirectAttributes.addFlashAttribute("validationErrors", validationMessages);


        if (!validationMessages.isEmpty()) {

            MensagemErrorDto error = new MensagemErrorDto(validationMessages.get(0));

            redirectAttributes.addFlashAttribute("erroDto", error);
        }

        return "redirect:" + redirectURL;
    }


}
