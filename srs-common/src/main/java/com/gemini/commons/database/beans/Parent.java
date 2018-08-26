package com.gemini.commons.database.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/9/18
 * Time: 1:24 AM
 */

import java.util.Date;

public class Parent {

    private String familyId;
    private Date dateOfBirth;
    private String ssn;
    private String gender;
    private String firstName;
    private String lastName;
    private Integer houseHeadNumber;
    private Long houseHeadId;
    private String relationCd;
    private Long studentId;
    private Integer primaryFamilyInd;
    private Integer livesWithInd;
    private String occupation;

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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

    public Integer getHouseHeadNumber() {
        return houseHeadNumber;
    }

    public void setHouseHeadNumber(Integer houseHeadNumber) {
        this.houseHeadNumber = houseHeadNumber;
    }

    public Long getHouseHeadId() {
        return houseHeadId;
    }

    public void setHouseHeadId(Long houseHeadId) {
        this.houseHeadId = houseHeadId;
    }

    public String getRelationCd() {
        return relationCd;
    }

    public void setRelationCd(String relationCd) {
        this.relationCd = relationCd;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getPrimaryFamilyInd() {
        return primaryFamilyInd;
    }

    public void setPrimaryFamilyInd(Integer primaryFamilyInd) {
        this.primaryFamilyInd = primaryFamilyInd;
    }

    public Integer getLivesWithInd() {
        return livesWithInd;
    }

    public void setLivesWithInd(Integer livesWithInd) {
        this.livesWithInd = livesWithInd;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}