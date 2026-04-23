package com.senai.SA.service;


import com.senai.SA.dto.EstacionamentoRequisicaoDto;
import com.senai.SA.dto.EstacionamentoRespostaDto;
import com.senai.SA.infra.Status;
import com.senai.SA.model.EstacionamentoModel;
import com.senai.SA.repository.EstacionamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {
    private final EstacionamentoRepository repository;

    public boolean cadastrarEstacionamento(EstacionamentoRequisicaoDto dto) {

        EstacionamentoModel estacionamentoModel = new EstacionamentoModel();
        estacionamentoModel.setNumero(dto.getEstacionamentoNumero());
        estacionamentoModel.setPrecoHora(dto.getPrecohora());
        estacionamentoModel.setStatus(Status.Livre);
        repository.save(estacionamentoModel);
        return true;
    }

    public boolean deletarEstacionamento(int id) {
        boolean deletou = false;
        if (repository.existsById(id)) {
            repository.deleteById(id);
            deletou = true;
        }
        return deletou;

    }

    public List<EstacionamentoRespostaDto> listarEstacionamentos() {
        List<EstacionamentoRespostaDto> lista = new ArrayList<>();
        List<EstacionamentoModel> listaModel = repository.findAll();
        for (EstacionamentoModel model : listaModel) {
            EstacionamentoRespostaDto estacionamentoDto = new EstacionamentoRespostaDto();
            estacionamentoDto.setId(model.getId());
            estacionamentoDto.setEstacionamentoNumero(model.getNumero());
            estacionamentoDto.setStatus(model.getStatus().toString());
            estacionamentoDto.setPrecohora(model.getPrecoHora());
            lista.add(estacionamentoDto);
        }
        return lista;
    }

    public boolean estacionamentoAtualizar(int id, EstacionamentoRequisicaoDto dto) {
        Optional<EstacionamentoModel> estacionamentoModel = repository.findById(id);
        boolean atualizou = false;
        if (estacionamentoModel.isPresent()) {
            Optional<EstacionamentoModel> estacionamentoModelOptional = repository.findByNumero(dto.getEstacionamentoNumero());
            if (estacionamentoModelOptional.isPresent() && estacionamentoModelOptional.get().getId() != id) {
                return atualizou;
            }
            estacionamentoModel.get().setNumero(dto.getEstacionamentoNumero());
            switch (dto.getStatus()) {
                case "Lcupado":
                    estacionamentoModel.get().setStatus(Status.Ocupado);
                    break;
                case "Livre":
                    estacionamentoModel.get().setStatus(Status.Livre);
                    break;
                case "Reservado":
                    estacionamentoModel.get().setStatus(Status.Reservado);
                    break;
                default:
                    break;
            }
            estacionamentoModel.get().setPrecoHora(dto.getPrecohora());
            atualizou = true;
        }
        if (atualizou) repository.save(estacionamentoModel.get());
        return atualizou;
    }

    public EstacionamentoRespostaDto estacionamentobyId(int id) {
        Optional<EstacionamentoModel> estacionamentoModel = repository.findById(id);
        EstacionamentoRespostaDto dto = new EstacionamentoRespostaDto();
        if (estacionamentoModel.isPresent()) {
            dto.setId(estacionamentoModel.get().getId());
            dto.setStatus(estacionamentoModel.get().getStatus().toString());
            dto.setEstacionamentoNumero(estacionamentoModel.get().getNumero());
        }
        return dto;
    }

    public long qtdEstacionamentos() {
        return repository.count();
    }


}
