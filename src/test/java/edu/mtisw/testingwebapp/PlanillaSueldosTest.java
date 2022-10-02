package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.PlanillaSueldoEntity;
import edu.mtisw.testingwebapp.repositories.EmpleadoRepository;
import edu.mtisw.testingwebapp.repositories.HoraExtraRepository;
import edu.mtisw.testingwebapp.repositories.JustificativoRepository;
import edu.mtisw.testingwebapp.repositories.PlanillaSueldoRepository;
import edu.mtisw.testingwebapp.services.HoraExtraService;
import edu.mtisw.testingwebapp.services.JustificativoService;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
import edu.mtisw.testingwebapp.services.PlanillaSueldosServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlanillaSueldosTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    PlanillaSueldoRepository planillaSueldoRepository;
    @Autowired
    EmpleadoRepository empleadoRepository;
    @Autowired
    HoraExtraRepository horaExtraRepository;
    @Autowired
    JustificativoRepository justificativoRepository;


    PlanillaSueldosServices planillaSueldosServices = new PlanillaSueldosServices();
    OficinaRRHH oficinaRRHH = new OficinaRRHH();
    HoraExtraService horaExtraService = new HoraExtraService();

    JustificativoService justificativoService = new JustificativoService();

    @Test
    void generarPlanilla() throws ParseException {
        PlanillaSueldoEntity planillaSueldo = new PlanillaSueldoEntity();
        planillaSueldo.setRut("17.765.876-2");
        planillaSueldo.setMontoFinal(1045500);
        double a = planillaSueldosServices.calcularSueldo(2, empleadoRepository, horaExtraRepository,oficinaRRHH,
                justificativoRepository, horaExtraService, justificativoService, planillaSueldoRepository);
        assertEquals(1066000, a, 0.0);
    }
}
