package com.gemini.geocode;

import com.google.common.base.Joiner;

public class GoogleGeocodeSample {

    public static void main(String[] args) {
        Address address = new Address("705, Calle Roosevelt", "San Juan", "00907");
        LatLng latLng = new GeocodeRequestor().request(address);
        System.out.println(Joiner.on(" -> ").join(address, latLng));
    }

}
