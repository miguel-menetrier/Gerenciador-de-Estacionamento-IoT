package com.senai.SA.service;


import com.senai.SA.dto.EstacionamentoDto;
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

    private boolean cadastrarEstacionamento(EstacionamentoDto dto){

        EstacionamentoModel estacionamentoModel = new EstacionamentoModel();
        estacionamentoModel.setNumero(dto.getEstacionamentoNumero());
        estacionamentoModel.setStatus(Status.livre);
        repository.save(estacionamentoModel);
        return true;
    }
    private boolean deletarEstacionamento(int id){
        boolean deletou = false;
        if(repository.existsById(id)){
            repository.deleteById(id);
            deletou = true;
        }
        return deletou;

    }

    private List<EstacionamentoDto> listarEstacionamentos(){
        List<EstacionamentoDto>lista = new ArrayList<>();
        List<EstacionamentoModel>listaModel = repository.findAll();
        for(EstacionamentoModel model : listaModel){
            EstacionamentoDto estacionamentoDto = new EstacionamentoDto();
            estacionamentoDto.setEstacionamentoNumero(model.getNumero());
            estacionamentoDto.setStatus(model.getStatus().toString());
            lista.add(estacionamentoDto);
        }
        return lista;
    }

    private boolean estacionamentoAtualizar(int id,EstacionamentoDto dto){
        Optional<EstacionamentoModel> estacionamentoModel = repository.findById(id);
        boolean atualizou = false;
        if (estacionamentoModel.isPresent()){
            Optional<EstacionamentoModel>estacionamentoModelOptional  = repository.findByEstacionamentoNumero(dto.getEstacionamentoNumero());
            if(estacionamentoModelOptional.isPresent() && estacionamentoModelOptional.get().getId() != id){
                return atualizou;
            }
            estacionamentoModel.get().setNumero(dto.getEstacionamentoNumero());
            switch (dto.getStatus()){
                case "ocupado":
                    estacionamentoModel.get().setStatus(Status.ocupado);
                    break;
                case "livre":
                    estacionamentoModel.get().setStatus(Status.livre);
                    break;
                case "reservado":
                    estacionamentoModel.get().setStatus(Status.reservado);
                    break;
                default:
                    break;
            }
           atualizou = true;
        }
        return atualizou;
    }




}
