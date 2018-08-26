package com.gemini.commons.database;

import com.gemini.commons.beans.types.Gender;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/8/18
 * Time: 9:49 AM
 */
public interface IdentityEntity {
    Gender getGender();

    void setGender(Gender gender);

    String getFirstName();

    void setFirstName(String firstName);

    String getMiddleName();

    void setMiddleName(String middleName);

    String getLastName();

    void setLastName(String lastName);
}