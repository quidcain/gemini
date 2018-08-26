package com.gemini.beans.requests;

import com.gemini.commons.beans.forms.EthnicCodeBean;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/27/18
 * Time: 11:47 PM
 */
public class StudentDemographicsRequest {
    private Long studentId;
    @NotNull
    private String language;
    @NotNull
    private String citizenship;
    @NotNull
    @NotEmpty
    private List<EthnicCodeBean> ethnicCodes;
    private List<EthnicCodeBean> ethnicCodesToDelete;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public List<EthnicCodeBean> getEthnicCodes() {
        return ethnicCodes;
    }

    public void setEthnicCodes(List<EthnicCodeBean> ethnicCodes) {
        this.ethnicCodes = ethnicCodes;
    }

    public List<EthnicCodeBean> getEthnicCodesToDelete() {
        return ethnicCodesToDelete;
    }

    public void setEthnicCodesToDelete(List<EthnicCodeBean> ethnicCodesToDelete) {
        this.ethnicCodesToDelete = ethnicCodesToDelete;
    }
}