package com.gemini.beans.requests.enrollment;

import com.gemini.commons.beans.IdentityForm;
import com.gemini.commons.beans.types.Gender;
import com.gemini.commons.beans.types.EnrollmentType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/20/18
 * Time: 10:08 PM
 */
public class PreEnrollmentInitialRequest implements IdentityForm {
    private Long requestId;
    private EnrollmentType type;
    private Long studentNumber;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{4}$")
    private String ssn;
    @NotNull
    @NotBlank
    private String firstName;
    private String middleName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    private Date dateOfBirth;
    @NotNull
    private Gender gender;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}