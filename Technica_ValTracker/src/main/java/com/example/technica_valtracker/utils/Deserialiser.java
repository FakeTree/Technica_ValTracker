package com.example.technica_valtracker.utils;

import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.temp.TempResponseObject;
import com.example.technica_valtracker.db.model.Summoner;
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
     * Checks if the JSON string returned by getLeagueBySummonerId() is an array.
     * If there are multiple leagues,
     */
    public static boolean responseHasTwoLeagues(String json) {
        return true;
    }

    public static String[] getBothLeaguesFromJson(String json) {
        String solo_league = "solo";
        String flex_league = "flex";
        return new String[] {solo_league, flex_league};
    }
}
