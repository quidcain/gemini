package com.gemini.admin.security;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/7/18
 * Time: 1:50 AM
 */


/*
    3641	SIE
    5163	Estadisticos y SAPDE

    2660	Especialistas
    5645	Recursos Humanos - Solo Lectura
    5985	Ayudante Especial
    6928	Educación Ocupacional y Técnica - Editar

    2620	Director Escolar
    6625	Director Escolar Destaque
    3642	Directores Regionales y Superintendentes
    --NO USED
    6325	Educación Ocupacional y Técnica
    4862	Especialistas Enlaces
    7908	Directores Regionales (o records)
    4242	Disciplina
    7947	Director Centro Homeless
*/
public enum Authorities {
    DE_CENTRAL("Nivel Central"),
    ACCESS_ON_VARIOUS_REGIONS("Director con acceso a varias Regiones"),
    REGION_DIRECTOR("Director Regional"),
    ACCESS_ON_VARIOUS_SCHOOLS("Director con acceso a varias Escuelas"),
    SCHOOL_DIRECTOR("Director Escolar");


    String description;

    Authorities(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}