package com.senai.SA.repository;

import com.senai.SA.model.EstacionamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstacionamentoRepository extends JpaRepository<EstacionamentoModel,Integer> {
Optional<EstacionamentoModel>findByEstacionamentoNumero(String numero);

}
