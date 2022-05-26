package com.edug.devfinder.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor

@Getter
public enum ProficiencyEnum {
    BEGGINING(1L, "Conhecimento básico. Entende conceitos básicos, já utilizou a tecnologia em POCs ou projetos de pequena escala/visibilidade."),
    INTERMEDIATE(2L , "Conhecimento mediano, já utilizou a tecnologia em ambiente de produção mas sem profundidade."),
    ADVANCED(3L, "Conhecimento avançado. Utilizou diversas vezes a tecnologia em ambiente profissional, está preparado para resolver problemas incomuns que possam ocorrer ao utilizar a mesma.");

    private Long id;
    private String description;
}
