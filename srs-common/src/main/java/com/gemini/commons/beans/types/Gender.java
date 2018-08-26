package com.gemini.commons.beans.types;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/20/18
 * Time: 10:12 PM
 */
public enum Gender {
    M("MALE"),
    F("FEMALE");

    String description;

    Gender(String description) {
        this.description = description;
    }
}
