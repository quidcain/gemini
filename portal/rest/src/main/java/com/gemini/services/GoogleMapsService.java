package com.gemini.services;

import com.gemini.beans.json.CoordinatesResponse;
import com.gemini.commons.beans.forms.AddressBean;
import com.gemini.commons.database.jpa.entities.AddressEntity;
import com.gemini.controllers.PreEnrollmentRequestController;
import com.google.common.base.Joiner;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/20/18
 * Time: 8:44 AM
 */
@Service
public class GoogleMapsService {

    static Logger logger = LoggerFactory.getLogger(GoogleMapsService.class.getName());


    @Autowired
    @Qualifier("httpsRestTemplate")
    private RestTemplate httpsRestTemplate;

    public CoordinatesResponse doGeoCodeAddress(AddressEntity address) {
        return doGeoCodeAddress(buildAddressString(address));
    }

    public CoordinatesResponse doGeoCodeAddress(AddressBean address) {
        return doGeoCodeAddress(buildAddressString(address));

    }

    public CoordinatesResponse doGeoCodeAddress(String addressString) {
        addressString = addressString.replaceAll("#",  "");
        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", addressString, "AIzaSyDf-q5eFwF_yAlt24G15hz3wGepiXZKdMo");
        ResponseEntity<String> entity = httpsRestTemplate.getForEntity(url, String.class);
        try {
            JSONObject root = new JSONObject(entity.getBody());
            JSONObject geometry = root.getJSONArray("results").getJSONObject(0).getJSONObject("geometry");
 /*

                JSONArray addressComponentArray = root
                        .getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONArray("address_components");

                if (addressComponentArray != null && addressComponentArray.length() > 1) {
                    String type = addressComponentArray
                            .getJSONObject(1)
                            .getJSONArray("types")
                            .getJSONObject(0)
                            .getString("");
                    if ("route".equals(type)) {
                        String city = addressComponentArray.getJSONObject(1).getString("long_name");
                    }
                }
*/
            JSONObject location = geometry.getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            return new CoordinatesResponse(lat, lng);
        } catch (JSONException e) {
//            throw new IllegalStateException(e);
            logger.error("error geocode", e);
        }
        return null;
    }


    private String buildAddressString(AddressEntity address) {
        return Joiner.on(", ").join(address.getLine1(), address.getZipcode(), "PR");
    }

    private String buildAddressString(AddressBean address) {
        return Joiner.on(", ").join(address.getLine1(), address.getZipcode(), "PR");
    }


}