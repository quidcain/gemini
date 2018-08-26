package com.gemini.commons.beans.forms;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/25/18
 * Time: 12:53 AM
 */
public class AlternateSchoolPreEnrollmentBean extends PreEnrollmentBean {
    private List<AlternateSchoolBean> alternateSchools;

    public List<AlternateSchoolBean> getAlternateSchools() {
        return alternateSchools;
    }

    public void setAlternateSchools(List<AlternateSchoolBean> alternateSchools) {
        this.alternateSchools = alternateSchools;
    }
}