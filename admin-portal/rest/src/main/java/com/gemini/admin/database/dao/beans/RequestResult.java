package com.gemini.admin.database.dao.beans;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/30/18
 * Time: 6:54 PM
 */
public class RequestResult {
    /*
        ID                         NUMBER
        EMAIL                      VARCHAR2(255 CHAR)
        FIRST_NAME        NOT NULL VARCHAR2(255 CHAR)
        MIDDLE_NAME                VARCHAR2(255 CHAR)
        LAST_NAME         NOT NULL VARCHAR2(255 CHAR)
        SCHOOL_ID                  NUMBER(8)
        SCHOOL_NAME                VARCHAR2(80)
        EXT_SCHOOL_NUMBER          VARCHAR2(16)
        LINE1                      VARCHAR2(60)
        LINE2                      VARCHAR2(60)
        CITY                       VARCHAR2(256)
        ZIPCODE                    VARCHAR2(9)
        GRADE_LEVEL       NOT NULL VARCHAR2(255 CHAR)
        SENT_DATE                  DATE
        TRIES                      NUMBER
        REQUEST_RESULT             NVARCHAR2(25)
    */

    private Long requestId;
    private String email;
    private Long extSchoolNumber;
    private String schoolName;
    private String gradeLevel;
    private Date sentDate;
    private String requestResult;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(String requestResult) {
        this.requestResult = requestResult;
    }
}