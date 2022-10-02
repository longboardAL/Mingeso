package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.HoraExtraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraExtraRepository extends JpaRepository <HoraExtraEntity, Long> {

}
