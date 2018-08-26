package com.gemini.commons.beans.forms;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/27/18
 * Time: 11:49 PM
 */
public class EthnicCodeBean {
    @NotNull
    private String value;
    @NotNull
    private String description;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EthnicCodeBean)) return false;
        EthnicCodeBean that = (EthnicCodeBean) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }
}