package com.gemini.utils;

import com.gemini.commons.beans.integration.StudentResponse;
import com.gemini.commons.beans.responses.ResponseBase;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/16/18
 * Time: 1:49 AM
 */
@Component
public class MessageHelper {


    @Autowired
    Environment env;

    public List<String> processMessages(String... message) {
        return FluentIterable
                .from(message)
                .transform(new Function<String, String>() {
                    @Override
                    public String apply(String key) {
                        return processMessage(key);
                    }
                }).toList();
    }

    public String generalError(){
        return env.getProperty("messages.general.unknown.error");
    }

    public String processMessage(String messageKey) {
        return env.getProperty("messages.".concat(messageKey));
    }

    public List<String> processRequest(BindingResult result) {
        Set<String> messages = new HashSet<>();
        for (FieldError field : result.getFieldErrors()) {
            String key = "fields.".concat(field.getField());
            messages.add(processMessage(key));
        }
        return Lists.newArrayList(messages);
    }

    public ResponseBase missingFormFields(BindingResult result, String... additionalMessage){
        List<String> msgs = processRequest(result);
        if(additionalMessage != null){
            msgs.addAll(Arrays.asList(additionalMessage));
        }
        return  ResponseBase.missingFields(msgs);
    }

    public StudentResponse missingFieldsOnStudentSearch(BindingResult result) {
        StudentResponse response = new StudentResponse();
        ResponseBase base = missingFormFields(result);
        response.setResponseBase(base);
        return response;
    }
}