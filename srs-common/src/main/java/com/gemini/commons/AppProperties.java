package com.gemini.commons;

import com.gemini.commons.database.dao.impl.MockSchoolMaxDaoImpl;
import com.gemini.commons.database.dao.impl.SchoolMaxDaoImpl;
import com.gemini.commons.database.dao.SchoolMaxDaoInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/19/18
 * Time: 6:03 PM
 */
@Configuration
public class AppProperties {

    static Logger logger = LoggerFactory.getLogger(AppProperties.class.getName());

    @Value("${property.load.test}")
    String value;

    @PostConstruct
    public void init() {
        logger.info("Test Property Load from server path: " + value);
    }

    @Bean
    @ConditionalOnProperty(value = "smax.interface.use", havingValue = "mock")
    public SchoolMaxDaoInterface mockSmax() {
        return new MockSchoolMaxDaoImpl();
    }

    @Bean
    @ConditionalOnProperty(value = "smax.interface.use", havingValue = "real", matchIfMissing = true)
    public SchoolMaxDaoInterface prodMax() {
        return new SchoolMaxDaoImpl();
    }
}