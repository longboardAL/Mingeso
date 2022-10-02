package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.EmpleadoEntity;
import edu.mtisw.testingwebapp.repositories.EmpleadoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmpleadoRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Test
    public void encontrarEmpleadoPorRut() {
        // given
        EmpleadoEntity empleado1 = new EmpleadoEntity();
        empleado1.setRut("27.134.678-6");
        empleado1.setNombre("Peter");
        empleado1.setSueldoMensual(2500);
        empleado1.setNumeroHijos(3);
        empleado1.setCategoria("A");

        entityManager.persistAndFlush(empleado1);

        // when
        EmpleadoEntity empleado2 = empleadoRepository.findByRut(empleado1.getRut());

        // then
        assertThat(empleado2.getRut()).isEqualTo(empleado1.getRut());
    }

}
