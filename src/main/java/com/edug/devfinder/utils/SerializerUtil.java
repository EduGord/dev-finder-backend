package com.edug.devfinder.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SerializerUtil {

    private static ObjectMapper objectMapper;

    public SerializerUtil(@Qualifier("objectMapper") ObjectMapper objectMapper) {
        SerializerUtil.objectMapper = objectMapper;
    }

    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ignored) {
            return object.toString();
        }
    }
}
