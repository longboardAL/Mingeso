package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.EmpleadoEntity;
import edu.mtisw.testingwebapp.entities.HoraExtraEntity;
import edu.mtisw.testingwebapp.repositories.EmpleadoRepository;
import edu.mtisw.testingwebapp.repositories.HoraExtraRepository;
import edu.mtisw.testingwebapp.services.HoraExtraService;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class HoraExtraTest {
    HoraExtraService horaExtraService = new HoraExtraService();
    private OficinaRRHH oficinaRRHH = new OficinaRRHH();
    //EmpleadoEntity empleado = new EmpleadoEntity();

    @Test
    void calcularHoraExtra() throws ParseException {

        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");
        java.util.Date salida = formato.parse("2022/03/26 20:00");
        java.util.Date nacimiento = formato.parse("2000/04/15 19:00");
        java.util.Date empresa = formato.parse("2018/09/03 19:00");

        HoraExtraEntity horaExtra = new HoraExtraEntity();
        horaExtra.setId(5L);
        horaExtra.setRut("17.765.876-2");
        horaExtra.setAutorizacion("Autorizado");
        horaExtra.setFechaAutorizada(salida);

        ArrayList <HoraExtraEntity> horas = new ArrayList<>();
        horas.add(horaExtra);
        System.out.println(horas);
        EmpleadoEntity empleado1 = new EmpleadoEntity("17.765.876-2","Avendaño Saa","Angel Isaac",nacimiento,"A",empresa,1700000,0);

        ArrayList<EmpleadoEntity> empleados = new ArrayList<>();
        empleados.add(empleado1);
        System.out.println(empleados);
        long extra  = horaExtraService.calculoHorasExtras2(empleado1.getRut(), empleado1.getCategoria(), horas, oficinaRRHH);
        assertEquals(2, extra, 0.0);
    }
}
