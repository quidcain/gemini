package com.gemini.commons.beans.responses;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/20/18
 * Time: 10:24 PM
 */
public class ResponseBase<T> {
    private Long requestId;
    private boolean successfulOperation;
    private boolean errorOperation;
    private boolean found;
    private boolean foundOnSIE;
    private boolean missingRequiredFields;
    private T content;
    private String titleMessage;
    private List<String> validationMessages = new ArrayList<>();

    public ResponseBase() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccessfulOperation() {
        return successfulOperation;
    }

    public void setSuccessfulOperation(boolean successfulOperation) {
        this.successfulOperation = successfulOperation;
    }

    public boolean isErrorOperation() {
        return errorOperation;
    }

    public void setErrorOperation(boolean errorOperation) {
        this.errorOperation = errorOperation;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public boolean isFoundOnSIE() {
        return foundOnSIE;
    }

    public void setFoundOnSIE(boolean foundOnSIE) {
        this.foundOnSIE = foundOnSIE;
    }

    public boolean isMissingRequiredFields() {
        return missingRequiredFields;
    }

    public void setMissingRequiredFields(boolean missingRequiredFields) {
        this.missingRequiredFields = missingRequiredFields;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public List<String> getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(List<String> validationMessages) {
        this.validationMessages = validationMessages;
    }

    public String getTitleMessage() {
        return titleMessage;
    }

    public void setTitleMessage(String titleMessage) {
        this.titleMessage = titleMessage;
    }

    public void setResponseBase(ResponseBase responseBase) {
        this.requestId = responseBase.requestId;
        this.successfulOperation = responseBase.successfulOperation;
        this.content = (T) responseBase.content;
        this.validationMessages = responseBase.validationMessages;
        this.errorOperation = responseBase.errorOperation;
    }

    public boolean hasError(){
        return this.validationMessages != null && !this.validationMessages.isEmpty();
    }

    public void addError(String message){
        if(this.validationMessages == null)
            this.validationMessages = new ArrayList<>();
        this.validationMessages.add(message);
    }

    public static ResponseBase success(Long requestId) {
        ResponseBase base = new ResponseBase();
        base.successfulOperation = true;
        base.requestId = requestId;
        return base;
    }

    public static ResponseBase success() {
        return success(null);
    }

    public static <T> ResponseBase success(Long requestId, T responseBean) {
        ResponseBase base = success(requestId);
        base.content = responseBean;
        return base;
    }

    public static <T> ResponseBase success(T responseBean) {
        return success(null, responseBean);
    }

    public static ResponseBase missingFields(List<String> validationMessages) {
        ResponseBase base = error("Favor cotejar los siguientes campos", validationMessages);
        base.missingRequiredFields = true;
        return base;
    }

    public static ResponseBase error(String titleMessage, List<String> validationMessages) {
        ResponseBase base = new ResponseBase();
        base.errorOperation = true;
        base.titleMessage = titleMessage;
        base.validationMessages = validationMessages;
        return base;
    }

    public static ResponseBase error(List<String> validationMessages) {
        ResponseBase base = new ResponseBase();
        base.errorOperation = true;
        base.validationMessages = validationMessages;
        return base;
    }

    public static ResponseBase error(String message) {
        return error(Lists.newArrayList(message));
    }

}