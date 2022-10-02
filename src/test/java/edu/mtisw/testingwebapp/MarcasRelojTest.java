package edu.mtisw.testingwebapp;


import edu.mtisw.testingwebapp.services.MarcasRelojService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MarcasRelojTest {

    MarcasRelojService marcasRelojService = new MarcasRelojService();

    @Test
    void VeridicarHorario() throws ParseException {
        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date fecha = formato.parse("2022/08/17 08:00");
        String instancia = marcasRelojService.obtenerInstancia(fecha);
        assertEquals("Entrada", instancia, String.valueOf(0.0));
    }
}
