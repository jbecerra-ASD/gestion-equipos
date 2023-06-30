package com.company.stock.repository;

import com.company.stock.model.Equipo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends CrudRepository<Equipo, String> {
}
