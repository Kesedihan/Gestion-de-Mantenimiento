package com.jorel.proyectogestionmantcorrectivo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prioridad")

public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prioridad")
    private Long idPrioridad;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel", nullable = false, unique = true, length = 50)
    private NivelPrioridad nivel;

    @Column(name = "tiempo_max_respuesta", nullable = false)
    private Integer tiempoMaxRespuesta;
}


