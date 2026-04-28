package com.senai.SA.service;

import com.senai.SA.dto.ReservaRequisicaoDto;
import com.senai.SA.dto.ReservaRespostaDto;
import com.senai.SA.exceptions.NotFoundException;
import com.senai.SA.model.EstacionamentoModel;
import com.senai.SA.model.Reservamodel;
import com.senai.SA.model.UsuarioModel;
import com.senai.SA.repository.EstacionamentoRepository;
import com.senai.SA.repository.ReservaRepository;
import com.senai.SA.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final EstacionamentoRepository estacionamentoRepository;

    public boolean cadastrarReserva(ReservaRequisicaoDto resquisicao) {
        // Busca obrigatória para evitar campos nulos no banco
        EstacionamentoModel estacionamento = estacionamentoRepository.findById(resquisicao.getEstacionamentoId())
                .orElseThrow(() -> new NotFoundException("Estacionamento não encontrado"));
        UsuarioModel usuario = usuarioRepository.findById(resquisicao.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Reservamodel reserva = new Reservamodel();
        reserva.setReservaDatainicio(resquisicao.getReservaDatainicio());
        reserva.setReservadataFim(resquisicao.getReservadataFim());
        reserva.setHorariochegada(resquisicao.getHorariochegada());
        reserva.setHorarioSaida(resquisicao.getHorarioSaida());

        // Pegando o preço da hora diretamente do estacionamento ou do DTO
        reserva.setPrecoHora(estacionamento.getPrecoHora());
        reserva.setNomeCarro(resquisicao.getNomeCarro());
        reserva.setPlacaCarro(resquisicao.getPlacaCarro());
        reserva.setEstacionamentoId(estacionamento);
        reserva.setUsuarioId(usuario);

        // Cálculo automático de tempo e preço
        if (resquisicao.getHorariochegada() != null && resquisicao.getHorarioSaida() != null) {
            java.time.Duration duracao = java.time.Duration.between(resquisicao.getHorariochegada(), resquisicao.getHorarioSaida());
            long horas = duracao.toHours();
            long minutos = duracao.toMinutesPart();
            reserva.setTempoTotal(java.time.LocalTime.of((int) horas, (int) minutos));

            float precoFinal = (horas + (minutos / 60.0f)) * reserva.getPrecoHora();
            reserva.setPrecoTotal(precoFinal);
        } else {
            reserva.setTempoTotal(java.time.LocalTime.of(0, 0));
            reserva.setPrecoTotal(0.0f);
        }

        repository.save(reserva);
        return true;
    }

    public boolean deletarReserva(int id) {
        boolean deletou = false;
        Optional<Reservamodel> reservaModelOptinal = repository.findById(id);
        if (reservaModelOptinal.isPresent()) {
            Reservamodel reservaModel = reservaModelOptinal.get();
            repository.deleteById(reservaModel.getId());
            deletou = true;
        }
        return deletou;
    }

    public List<ReservaRespostaDto> listarReservas() {
        List<ReservaRespostaDto> respostaList = new ArrayList<>();
        List<Reservamodel> listaReservaModel = repository.findAll();

        for (Reservamodel dados : listaReservaModel) {
            // Mova a instância para DENTRO do loop
            ReservaRespostaDto respostaDto = new ReservaRespostaDto();

            respostaDto.setId(dados.getId());
            respostaDto.setPrecoHora(dados.getPrecoHora());
            respostaDto.setReservaDatainicio(dados.getReservaDatainicio());
            respostaDto.setNomeCarro(dados.getNomeCarro());
            respostaDto.setPlacaCarro(dados.getPlacaCarro());
            respostaDto.setReservadataFim(dados.getReservadataFim());

            if (dados.getEstacionamentoId() != null) {
                respostaDto.setEstacionamento(dados.getEstacionamentoId().getNumero());
            }
            if (dados.getUsuarioId() != null) {
                respostaDto.setUsuario(dados.getUsuarioId().getNome());
            }

            respostaDto.setHorariochegada(dados.getHorariochegada());
            respostaDto.setHorarioSaida(dados.getHorarioSaida());
            respostaDto.setPrecoTotal(dados.getPrecoTotal());
            respostaDto.setTempoTotal(dados.getTempoTotal());

            respostaList.add(respostaDto);
        }
        return respostaList;
    }

    public boolean atualizarReserva(int id, ReservaRequisicaoDto dto) {
        // 1. Verifica se o ID da reserva é válido
        if (id == 0) return false;

        Optional<Reservamodel> reservaModelOptional = repository.findById(id);

        if (reservaModelOptional.isPresent()) {
            Reservamodel model = reservaModelOptional.get();

            // 2. Busca as entidades usando os IDs que o formulário enviou (name="estacionamentoId")
            // AQUI ESTAVA O ERRO: Se dto.getEstacionamentoId() for null, o findById estoura.
            if (dto.getEstacionamentoId() == null || dto.getUsuarioId() == null) {
                throw new IllegalArgumentException("ID de usuário ou estacionamento não fornecido.");
            }

            EstacionamentoModel est = estacionamentoRepository.findById(dto.getEstacionamentoId())
                    .orElseThrow(() -> new NotFoundException("Vaga não encontrada"));
            UsuarioModel usu = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

            // 3. Equals lógico: Atualiza as referências
            model.setEstacionamentoId(est);
            model.setUsuarioId(usu);
            model.setPrecoHora(est.getPrecoHora());

            // 4. Atualiza os dados de texto e data
            model.setNomeCarro(dto.getNomeCarro());
            model.setPlacaCarro(dto.getPlacaCarro());
            model.setReservaDatainicio(dto.getReservaDatainicio());
            model.setReservadataFim(dto.getReservadataFim());
            model.setHorariochegada(dto.getHorariochegada());
            model.setHorarioSaida(dto.getHorarioSaida());

            // 5. Recalcula tempo e preço total para o Update ser preciso
            if (dto.getHorariochegada() != null && dto.getHorarioSaida() != null) {
                java.time.Duration duracao = java.time.Duration.between(dto.getHorariochegada(), dto.getHorarioSaida());
                model.setTempoTotal(java.time.LocalTime.of((int)duracao.toHours(), (int)duracao.toMinutesPart()));
                model.setPrecoTotal((duracao.toMinutes() / 60.0f) * model.getPrecoHora());
            }

            repository.save(model);
            return true;
        }
        return false;
    }

    public ReservaRespostaDto buscarReservaPorId(int id) {

        Optional<Reservamodel> reservamodelOptional = repository.findById(id);
        if (reservamodelOptional.isPresent()) {
            return new ReservaRespostaDto(reservamodelOptional.get());
        }
        throw new NotFoundException("Reserva não encontrada!!");
    }


    public long qtdReserva(){
        System.out.println(repository.count());
        return repository.count();
    }


}
