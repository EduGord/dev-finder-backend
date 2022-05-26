package com.edug.devfinder.configs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ClassUtils;

@Configuration
public class ObjectMapperConfig {
    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        log.info("Customizing Application Object Mapper.");
        return new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
                        SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)
                .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS,
                        DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)
                .registerModules(
                        // Optionals
                        new Jdk8Module(),
                        // Dates/Times
                        new JavaTimeModule(),
                        // Json
                        new JodaModule())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules();
    }
}
