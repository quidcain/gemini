package com.gemini.admin.beans.types;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/18/18
 * Time: 2:39 PM
 */
public enum ReportType {
    CONFIRMED("Confirmado"),
    DENIED("Denegado"),
    ALTERNATE_ENROLLMENT("Matricula Alterna"),
    PENDING_ENROLLMENT("Pendiente a ser matriculado"),
    NEW_ENTRY_ENROLLMENT("Nuevo Ingreso"),
    INCOMPLETE_ENROLLMENT(""),
    VOCATIONAL_ENROLLMENT("Ocupacional"),
    TRANSPORTATION_REQUESTED("Estudiantes Solicitando Transportación");

    String description;

    ReportType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
