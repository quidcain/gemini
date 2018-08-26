package com.gemini.admin.beans.forms;

import com.gemini.commons.utils.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/23/18
 * Time: 5:43 AM
 */
public class RequestSearchCriteria {
    private String email;
    private String ssn;
    private Long studentNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = Utils.cleanSsn(ssn);
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }
}