package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.EmpleadoEntity;
import edu.mtisw.testingwebapp.entities.HoraExtraEntity;
import edu.mtisw.testingwebapp.repositories.HoraExtraRepository;
import edu.mtisw.testingwebapp.services.HoraExtraService;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//sacarlos
public class HoraExtraTest {
    @Autowired
    private HoraExtraRepository horaExtraRepository;
    //@Mock
    //HoraExtraRepository horaExtraRepository;
    //@InjectMocks
    OficinaRRHH oficinaRRHH = new OficinaRRHH();
    HoraExtraService horaExtraService = new HoraExtraService();
    HoraExtraEntity horaExtra = new HoraExtraEntity();
    EmpleadoEntity empleado = new EmpleadoEntity();
    @Test
    void calcularHoraExtra() throws ParseException {
        //HoraExtraEntity horaExtra = new HoraExtraEntity();
        //horaExtra.setAutorizacion();
        //horaExtra.setFechaAutorizada();
        //horaExtra.setRut();

        //when(horaExtraRepository.save(any()))
        empleado.setRut("17.765.876-2");
        empleado.setCategoria("A");
        double horaExtra = horaExtraService.calculoHorasExtras(empleado.getRut(),empleado.getCategoria(),horaExtraRepository,oficinaRRHH);
        assertEquals(25000, horaExtra, 0.0);
    }
}
