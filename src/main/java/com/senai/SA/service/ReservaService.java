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
        boolean atualizou = false;
        Optional<Reservamodel> reservaModelOptional = repository.findById(id);
        Optional<EstacionamentoModel> estacionamentoModelOptional = estacionamentoRepository.findById(dto.getEstacionamentoId());
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(dto.getUsuarioId());

        if (reservaModelOptional.isPresent()) {
            Reservamodel model = reservaModelOptional.get();

            model.setPrecoHora(dto.getPrecoHora());
            model.setReservaDatainicio(dto.getReservaDatainicio());
            model.setNomeCarro(dto.getNomeCarro());
            model.setPlacaCarro(dto.getPlacaCarro());
            model.setReservadataFim(dto.getReservadataFim());

            if (estacionamentoModelOptional.isPresent()) {
                model.setEstacionamentoId(estacionamentoModelOptional.get());
            }
            if (usuarioModelOptional.isPresent()) {
                model.setUsuarioId(usuarioModelOptional.get());
            }

            model.setHorariochegada(dto.getHorariochegada());
            model.setHorarioSaida(dto.getHorarioSaida());
            model.setPrecoTotal(dto.getPrecoTotal());
            model.setTempoTotal(dto.getTempoTotal());
            repository.save(model);
            atualizou = true;
        }
        return atualizou;
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
