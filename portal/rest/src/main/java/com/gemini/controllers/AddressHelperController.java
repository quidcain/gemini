package com.gemini.controllers;

import com.gemini.beans.json.CoordinatesResponse;
import com.gemini.commons.beans.forms.AddressBean;
import com.gemini.services.CommonService;
import com.gemini.services.GoogleMapsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/21/18
 * Time: 5:25 PM
 */
@RestController
@RequestMapping("/address/helper")
public class AddressHelperController {

    static Logger logger = LoggerFactory.getLogger(AddressHelperController.class.getName());


    @Autowired
    private GoogleMapsService googleMapsService;
    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/geocode", method = RequestMethod.POST)
    public ResponseEntity<CoordinatesResponse> geoCodeAddress(@RequestBody AddressBean addressBean) {

        CoordinatesResponse response = googleMapsService.doGeoCodeAddress(addressBean);

        if (StringUtils.hasText(addressBean.getZipcode())) {
            String code = commonService.getCountyCode(addressBean.getZipcode());
            response.setCountyCode(code);
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/retrieve/county/code/{zipcode}")
    public String retrieveCountyCodeByZipCode(@PathVariable(value = "zipcode") String zipcode) {
        return commonService.getCountyCode(zipcode);
    }


}