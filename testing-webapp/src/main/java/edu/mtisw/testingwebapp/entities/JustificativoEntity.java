package edu.mtisw.testingwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Justificativos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JustificativoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private String rut;
    private Date fechaJustificada;
    private String justificativo;
    private String diasInasistencia;

}

