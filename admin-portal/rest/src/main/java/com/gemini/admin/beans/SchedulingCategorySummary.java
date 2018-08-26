package com.gemini.admin.beans;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/11/18
 * Time: 1:58 PM
 */
public class SchedulingCategorySummary {
    private List<String> labels;
    private List<Integer> categoryTotals;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Integer> getCategoryTotals() {
        return categoryTotals;
    }

    public void setCategoryTotals(List<Integer> categoryTotals) {
        this.categoryTotals = categoryTotals;
    }
}