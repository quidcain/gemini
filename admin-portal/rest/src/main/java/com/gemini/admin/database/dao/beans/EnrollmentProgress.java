package com.gemini.admin.database.dao.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/9/18
 * Time: 10:24 AM
 */
public class EnrollmentProgress {

    private String actionDate;
    private int totalNewEntryEnrollments;
    private int totalConfirmed;
    private int totalDenied;

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public int getTotalNewEntryEnrollments() {
        return totalNewEntryEnrollments;
    }

    public void setTotalNewEntryEnrollments(int totalNewEntryEnrollments) {
        this.totalNewEntryEnrollments = totalNewEntryEnrollments;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public int getTotalDenied() {
        return totalDenied;
    }

    public void setTotalDenied(int totalDenied) {
        this.totalDenied = totalDenied;
    }
}