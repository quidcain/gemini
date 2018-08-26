package com.gemini.beans.responses;

import com.gemini.commons.beans.forms.PreEnrollmentBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/27/18
 * Time: 2:33 PM
 */
public class HomeResponse {

    private List<PreEnrollmentBean> preEnrollments;

    public List<PreEnrollmentBean> getPreEnrollments() {
        return preEnrollments;
    }

    public void setPreEnrollments(List<PreEnrollmentBean> preEnrollments) {
        this.preEnrollments = preEnrollments;
    }
}