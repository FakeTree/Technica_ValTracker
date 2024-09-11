package com.example.technica_valtracker.utils;

import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.temp.TempResponseObject;
import com.example.technica_valtracker.db.model.League;
import com.example.technica_valtracker.db.model.Summoner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

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

    /**
     * Converts ErrorMessage object to JSON.
     * @param error The ErrorMessage to convert.
     * @return The converted ErrorMessage JSON string.
     */
    public static String errorMessageToJson(ErrorMessage error) throws JsonProcessingException {
        return objectMapper.writeValueAsString(error);
    }

    /**
     * Converts JSON to a TempResponseObject.
     * @param json The JSON string to deserialise.
     * @param tempObject The TempResponseObject to insert values to.
     */
    public static void getTempResponseObjectFromJson(String json, TempResponseObject tempObject) throws JsonProcessingException {
        tempObject = objectMapper.readerForUpdating(tempObject).readValue(json);
    }

    /**
     * Converts JSON response body to a Summoner object.
     * @param json The JSON string to deserialise.
     * @param summoner The Summoner object to insert values into.
     */
    public static void getSummonerFromJson(String json, Summoner summoner) throws JsonProcessingException {
        summoner = objectMapper.readerForUpdating(summoner).readValue(json);
    }

    /**
     * Converts JSON Array response body to a JSON list and returns it.
     * @param json The Json string to deserialise, must represent a response body array.
     * @return List of object type League
     * @throws JsonProcessingException
     */
    public static League[] getLeagueArrayFromJson(String json) throws JsonProcessingException {
        League[] leagues = objectMapper.readValue(json, League[].class);
        return leagues;
    }

}
