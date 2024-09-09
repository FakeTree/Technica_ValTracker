package com.example.technica_valtracker.db.dao;

import com.example.technica_valtracker.db.model.Summoner;
import java.util.List;

/**
 * Interface for the Summoner Data Access Object that handles CRUD
 * operations for the Summoner table.
 */
public interface ISummonerDAO {
    /**
     * Add a new entry to the Summoner table.
     * @param summoner Summoner to be added.
     */
    public void addNewSummoner(Summoner summoner);
    /**
     * Update existing Summoner entry.
     * @param summoner Summoner to be updated.
     */
    public void updateSummoner(Summoner summoner);
    /**
     * Retrieve an existing Summoner entry by their userId.
     * @param userId User ID to retrieve Summoner from.
     */
    public Summoner getSummoner(String userId);
    /**
     * Retrieve all summoners from the database.
     * @return A list of all Summoners in the database.
     */
    public List<Summoner> getAllSummoners();
}
