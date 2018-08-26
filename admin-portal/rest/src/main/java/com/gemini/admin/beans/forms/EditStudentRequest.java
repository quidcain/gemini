package com.gemini.admin.beans.forms;

import com.gemini.commons.beans.forms.VocationalProgramSelection;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 11:07 PM
 */
public class EditStudentRequest {
    private Long preEnrollmentId;
    private Long studentId;
    private Long schoolId;
    private String schoolName;
    private String gradeLevel;
    private String gradeLevelDescription;
    private String studentName;
    private String disability;
    private String originSchoolNumber;
    private String originSchoolName;
    private Integer age;
    private String status;
    private boolean denied;
    private String reasonToDeny;
    private String colorCss;
    private boolean occupationalEnrollment;
    private VocationalProgramSelection selectedProgram;
    private List<VocationalProgramSelection> programs;
    private Boolean isMontessori;


    public Long getPreEnrollmentId() {
        return preEnrollmentId;
    }

    public void setPreEnrollmentId(Long preEnrollmentId) {
        this.preEnrollmentId = preEnrollmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
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

    public String getGradeLevelDescription() {
        return gradeLevelDescription;
    }

    public void setGradeLevelDescription(String gradeLevelDescription) {
        this.gradeLevelDescription = gradeLevelDescription;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getOriginSchoolNumber() {
        return originSchoolNumber;
    }

    public void setOriginSchoolNumber(String originSchoolNumber) {
        this.originSchoolNumber = originSchoolNumber;
    }

    public String getOriginSchoolName() {
        return originSchoolName;
    }

    public void setOriginSchoolName(String originSchoolName) {
        this.originSchoolName = originSchoolName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDenied() {
        return denied;
    }

    public void setDenied(boolean denied) {
        this.denied = denied;
    }

    public String getDisability() {
        return StringUtils.hasText(disability) ? disability : "No tiene";
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getReasonToDeny() {
        return reasonToDeny;
    }

    public void setReasonToDeny(String reasonToDeny) {
        this.reasonToDeny = reasonToDeny;
    }

    public String getColorCss() {
        return colorCss;
    }

    public void setColorCss(String colorCss) {
        this.colorCss = colorCss;
    }

    public boolean isOccupationalEnrollment() {
        return occupationalEnrollment;
    }

    public void setOccupationalEnrollment(boolean occupationalEnrollment) {
        this.occupationalEnrollment = occupationalEnrollment;
    }

    public VocationalProgramSelection getSelectedProgram() {
        return selectedProgram;
    }

    public void setSelectedProgram(VocationalProgramSelection selectedProgram) {
        this.selectedProgram = selectedProgram;
    }

    public List<VocationalProgramSelection> getPrograms() {
        return programs;
    }

    public void setPrograms(List<VocationalProgramSelection> programs) {
        this.programs = programs;
    }

    public Boolean getMontessori() {
        return isMontessori;
    }

    public void setMontessori(Boolean montessori) {
        isMontessori = montessori;
    }
}