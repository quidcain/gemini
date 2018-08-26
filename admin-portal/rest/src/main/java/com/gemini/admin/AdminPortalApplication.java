package com.gemini.admin;

import com.gemini.admin.security.SiePasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = {"com.gemini", "com.gemini.commons"})
@EntityScan("com.gemini")
@EnableJpaRepositories("com.gemini")
@EnableConfigurationProperties
@EnableWebSecurity
@EnableTransactionManagement
public class AdminPortalApplication extends SpringBootServletInitializer {

    final static Logger logger = LoggerFactory.getLogger(AdminPortalApplication.class.getName());
    /*
            TMAX1O -> /tmax1o/ias/srs/admin/
            PMAX1O -> /pmax1o/ias/srs/admin/
            DEV -> /home/ubuntu/srs/admin/
     */
    static final String PROPS_DIR;

    static {
        System.out.println("Before Setting default timezone :" + new Date());
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        TimeZone.setDefault(TimeZone.getTimeZone("America/Puerto_Rico"));
        System.out.println("Before setting timezone :" + new Date());


        logger.info("***Getting props from System***");
        if (StringUtils.hasText(System.getProperty("srs.admin.config.path")))
            PROPS_DIR = System.getProperty("srs.admin.config.path");
        else
            PROPS_DIR = "/home/ubuntu/srs/admin/";
        logger.info(String.format("***config location path in use is: %s***", "spring.config.location:".concat(PROPS_DIR)));
    }

    public static void main(String[] args) {
        final String[] a = new String[args.length + 1];

        a[0] = "--spring.config.location=".concat(PROPS_DIR);
        System.arraycopy(args, 0, a, 1, args.length);
        SpringApplication.run(AdminPortalApplication.class, a);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        logger.info("***Starting admin services from servlet***");
        return application
                .sources(AdminPortalApplication.class)
                .properties("spring.config.location:".concat(PROPS_DIR));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SiePasswordEncoder();
    }

    @Bean("portalEncoder")
    public PasswordEncoder portalEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public Locale locale() {
        return Locale.forLanguageTag("es-PR");
    }

}
