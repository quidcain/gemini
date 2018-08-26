package com.gemini.admin.beans;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/11/18
 * Time: 1:50 PM
 */
public class SchedulingSummary {
    private List<String> labels;
    private List<String> labelIds;
    private List<Integer> asignadosTotales;
    private List<Integer> vacantesTotales;
    private List<Integer> excedentesTotales;
    private List<Integer> necesidadesTotales;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<String> labelIds) {
        this.labelIds = labelIds;
    }

    public List<Integer> getAsignadosTotales() {
        return asignadosTotales;
    }

    public void setAsignadosTotales(List<Integer> asignadosTotales) {
        this.asignadosTotales = asignadosTotales;
    }

    public List<Integer> getVacantesTotales() {
        return vacantesTotales;
    }

    public void setVacantesTotales(List<Integer> vacantesTotales) {
        this.vacantesTotales = vacantesTotales;
    }

    public List<Integer> getExcedentesTotales() {
        return excedentesTotales;
    }

    public void setExcedentesTotales(List<Integer> excedentesTotales) {
        this.excedentesTotales = excedentesTotales;
    }

    public List<Integer> getNecesidadesTotales() {
        return necesidadesTotales;
    }

    public void setNecesidadesTotales(List<Integer> necesidadesTotales) {
        this.necesidadesTotales = necesidadesTotales;
    }
}