package com.gemini.commons.beans.forms;


import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/20/18
 * Time: 10:23 PM
 */
public class PreEnrollmentAddressBean {

    private Long requestId;
    @Valid
    private AddressBean physical;
//    @Valid
//    private AddressBean postal;
    private Boolean needTransportation;

    public PreEnrollmentAddressBean() {
    }

    public PreEnrollmentAddressBean(Long preEnrollmentId) {
        this.requestId = preEnrollmentId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public AddressBean getPhysical() {
        return physical;
    }

    public void setPhysical(AddressBean physical) {
        this.physical = physical;
    }

    public Boolean getNeedTransportation() {
        return needTransportation;
    }

    public void setNeedTransportation(Boolean needTransportation) {
        this.needTransportation = needTransportation;
    }

    //
//    public AddressBean getPostal() {
//        return postal;
//    }
//
//    public void setPostal(AddressBean postal) {
//        this.postal = postal;
//    }

}