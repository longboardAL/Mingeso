package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.JustificativoEntity;
import edu.mtisw.testingwebapp.repositories.JustificativoRepository;
import edu.mtisw.testingwebapp.services.JustificativoService;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JustificativoTest {
    @Autowired
    private JustificativoRepository justificativoRepository;
    OficinaRRHH oficinaRRHH = new OficinaRRHH();
    JustificativoService justificativoService = new JustificativoService();
    JustificativoEntity justificativo = new JustificativoEntity();
    @Test
     void calcularHoraAtrasos() throws ParseException {
        justificativo.setRut("17.765.876-2");
        double sueldoFijoMensual = 1700000;
        double descuento = justificativoService.calculoHoraAtrasos(justificativo.getRut(), sueldoFijoMensual, justificativoRepository, oficinaRRHH);
        assertEquals(510000, descuento, 0.0);
    }
}
