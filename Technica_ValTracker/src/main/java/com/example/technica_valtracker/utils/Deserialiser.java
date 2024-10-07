package com.example.technica_valtracker.utils;

import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.db.model.Champion;
import com.example.technica_valtracker.db.model.League;
import com.example.technica_valtracker.db.model.Summoner;
import com.example.technica_valtracker.db.model.Match;
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
     * Converts JSON response body to a Summoner object.
     * @param json The JSON string to deserialise.
     * @param summoner The Summoner object to insert values into.
     */
    public static void getSummonerFromJson(String json, Summoner summoner) throws JsonProcessingException {
        summoner = objectMapper.readerForUpdating(summoner).readValue(json);
    }

    /**
     * Converts JSON Array containing the League entries to an array of League objects and returns it.
     * @param json The Json string to deserialise, must represent a response body array.
     * @return Array of League objects.
     * @throws JsonProcessingException
     */
    public static League[] getLeagueArrayFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, League[].class);
    }

    /**
     * Converts JSON response body to a Summoner object.
     * @param json The JSON string to deserialise.
     * @param match The Summoner object to insert values into.
     */
    public static void getMatchFromJson(String json, Match match) throws JsonProcessingException {
        match = objectMapper.readerForUpdating(match).readValue(json);
    }

    /** Converts JSON array containing the Champion Mastery response and returns an array of Champion objects.
     * @param json The JSON array string to deserialise.
     * @return Array of Champion objects.
     * @throws JsonProcessingException
     */
    public static Champion[] getChampionArrayFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Champion[].class);
    }
}
