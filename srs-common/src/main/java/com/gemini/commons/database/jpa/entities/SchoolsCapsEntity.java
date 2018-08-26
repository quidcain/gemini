package com.gemini.commons.database.jpa.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/12/18
 * Time: 12:55 PM
 */
@Entity
@Table(name = "schools_caps")
public class SchoolsCapsEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long schoolId;

    @Column(nullable = false)
    private Long extSchoolNumber;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String gradeLevel;

    @Column(nullable = false)
    private Integer totalEnrollmentsByNYE = 0;

    @Column(nullable = false)
    private Integer totalAdded = 0;

    @Column(nullable = false)
    private Integer totalSubtracted = 0;

    @Column(nullable = false)
    private Integer capacity = 0;

    @Column(nullable = false)
    private boolean full = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getExtSchoolNumber() {
        return extSchoolNumber;
    }

    public void setExtSchoolNumber(Long extSchoolNumber) {
        this.extSchoolNumber = extSchoolNumber;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Integer getTotalEnrollmentsByNYE() {
        return totalEnrollmentsByNYE;
    }

    public void setTotalEnrollmentsByNYE(Integer totalEnrollmentsByNYE) {
        this.totalEnrollmentsByNYE = totalEnrollmentsByNYE;
    }

    public Integer getTotalAdded() {
        return totalAdded;
    }

    public void setTotalAdded(Integer totalAdded) {
        this.totalAdded = totalAdded;
    }

    public Integer getTotalSubtracted() {
        return totalSubtracted;
    }

    public void setTotalSubtracted(Integer totalSubtracted) {
        this.totalSubtracted = totalSubtracted;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }
}