package com.gemini.commons.beans.forms;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/30/18
 * Time: 3:54 AM
 */
public class EnumCode {

    String name;
    String description;

    public EnumCode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public EnumCode() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}