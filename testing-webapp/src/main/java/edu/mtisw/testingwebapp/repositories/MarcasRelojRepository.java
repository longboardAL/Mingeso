package edu.mtisw.testingwebapp.repositories;



import edu.mtisw.testingwebapp.entities.MarcasRelojEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcasRelojRepository extends JpaRepository<MarcasRelojEntity, Long> {
}
