package com.gemini.commons.beans.forms;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/13/18
 * Time: 3:01 PM
 */
public class VocationalPreEnrollmentBean extends PreEnrollmentBean {
    private List<VocationalSchoolEnrollment> enrollments;

    public List<VocationalSchoolEnrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<VocationalSchoolEnrollment> enrollments) {
        this.enrollments = enrollments;
    }
}