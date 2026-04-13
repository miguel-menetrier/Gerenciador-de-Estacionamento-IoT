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
        boolean cadastrou = false;
        Reservamodel reserva = new Reservamodel();
        Optional<EstacionamentoModel> estacionamentoModelOptional = estacionamentoRepository.findById(resquisicao.getEstacionamentoId());
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(resquisicao.getUsuarioId());

        reserva.setReservaDatainicio(resquisicao.getReservaDatainicio());
        reserva.setReservadataFim(resquisicao.getReservadataFim());

        if (estacionamentoModelOptional.isPresent()) {
            reserva.setEstacionamentoId(estacionamentoModelOptional.get());
        }
        if (usuarioModelOptional.isPresent()) {
            reserva.setUsuarioId(usuarioModelOptional.get());
        }
        reserva.setPrecoHora(resquisicao.getPrecoHora());
        reserva.setNomeCarro(resquisicao.getNomeCarro());
        reserva.setPlacaCarro(resquisicao.getPlacaCarro());

        repository.save(reserva);

        cadastrou = true;
        return cadastrou;
    }

    public boolean deletarReserva(int id) {
        boolean deletou = false;
        Optional<Reservamodel> reservaModelOptinal = repository.findById(id);
        if (reservaModelOptinal.isPresent()) {
            Reservamodel reservaModel = reservaModelOptinal.get();

            UsuarioModel usuarioModel = reservaModel.getUsuarioId();
            EstacionamentoModel estacionamentoModel = reservaModel.getEstacionamentoId();

            usuarioRepository.deleteById(usuarioModel.getId());
            estacionamentoRepository.deleteById(reservaModel.getId());
            repository.deleteById(reservaModel.getId());
            deletou = true;
        }
        return true;
    }

    public List<ReservaRespostaDto> listarReservas() {
        ReservaRespostaDto respostaDto = new ReservaRespostaDto();
        List<ReservaRespostaDto> respostaList = new ArrayList<>();
        List<Reservamodel> listaReservaModel = repository.findAll();

        for (Reservamodel dados : listaReservaModel) {
            respostaDto.setPrecoHora(dados.getPrecoHora());
            respostaDto.setReservaDatainicio(dados.getReservaDatainicio());
            respostaDto.setNomeCarro(dados.getNomeCarro());
            respostaDto.setPlacaCarro(dados.getPlacaCarro());
            respostaDto.setReservadataFim(dados.getReservadataFim());
            respostaDto.setEstacionamento(dados.getEstacionamentoId().getNumero());
            respostaDto.setUsuario(dados.getUsuarioId().getNome());
            respostaDto.setHorariochegada(dados.getHorariochegada());
            respostaDto.setId(dados.getId());
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


}
