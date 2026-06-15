package com.senai.SA.repository;

import com.senai.SA.model.EstacionamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EstacionamentoRepository extends JpaRepository<EstacionamentoModel, Integer> {

    Optional<EstacionamentoModel> findByNumero(String estacionamentoNumero);

    Optional<EstacionamentoModel> findById(int id);

    @Query(value = """
    SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
    FROM reservas r
    WHERE r.estacionamento_id = :id
    AND CONCAT(r.reservadatainicio, ' ', r.reservahorariochegada) <= :now
    AND CONCAT(r.reservasdatafim, ' ', r.reservahorariosaida) >= :now
""", nativeQuery = true)
    Integer existsReservationActive(@Param("id") Integer id,
                                    @Param("now") LocalDateTime now);
}