package com.senai.SA.controller;

import com.senai.SA.service.EstacionamentoLiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class EstacionamentoLiveController {
    private final EstacionamentoLiveService liveService;

    @GetMapping("/estacionamentos/live")
    public SseEmitter live() {
        return liveService.connect();
    }
}
