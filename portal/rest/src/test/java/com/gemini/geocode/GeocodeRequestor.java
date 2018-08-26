package com.gemini.geocode;

import com.google.common.base.Joiner;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GeocodeRequestor {

    private RestTemplate template = new RestTemplate();

    public LatLng request(Address address) {
        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", buildAddressString(address), "AIzaSyDf-q5eFwF_yAlt24G15hz3wGepiXZKdMo");
        ResponseEntity<String> entity = template.getForEntity(url, String.class);
        try {
            JSONObject root = new JSONObject(entity.getBody());
            JSONObject geometry = root.getJSONArray("results").getJSONObject(0).getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            return new LatLng(lat, lng);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    private String buildAddressString(Address address) {
        return Joiner.on(", ").join(address.getAddressLine(), address.getCity(), address.getPostalCode());
    }
}
