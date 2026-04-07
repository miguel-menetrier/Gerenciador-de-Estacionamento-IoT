package com.senai.SA.repository;

import com.senai.SA.model.Reservamodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reservamodel,Integer> {
}
