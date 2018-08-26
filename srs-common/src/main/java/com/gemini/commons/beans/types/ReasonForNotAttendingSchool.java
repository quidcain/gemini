package com.gemini.commons.beans.types;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/30/18
 * Time: 4:09 PM
 */
public enum ReasonForNotAttendingSchool {

    MOVE_OUT_OF_COUNTRY("Mudanza fuera del país"),
    MEDICAL_CONDITION("Condición de Salud"),
    COURT_ORDER("Orden Judicial"),
    MOVE_OUT_OF_SCHOOL_CITY("Mudanza fuera del municipio escolar");

    String description;

    ReasonForNotAttendingSchool(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}