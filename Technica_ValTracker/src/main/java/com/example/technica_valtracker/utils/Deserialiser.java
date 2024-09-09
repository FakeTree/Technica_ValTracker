package com.example.technica_valtracker.utils;

import com.example.technica_valtracker.api.error.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Collection of functions that convert JSON to objects.
 */
public class Deserialiser {
    static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts JSON to an ErrorMessage object.
     * @param json The JSON string to deserialise.
     * @return ErrorMessage object populated with values from the JSON.
     */
    public static ErrorMessage getErrorMessageFromJson(String json) throws JsonProcessingException {
        ErrorMessage error = new ErrorMessage();

        error = objectMapper.readerForUpdating(error).readValue(json);
        return error;
    }


}
