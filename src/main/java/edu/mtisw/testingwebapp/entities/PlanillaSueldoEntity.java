package edu.mtisw.testingwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
//Mapear esta entidad con la tabla en la BD
@Table(name = "PlanillaSueldos")
//getters y setters
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaSueldoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;

    private String apellidos;
    private String nombres;
    private String categoria;
    private long aniosServicios;
    private double sueldoFijoMensual;
    private double montoBonificacionAnios;
    private double montoHorasExtras;
    private double montodescuentos;
    private double montoBruto;
    private double montoProvisional;
    private double montoSalud;
    private double montoFinal;


}
