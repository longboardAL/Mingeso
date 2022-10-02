package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.PlanillaSueldoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanillaSueldoRepository extends JpaRepository<PlanillaSueldoEntity, Long> {

}
