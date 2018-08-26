package com.gemini.commons.beans.integration;

import com.gemini.commons.beans.IdentityForm;
import com.gemini.commons.beans.SsnObfuscated;
import com.gemini.commons.beans.responses.ResponseBase;
import com.gemini.commons.beans.types.Gender;
import com.gemini.commons.utils.Utils;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/9/18
 * Time: 12:27 AM
 */
public class StudentResponse extends ResponseBase implements IdentityForm, SsnObfuscated {
    private Long studentNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;
    private String lastSsnFormatted;

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLastSsnFormatted() {
        return lastSsnFormatted;
    }

    public void setLastSsnFormatted(String lastSsnFormatted) {
        this.lastSsnFormatted = lastSsnFormatted;
    }

    public String getFullName() {
        return Utils.toFullName(firstName, middleName, lastName);
    }

}