package com.senai.SA.service;

import com.senai.SA.repository.EstacionamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class EstacionamentoLiveService {
    private final EstacionamentoRepository repository;
    private final List<SseEmitter> clients = new CopyOnWriteArrayList<>();
    private final Map<Integer, String> lastStatus = new ConcurrentHashMap<>();

    public SseEmitter connect() {
        SseEmitter emitter = new SseEmitter(0L);
        clients.add(emitter);
        emitter.onCompletion(() -> clients.remove(emitter));
        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onError(error -> clients.remove(emitter));
        return emitter;
    }

    public void publish(int id, String status) {
        for (SseEmitter client : clients) {
            try {
                client.send(SseEmitter.event()
                        .name("spot-update")
                        .data(Map.of("id", id, "status", status)));
            } catch (Exception e) {
                clients.remove(client);
            }
        }
    }

    @Scheduled(fixedDelay = 2000)
    public void watchDatabase() {
        repository.findAll().forEach(spot -> {
            String status = spot.getStatus().toString();
            String old = lastStatus.put(spot.getId(), status);
            if (old != null && !old.equals(status)) {
                publish(spot.getId(), status);
            }
        });
    }
}
