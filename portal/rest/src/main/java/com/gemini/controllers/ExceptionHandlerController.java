package com.gemini.controllers;

import com.gemini.commons.beans.responses.ResponseBase;
import com.gemini.utils.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/27/18
 * Time: 1:06 PM
 */
//Need enable MVC
//@ControllerAdvice(basePackageClasses = UserController.class)
public class ExceptionHandlerController {

    @Autowired
    private MessageHelper messageHelper;

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseBase handleException(MethodArgumentNotValidException exception) {
        return ResponseBase.error("Campos Requeridos", messageHelper.processRequest(exception.getBindingResult()));
    }

}
