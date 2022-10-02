package edu.mtisw.testingwebapp.services;


import edu.mtisw.testingwebapp.entities.*;
import edu.mtisw.testingwebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@Service
public class PlanillaSueldosServices {

    @Autowired
    PlanillaSueldoRepository planillaSueldoRepository;
    @Autowired
    JustificativoRepository justificativoRepository;
    @Autowired
    HoraExtraRepository horaExtraRepository;
    @Autowired
    JustificativoService justificativoService;

    @Autowired
    OficinaRRHH oficinaRRHH;

    @Autowired
    HoraExtraService horaExtraService;


    @Autowired
    EmpleadoRepository empleadoRepository;

    public ArrayList<PlanillaSueldoEntity> obtenerPlanilla() {
        return (ArrayList<PlanillaSueldoEntity>) planillaSueldoRepository.findAll();
    }

    public double calcularSueldo(int i, EmpleadoRepository empleadoRepository, HoraExtraRepository horaExtraRepository,
                                 OficinaRRHH oficinaRRHH, JustificativoRepository justificativoRepository, HoraExtraService horaExtraService,
                                 JustificativoService justificativoService, PlanillaSueldoRepository planillaSueldoRepository) throws ParseException {

        String rut = empleadoRepository.findAll().get(i).getRut();
        String categoria = empleadoRepository.findAll().get(i).getCategoria();
        double sueldoMensual = empleadoRepository.findAll().get(i).getSueldoMensual();
        Date fechaIngreso = empleadoRepository.findAll().get(i).getFechaIngresoEmpresa();

        // variables a utilizar
        double sueldo = 0;
        double sueldoBruto = 0;
        double montoAniosServicios = 0;
        long aniosServicio = 0;
        long montoHorasExtras = 0;
        double montoDescuentos = 0;
        double cotizacionProvisional = 0;
        double cotizacionSalud = 0;
        double bonificaciones = 0;


        // Si el sueldo es positivo, proceder al siguiente paso
        if (sueldoMensual > 0) {

            // clacular y verificar  si las horas extras están validadas
            montoHorasExtras = horaExtraService.calculoHorasExtras(rut,categoria,horaExtraRepository,oficinaRRHH);
            System.out.println("horas extras: " + montoHorasExtras);

            //Las marcas de hora después de la hora de entrada tienen un descuento sobre el sueldo fijo mensual
            montoDescuentos = justificativoService.calculoHoraAtrasos(rut, sueldoMensual, justificativoRepository, oficinaRRHH);
            System.out.println("Sueldo: " + sueldoMensual + " Descuentos: " + montoDescuentos);

            //Calculo años de servicio
            aniosServicio = oficinaRRHH.aniosServicio(fechaIngreso);

            // Calculo del beneficio por años de servicio
            montoAniosServicios = oficinaRRHH.calcularMontoBonificacionAniosServicios(aniosServicio, sueldoMensual);
            bonificaciones = montoAniosServicios;
            System.out.println("Monto anios servicio: " + montoAniosServicios + " Total descuentos: " + montoDescuentos);

            sueldo = sueldoMensual + bonificaciones + montoHorasExtras - montoDescuentos;

            // Descuentos que se aplican a todos los empleados sobre el sueldo final: Salud y provisión
            cotizacionProvisional = oficinaRRHH.calcularMontoDescuentoProvisional(sueldo);
            cotizacionSalud = oficinaRRHH.calcularMontoDescuentoSalud(sueldo);

            // sumar descuentos
            montoDescuentos = montoDescuentos + cotizacionProvisional + cotizacionSalud;
            System.out.println("Anios servicio: " + aniosServicio + " Porvision: " + cotizacionProvisional + " Salud: " + cotizacionSalud);

            sueldo = sueldo - cotizacionProvisional - cotizacionSalud;
            sueldoBruto = sueldoMensual + bonificaciones + montoHorasExtras;

            guardarPlanillaEmpleado(planillaSueldoRepository, empleadoRepository, aniosServicio, bonificaciones, montoHorasExtras, montoDescuentos, cotizacionProvisional, cotizacionSalud, sueldo, i, sueldoBruto);
        }
        return sueldo;
    }

    public void guardarPlanillaEmpleado(PlanillaSueldoRepository planillaSueldoRepository, EmpleadoRepository empleadoRepository, long aniosServicio, double bonificaciones, long montoHorasExtras, double montoDescuentos,
                                        double cotizacionProvisional, double cotizacionSalud, double sueldo, int i, double sueldoBruto){

        PlanillaSueldoEntity planillaSueldoEntity1 = new PlanillaSueldoEntity();
        planillaSueldoEntity1.setRut(empleadoRepository.findAll().get(i).getRut());
        planillaSueldoEntity1.setNombres(empleadoRepository.findAll().get(i).getNombre());
        planillaSueldoEntity1.setApellidos(empleadoRepository.findAll().get(i).getApellidos());
        planillaSueldoEntity1.setCategoria(empleadoRepository.findAll().get(i).getCategoria());
        planillaSueldoEntity1.setAniosServicios(aniosServicio);
        planillaSueldoEntity1.setSueldoFijoMensual(empleadoRepository.findAll().get(i).getSueldoMensual());
        planillaSueldoEntity1.setMontoBonificacionAnios(bonificaciones);
        planillaSueldoEntity1.setMontoHorasExtras(montoHorasExtras);
        planillaSueldoEntity1.setMontodescuentos(montoDescuentos);
        planillaSueldoEntity1.setMontoBruto(sueldoBruto);
        planillaSueldoEntity1.setMontoProvisional(cotizacionProvisional);
        planillaSueldoEntity1.setMontoSalud(cotizacionSalud);
        planillaSueldoEntity1.setMontoFinal(sueldo);

        if(!ExistePlanilla(planillaSueldoEntity1, planillaSueldoRepository)) {
            System.out.println("Aca la planilla: "+planillaSueldoEntity1);
            planillaSueldoRepository.save(planillaSueldoEntity1);
        }
    }

    public void generarPlanilla() throws ParseException {
        for(int i = 0; i < empleadoRepository.count(); i++){
            double sueldo = calcularSueldo(i, empleadoRepository, horaExtraRepository,oficinaRRHH,justificativoRepository,
                    horaExtraService, justificativoService, planillaSueldoRepository);
        }
    }

    public boolean ExistePlanilla(PlanillaSueldoEntity planillaSueldoEntity, PlanillaSueldoRepository planillaSueldoRepository){
        boolean validar = false;
        for (int i = 0; i < planillaSueldoRepository.count(); i++){
            if(planillaSueldoRepository.findAll().get(i).getRut().equals(planillaSueldoEntity.getRut()) &&
            planillaSueldoRepository.findAll().get(i).getMontoFinal() == planillaSueldoEntity.getMontoFinal()){
                validar = true;
            }
        }
        return validar;
    }

    public double pruebatest(PlanillaSueldoEntity planillaSueldoEntity, PlanillaSueldoRepository planillaSueldoRepository){
        double validar = 0;
        for (int i = 0; i < planillaSueldoRepository.count(); i++){
            if(planillaSueldoRepository.findAll().get(i).getRut().equals(planillaSueldoEntity.getRut()) &&
                    planillaSueldoRepository.findAll().get(i).getMontoFinal() == planillaSueldoEntity.getMontoFinal()){
                validar = 1;
            }
        }
        return validar;
    }
}