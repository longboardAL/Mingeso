package edu.mtisw.testingwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoEntity {


    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(unique = true, nullable = false)
    //private Long id;

    @Id
    private String rut;

    private String apellidos;
    private String nombre;
    private Date fechaNacimiento;
    private String categoria;
    private Date fechaIngresoEmpresa;
    private double sueldoMensual;
    private int numeroHijos;
}
