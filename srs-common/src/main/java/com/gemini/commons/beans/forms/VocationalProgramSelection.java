package com.gemini.commons.beans.forms;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/12/18
 * Time: 10:02 PM
 */
public class VocationalProgramSelection {

    private Long schoolId;
    private String programCode;
    private String programDescription;

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VocationalProgramSelection)) return false;
        VocationalProgramSelection that = (VocationalProgramSelection) o;
        return Objects.equals(schoolId, that.schoolId) &&
                Objects.equals(programCode, that.programCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(schoolId, programCode);
    }
}