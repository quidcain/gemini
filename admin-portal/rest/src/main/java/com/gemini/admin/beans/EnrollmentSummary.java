package com.gemini.admin.beans;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/9/18
 * Time: 10:19 AM
 */
public class EnrollmentSummary {

    private int totalPreEnrollments;
    private int totalNewEntryEnrollments;
    private int totalConfirmed;
    private int totalDenied;
    private List<String> labels;
    private List<Integer> confirmedEnrollments;
    private List<Integer> deniedEnrollments;
    private List<Integer> newEntryEnrollments;

    public int getTotalPreEnrollments() {
        return totalPreEnrollments;
    }

    public void setTotalPreEnrollments(int totalPreEnrollments) {
        this.totalPreEnrollments = totalPreEnrollments;
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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Integer> getConfirmedEnrollments() {
        return confirmedEnrollments;
    }

    public void setConfirmedEnrollments(List<Integer> confirmedEnrollments) {
        this.confirmedEnrollments = confirmedEnrollments;
    }

    public List<Integer> getDeniedEnrollments() {
        return deniedEnrollments;
    }

    public void setDeniedEnrollments(List<Integer> deniedEnrollments) {
        this.deniedEnrollments = deniedEnrollments;
    }

    public List<Integer> getNewEntryEnrollments() {
        return newEntryEnrollments;
    }

    public void setNewEntryEnrollments(List<Integer> newEntryEnrollments) {
        this.newEntryEnrollments = newEntryEnrollments;
    }

    public boolean isEmpty() {
        return (confirmedEnrollments == null || confirmedEnrollments.isEmpty())
                && (deniedEnrollments == null || deniedEnrollments.isEmpty())
                && (newEntryEnrollments == null || newEntryEnrollments.isEmpty());
    }
}