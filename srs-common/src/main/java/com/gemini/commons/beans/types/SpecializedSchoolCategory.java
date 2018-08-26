package com.gemini.commons.beans.types;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/30/18
 * Time: 2:37 AM
 */
public enum SpecializedSchoolCategory {
    SCIENCE_AND_MATHEMATHICS("STEM (Ciencias y Matemáticas)"),
    FINE_ARTS("Bellas Artes (Cinematografía, Música y Radio y Televisión)"),
    BILINGUE("Bilingüe e Idiomas"),
    MONTESSORI("Montessori");

    String description;

    SpecializedSchoolCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
