package edu.mtisw.testingwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
//Mapear esta entidad con la tabla en la BD
@Table(name = "MarcasIngreso")
//getters y setters
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcasRelojEntity {

    @Id
    //Incrementar id auto
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //valores unico y no vacios
    @Column(unique = true, nullable = false)
    private Long id;

    private Date fechaIngreso;
    private String rut;
    private  String instancia;


}
