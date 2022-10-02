package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.EmpleadoEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OficinaRRHH {

   //Calcula bonificacion por numero de horas extras
   //Categoria "A": le corresponde 25.000 mil pesos x hora
   //Categoria "B": le corresponde 20.000 mil pesos x hora
   //Categoria "C": le corresponde 10.000 mil pesos x hora
   public long calcularBonificacionPorHorasExtras(long numHorasExtras, String categoria) {
      long bonificacionPorHorasExtras = 0;

      if(categoria.equals("A")) {
         bonificacionPorHorasExtras = numHorasExtras * 25000;
      }
      if(categoria.equals("B")) {
         bonificacionPorHorasExtras = numHorasExtras * 20000;
      }
      if(categoria.equals("C")){
         bonificacionPorHorasExtras = numHorasExtras * 10000;
      }

      return bonificacionPorHorasExtras;
   }

   // Calcula el monto de descuento del "sueldo fijo mensual" según las marcas de hora después de la hora de entrada
   // > 10: 1% del monto de su sueldo si no aplica justificativo
   // > 25: 3% del monto de su sueldo si no aplica justificativo
   // > 45: 6% del monto de su sueldo si no aplica justificativo
   // > 70: 15% del monto de su sueldo si no aplica justificativo X dia que no vino a trabajar
   // > 70: x% del monto de su sueldo si  aplica justificativo??
   public double calcularMontoDescuentoPorAtraso(long minutosAtraso, double sueldoFijo) {

      double montoDescuentoPorAtraso = 0;
      if((minutosAtraso > 10) && (minutosAtraso <= 25)){
         montoDescuentoPorAtraso = sueldoFijo * 0.01;
      }if((minutosAtraso > 25) && (minutosAtraso <= 45)){
         montoDescuentoPorAtraso = sueldoFijo * 0.03;
      }if((minutosAtraso > 45) && (minutosAtraso <= 70)){
         montoDescuentoPorAtraso = sueldoFijo * 0.06;
      }if(minutosAtraso > 70){
         montoDescuentoPorAtraso = sueldoFijo * 0.15;
      }
      System.out.println("descuento minutos: "+minutosAtraso);
      return montoDescuentoPorAtraso;
   }

   public double calcularMontoDescuentoPorAtrasoDias(double sueldoFijo) {

      double montoDescuentoPorAtraso = 0;
      montoDescuentoPorAtraso = sueldoFijo * 0.15;
      System.out.println("descuento dias: " + montoDescuentoPorAtraso);
      return montoDescuentoPorAtraso;
   }


   // Descuentos que se aplican por ley a todos los empleados. Estos descuentos se aplican sobre
   // el monto del sueldo final
   // Cotización previsional: 10%

   public double calcularMontoDescuentoProvisional(double montoSueldoFinal) {
      double montoDescuentoProvisional = 0;

      montoDescuentoProvisional = (montoSueldoFinal * 0.10);

      return montoDescuentoProvisional;
   }

   // Descuentos que se aplican por ley a todos los empleados. Estos descuentos se aplican sobre
   // el monto del sueldo final
   // Cotización del plan de salud: 8%

   public double calcularMontoDescuentoSalud(double montoSueldoFinal) {
      double montoDescuentoSalud = 0;

      montoDescuentoSalud = (montoSueldoFinal * 0.08);

      return montoDescuentoSalud;
   }
   //  bonificación por tiempo de servicio. Se calcula sobre el sueldo fijo
   //   <  5  años 0%
   //   >= 5  años 5%
   //   >= 10 años 8%
   //   >= 15 años 11%
   //   >= 20 años 14%
   //   >= 25 años 17%
   public double calcularMontoBonificacionAniosServicios(long anosServicios, double sueldoFinal) {
      double bonificacionTiempoServicio = 0;

      if(anosServicios < 5){
         bonificacionTiempoServicio = (sueldoFinal * 0.00);
      }if((anosServicios >= 5) && (anosServicios < 10)){
         bonificacionTiempoServicio = (sueldoFinal * 0.05);
      }if((anosServicios >= 10) && (anosServicios < 15)){
         bonificacionTiempoServicio = (sueldoFinal * 0.08);
      }if(anosServicios >= 15 && (anosServicios < 20)){
         bonificacionTiempoServicio = (sueldoFinal * 0.11);
      }if(anosServicios >= 20 && (anosServicios < 25)){
         bonificacionTiempoServicio = (sueldoFinal * 0.14);
      }if(anosServicios >= 25){
         bonificacionTiempoServicio = (sueldoFinal * 0.17);
      }

      return bonificacionTiempoServicio;
   }

   public long aniosServicio(Date fechaIngreso) throws ParseException {

      long aniosServicios = 0;
      DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");
      java.util.Date salida = formato.parse("2022/09/24 08:00");

      long l = salida.getTime()-fechaIngreso.getTime();
      long day = l/(24*60*60*1000);
      if(day > 365){
         aniosServicios = day/365;
      }
      return aniosServicios;
   }
}

