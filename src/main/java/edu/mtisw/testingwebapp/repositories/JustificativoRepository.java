package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.JustificativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JustificativoRepository extends JpaRepository<JustificativoEntity, Long> {

}
