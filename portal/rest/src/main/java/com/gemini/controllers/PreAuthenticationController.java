package com.gemini.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/7/18
 * Time: 12:44 AM
 */
@RestController
@RequestMapping("/token")
public class PreAuthenticationController {

    @RequestMapping(method = RequestMethod.GET)
    private ResponseEntity token() {
        return new ResponseEntity(HttpStatus.OK);
    }
}