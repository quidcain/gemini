package com.gemini.commons.beans.requests;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/19/18
 * Time: 4:07 PM
 */
public class StudentSearchRequest {
//    @NotNull
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    private Long studentNumber;
    private String ssn;
//    @NotNull
//    @NotEmpty
    private String dateOfBirth;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "StudentSearchRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentNumber=" + studentNumber +
                ", ssn=" + ssn +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}