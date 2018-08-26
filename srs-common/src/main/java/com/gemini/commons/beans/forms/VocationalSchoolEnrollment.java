package com.gemini.commons.beans.forms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/13/18
 * Time: 3:03 PM
 */
public class VocationalSchoolEnrollment {
    private Long schoolId;
    private String schoolName;
    private AddressBean schoolAddress;
    private List<VocationalProgramSelection> programs = new ArrayList<>();

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

    public AddressBean getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(AddressBean schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public List<VocationalProgramSelection> getPrograms() {
        return programs;
    }

    public void setPrograms(List<VocationalProgramSelection> programs) {
        this.programs = programs;
    }
}