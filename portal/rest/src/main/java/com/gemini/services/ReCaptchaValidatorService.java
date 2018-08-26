package com.gemini.services;

import com.gemini.beans.json.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/1/18
 * Time: 6:17 PM
 */
@Service
public class ReCaptchaValidatorService {
    @Autowired
    @Qualifier("httpsRestTemplate")
    private RestTemplate httpsRestTemplate;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";

    @Value("${google.recaptcha.secret}")
    private String secret;

    @Value("${google.recaptcha.active:true}")
    private boolean active;


    public boolean verifyReCaptcha(String recaptchaToken) {
        if (!active) return true;

        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("secret", secret);
        body.add("response", recaptchaToken);

        ReCaptchaResponse response =
                httpsRestTemplate.postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL, body, ReCaptchaResponse.class).getBody();
        return response.isSuccess();
    }

}