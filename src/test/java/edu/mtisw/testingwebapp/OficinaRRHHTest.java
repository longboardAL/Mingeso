package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.EmpleadoEntity;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OficinaRRHHTest {
    OficinaRRHH oficinaRRHH = new OficinaRRHH();
    EmpleadoEntity empleado = new EmpleadoEntity();
    @Test
    void calcularBonificacionPorHoraExtra() {
        empleado.setCategoria("A");
        long HorasExtras = 1;
        long bonificacion = oficinaRRHH.calcularBonificacionPorHorasExtras(HorasExtras, empleado.getCategoria());

        assertEquals(25000, bonificacion, 0.0);
    }

    @Test
    void calcularDescuentoAtraso() {
        empleado.setSueldoMensual(1700000);
        long minutos = 15;
        double descuento = oficinaRRHH.calcularMontoDescuentoPorAtraso(minutos, empleado.getSueldoMensual());

        assertEquals(17000, descuento, 0.0);
    }
    @Test
    void calcularDescuentoAtrasoDias() {
        empleado.setSueldoMensual(1700000);
        long minutos = 15;
        double descuento = oficinaRRHH.calcularMontoDescuentoPorAtraso(minutos, empleado.getSueldoMensual());

        assertEquals(17000, descuento, 0.0);
    }

    @Test
    void calcularDescuentoProvision() {
        empleado.setSueldoMensual(1700000);
        double descuento = oficinaRRHH.calcularMontoDescuentoProvisional(empleado.getSueldoMensual());
        assertEquals(170000, descuento, 0.0);
    }

    @Test
    void calcularDescuentoSalud() {
        empleado.setSueldoMensual(1700000);
        double descuento = oficinaRRHH.calcularMontoDescuentoSalud(empleado.getSueldoMensual());
        assertEquals(136000, descuento, 0.0);
    }

    @Test
    void calcularBonificacionAnios() {
        empleado.setSueldoMensual(1700000);
        long anios = 5;
        double descuento = oficinaRRHH.calcularMontoBonificacionAniosServicios(anios, empleado.getSueldoMensual());
        assertEquals(85000, descuento, 0.0);
    }
    @Test
    void calcularAnios() throws ParseException {
        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date fecha = formato.parse("2018/09/03 08:00");
        empleado.setFechaIngresoEmpresa(fecha);
        long anios = oficinaRRHH.aniosServicio(empleado.getFechaIngresoEmpresa());
        assertEquals(4, anios, 0.0);
    }
}