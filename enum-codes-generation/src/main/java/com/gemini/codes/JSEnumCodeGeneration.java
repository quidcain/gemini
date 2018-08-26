//package com.gemini.codes;
//
//import com.gemini.codes.services.CodeService;
//import org.apache.http.HttpHost;
//import org.apache.http.conn.ssl.DefaultHostnameVerifier;
//import org.apache.http.conn.util.PublicSuffixMatcherLoader;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.http.client.support.BasicAuthorizationInterceptor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.util.FileSystemUtils;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// * User: fran
// * Date: 2/5/18
// * Time: 4:46 PM
// */
//
//@SpringBootApplication
//public class JSEnumCodeGeneration implements CommandLineRunner {
//    private static Logger logger = Logger.getLogger(JSEnumCodeGeneration.class);
//
//
//    @Autowired
//    CodeService codeService;
//    @Autowired
//    JavaMailSender mailSender;
//
//    public static void main(String[] args) {
//        SpringApplication.run(JSEnumCodeGeneration.class, args);
//    }
//
//    @Override
//    public void run(String... args) {
//               doConstruct(args);
////        Authenticator.setDefault(new ProxyAuthenticator("", ""));
////        System.setProperty("http.proxyHost", "");
////        System.setProperty("http.proxyPort", "80");
////        testMailService(args);
//    }
//
//    private void testMailService(String... args) {
//        HttpHost proxy = new HttpHost("omcs-proxy.oracleoutsourcing.com", 80);
//        CloseableHttpClient httpClient = HttpClients
//                .custom()
//                .setSSLHostnameVerifier(new DefaultHostnameVerifier(PublicSuffixMatcherLoader.getDefault()))
//                .setProxy(proxy)
//                .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        requestFactory.setConnectTimeout(1000 * 60 * 2);
//        requestFactory.setReadTimeout(1000 * 60 * 2);
////        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("username", "password"));
//
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("from", "DE-SIE@de.gov.pr");
//        body.add("to", "paloufran@gmail.com");
//        body.add("subject", "Test from DE SERVERS");
//        body.add("text", "Hola, \n Ya estamos corriendo con MAIL GUN");
//        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//        headers.add("api", "key-32fc2654d7a2502e4325870c7f38298f");
//
//        Response response = restTemplate.exchange(
//                "https://api.mailgun.net/v3/sandboxeaedcf7bf8a6427ba38ae4672180dabf.mailgun.org/messages",
//                HttpMethod.POST,
//                new HttpEntity<Object>(body, headers),
//                Response.class).getBody();
//
//        System.out.println(String.format("Response -> %s", response));
//
//    }
//
//    static class Response {
//        String id;
//        String message;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//
//        @Override
//        public String toString() {
//            return "{" +
//                    "id='" + id + '\'' +
//                    ", message='" + message + '\'' +
//                    '}';
//        }
//    }
//
////    public static JsonNode sendSimpleMessage() throws UnirestException {
////
////        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
////                .basicAuth("api", API_KEY)
////                .queryString("from", "Excited User <USER@YOURDOMAIN.COM>")
////                .queryString("to", "artemis@example.com")
////                .queryString("subject", "hello")
////                .queryString("text", "testing")
////                .asJson();
////
////        return request.getBody();
////    }
//
//    private void doConstruct(String... args) {
//        System.out.println(new File(".").getAbsoluteFile());
//        String base = new File(".").getAbsoluteFile().getPath().replace(".", "");
////        if (args == null || args.length == 0)
////            throw new RuntimeException("Process cannot run if there is not a directory output specify");
//        String location = base.concat("portal/webapp/src/components/data/codes");
//        System.out.println(location);
//        FileSystemUtils.deleteRecursively(new File(location + "/"));
//        new File(location).mkdir();
//
//        constructCodeJS(location, "residentialStatus", "ENUM_CE_RESIDENTIAL_STATUS");
//        constructCodeJS(location, "municipios", "ENUM_CE_MUNICIPALITY_CODE");
//        constructCodeJS(location, "relationTypes", "ENUM_CE_RELATION_CODE");
//        constructCodeJS(location, "ethnicCodes", "ENUM_CE_ETHNIC_CODE");
//        constructCodeJS(location, "countries", "ENUM_CE_BIRTH_COUNTRY");
//        constructCodeJS(location, "states", "ENUM_CE_BIRTH_STATE");
//        constructCodeJS(location, "educationLevels", "ENUM_CE_EDUCATION_CODE");
//        constructCodeJS(location, "disabilityCodes", "ENUM_SE_DISABILITY_CODES");
//        constructCodeJS(location, "languageCodes", "ENUM_CE_LANGUAGE_CODE");
//        constructCodeJS(location, "transportationTypes", "ENUM_ST_TRANSPORTATION_TYPE");
//        constructCodeJS(location, "contactTypes", "ENUM_ST_CONTACT_TYPE");
//        constructCodeJS(location, "schoolTypes", "ENUM_SY_SCHOOL_TYPE");
//        constructCodeJS(location, "foodOptions", "ENUM_CE_ECONOMY_CODE");
//        constructCodeJS(location, "medicalConditions", "ENUM_HE_ALERT_TYPE");
//        constructCodeJS(location, "medicationCodes", "ENUM_HE_MEDICATION_CODE");
//        constructCodeJS(location, "medicationDosages", "ENUM_HE_MEDICATION_DOSAGES");
//        constructCodeJS(location, "medicationRoutes", "ENUM_HE_MEDICATION_ROUTES");
//        constructCodeJS(location, "jobType", "ENUM_CE_EMPLOYER_STATUS");
//    }
//
//    private void constructCodeJS(String baseLocation, String jsCodeName, String enumTable) {
//        List<String> codes = codeService.getCodesForTable(enumTable);
//
//        for (String code : codes) {
//            Path path = Paths.get(String.format("%s/%s.js", baseLocation, jsCodeName));
//            try {
//                int index = codes.indexOf(code);
//                int lastIndex = codes.size() - 1;
//                String line = index == 0
//                        ? String.format("module.exports =  [", jsCodeName).concat(code)
//                        : code;
//                line = line.concat(index < lastIndex ? "," : "];").concat("\n");
//                Files.write(path, line.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//            } catch (Exception e) {
//                logger.error("error writing file " + e);
//            }
//        }
//
//
//    }
//
//}
