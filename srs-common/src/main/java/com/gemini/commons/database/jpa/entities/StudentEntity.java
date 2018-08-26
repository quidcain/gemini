package com.gemini.commons.database.jpa.entities;

import com.gemini.commons.beans.types.EntryType;
import com.gemini.commons.beans.types.Gender;
import com.gemini.commons.database.IdentityEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/16/18
 * Time: 2:29 PM
 */
@Entity
@Table(name = "students")
public class StudentEntity implements IdentityEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryType entryType = EntryType.EXISTING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private boolean protectedStudent = false;

    @Column(nullable = false)
    private Long extStudentNumber = -1L;

    @Column(nullable = false)
    private Long sisStudentId = -1L;

    @Column(nullable = false)
    private Long familyId = -1L;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String ssn;

    @Column
    private String citizenship;
    @Column
    private String language;

    @Column
    private boolean isBornPR;
    @Column
    private boolean isHispanic;

    @Column
    private boolean transportationRequested;

    @Column(nullable = false, columnDefinition="number default 0")
    private boolean isMontessori = true;

    @Column(nullable = false, columnDefinition="number default 1")
    private boolean authorization_365 = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EthnicCodeEntity> ethnicCodes;

    @OneToOne
    @JoinColumn(name = "physical_address_id")
    private AddressEntity physical;

    @OneToOne
    @JoinColumn(name = "postal_address_id")
    private AddressEntity postal;

    @CreatedDate
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Date creationDate;
    @LastModifiedDate
    @Column()
    private Date revisionDate;

    @Column
    private Boolean isActiveInd = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isProtectedStudent() {
        return protectedStudent;
    }

    public void setProtectedStudent(boolean protectedStudent) {
        this.protectedStudent = protectedStudent;
    }

    public Long getExtStudentNumber() {
        return extStudentNumber;
    }

    public void setExtStudentNumber(Long extStudentNumber) {
        this.extStudentNumber = extStudentNumber;
    }

    public Long getSisStudentId() {
        return sisStudentId;
    }

    public void setSisStudentId(Long sisStudentId) {
        this.sisStudentId = sisStudentId;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<EthnicCodeEntity> getEthnicCodes() {
        return ethnicCodes;
    }

    public void setEthnicCodes(List<EthnicCodeEntity> ethnicCodes) {
        this.ethnicCodes = ethnicCodes;
    }

    public boolean isBornPR() {
        return isBornPR;
    }

    public void setBornPR(boolean bornPR) {
        isBornPR = bornPR;
    }

    public boolean isHispanic() {
        return isHispanic;
    }

    public void setHispanic(boolean hispanic) {
        isHispanic = hispanic;
    }

    public boolean isTransportationRequested() {
        return transportationRequested;
    }

    public void setTransportationRequested(boolean transportationRequested) {
        this.transportationRequested = transportationRequested;
    }

    public AddressEntity getPhysical() {
        return physical;
    }

    public void setPhysical(AddressEntity physical) {
        this.physical = physical;
    }

    public AddressEntity getPostal() {
        return postal;
    }

    public void setPostal(AddressEntity postal) {
        this.postal = postal;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public Boolean getActiveInd() {
        return isActiveInd;
    }

    public void setActiveInd(Boolean activeInd) {
        isActiveInd = activeInd;
    }

    public boolean isMontessori() {
        return isMontessori;
    }

    public void setMontessori(boolean montessori) {
        isMontessori = montessori;
    }

    public boolean isAuthorization_365() {
        return authorization_365;
    }

    public void setAuthorization_365(boolean authorization_365) {
        this.authorization_365 = authorization_365;
    }
}