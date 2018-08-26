package com.gemini.admin.beans.types;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/11/18
 * Time: 2:02 PM
 */

public enum SchedulingAnalysisType {
    REGULAR("Maestro Regulares"),
    REGULAR_AFTER_PLACEMENT("'Maestro Regulares luego de ubicar"),
    SPECIAL_EDUCATION("Maestro de Educacion Especial"),
    SPECIAL_EDUCATION_AFTER_PLACEMENT("Maestro de Educacion Especial"),
    OCCUPATIONAL_PLACEMENT("Maestro de Ocupacional"),
    SPECIALIZED_PLACEMENT("Maestro de Especializadas");


    String description;


    SchedulingAnalysisType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
