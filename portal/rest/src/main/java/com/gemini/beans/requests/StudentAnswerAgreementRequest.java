package com.gemini.beans.requests;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 7/11/18
 * Time: 9:23 AM
 */
public class StudentAnswerAgreementRequest {
    private Long studentId;
    @NotNull
    private Boolean answer;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }
}