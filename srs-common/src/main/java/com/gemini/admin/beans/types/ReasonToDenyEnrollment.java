package com.gemini.admin.beans.types;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/3/18
 * Time: 5:43 PM
 */
public enum ReasonToDenyEnrollment {

    EXCEED_CAPACITY("No hay cupo"),
    NO_MEET_ADMISSION_REQUIREMENTS("No cumple con los criterios de admisioón"),
    OTHER("Otro");

    String description;

    ReasonToDenyEnrollment(String descrition) {
        this.description = descrition;
    }

    public String getDescription() {
        return description;
    }
}