package com.example.technica_valtracker.utils;

import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.db.model.Champion;
import com.example.technica_valtracker.db.model.League;
import com.example.technica_valtracker.db.model.Summoner;
import com.example.technica_valtracker.db.model.Match;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
     */
    public static League[] getLeagueArrayFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, League[].class);
    }

    /**
     * Converts JSON Array containing the League entries to an array of League objects and returns it.
     *
     * @param json The Json string to deserialise, must represent a response body array.
     * @return Array of League objects.
     */
    public static Match getMatchArrayFromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Match matchQuery = objectMapper.readValue(json, Match.class);
        return matchQuery;
    }

    /** Converts JSON array containing the Champion Mastery response and returns an array of Champion objects.
     * @param json The JSON array string to deserialise.
     * @return Array of Champion objects.
     */
    public static Champion[] getChampionArrayFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Champion[].class);
    }

    /** Converts JSON Array containing match IDs into a Java List object.
     * @param json The JSON array string to deserialise.
     * @return List of match ids.
     */
    public static List<String> getMatchIdListFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<>(){});
    }
}
