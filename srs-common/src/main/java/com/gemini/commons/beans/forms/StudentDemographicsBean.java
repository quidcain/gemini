package com.gemini.commons.beans.forms;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/28/18
 * Time: 12:56 AM
 */
public class StudentDemographicsBean {

    private String language;
    private String citizenship;
    private List<EthnicCodeBean> ethnicCodes;

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
}