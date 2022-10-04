package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.EmpleadoEntity;
import edu.mtisw.testingwebapp.entities.HoraExtraEntity;
import edu.mtisw.testingwebapp.repositories.EmpleadoRepository;
import edu.mtisw.testingwebapp.repositories.HoraExtraRepository;
import edu.mtisw.testingwebapp.services.HoraExtraService;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HoraExtraTest {
    @Autowired
    private HoraExtraRepository horaExtraRepository;
    OficinaRRHH oficinaRRHH = new OficinaRRHH();
    HoraExtraService horaExtraService = new HoraExtraService();
    HoraExtraEntity horaExtra = new HoraExtraEntity();
    EmpleadoEntity empleado = new EmpleadoEntity();
    @Test
    void calcularHoraExtra() throws ParseException {
        empleado.setRut("17.765.876-2");
        empleado.setCategoria("A");
        double horaExtra = horaExtraService.calculoHorasExtras(empleado.getRut(),empleado.getCategoria(),horaExtraRepository,oficinaRRHH);
        assertEquals(25000, horaExtra, 0.0);
    }
}
