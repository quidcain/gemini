package com.gemini.commons.beans.types;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/16/18
 * Time: 2:39 PM
 */
public enum RequestStatus {
    ACTIVE("Activo"),
    PENDING_TO_REVIEW("Sometida"),
    APPROVED("Aprobada"),
    DENIED_BY_DIRECTOR("Rechazada por el director"),
    DENIED("Rechazada"),
    DENIED_BY_PARENT("Rechazada"),
    REVIEWING("En Revision");

    String description;

    RequestStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
