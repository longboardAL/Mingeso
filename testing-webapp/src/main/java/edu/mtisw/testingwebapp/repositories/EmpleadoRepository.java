package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    public EmpleadoEntity findByRut(String rut);
}