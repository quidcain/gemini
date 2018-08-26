package com.gemini.beans.requests.enrollment;

import com.gemini.commons.beans.forms.AlternateSchoolBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/24/18
 * Time: 3:58 PM
 */
public class AlternateSchoolPreEnrollmentSubmitRequest extends PreEnrollmentSubmitRequest{

    private List<AlternateSchoolBean> alternateSchools;
    private List<AlternateSchoolBean> alternateSchoolsToDelete;

    public List<AlternateSchoolBean> getAlternateSchools() {
        return alternateSchools;
    }

    public void setAlternateSchools(List<AlternateSchoolBean> alternateSchools) {
        this.alternateSchools = alternateSchools;
    }

    public List<AlternateSchoolBean> getAlternateSchoolsToDelete() {
        return alternateSchoolsToDelete;
    }

    public void setAlternateSchoolsToDelete(List<AlternateSchoolBean> alternateSchoolsToDelete) {
        this.alternateSchoolsToDelete = alternateSchoolsToDelete;
    }
}