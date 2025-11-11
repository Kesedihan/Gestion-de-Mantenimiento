package com.jorel.proyectogestionmantcorrectivo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "analisis_falla")
public class AnalisisFalla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnalisis;

    @OneToOne
    @JoinColumn(name = "id_orden")
    private OrdenDeTrabajo orden;

    private String sintoma;
    private String causa;
    private String metodo;
    private String conclusion;
}
