package com.example.technica_valtracker.db.dao;

import com.example.technica_valtracker.db.model.Match;

import java.util.List;

/**
 * Interface for the Match Data Access Object that handles CRUD
 * operations for the match_history table.
 */
public interface IMatchHistoryDAO {
    /**
     * Add a new entry to the Match table.
     * @param match The match to add.
     */
    public void addNewMatch(Match match);

    // TODO: IMatchHistoryDAO: Get match by match ID?

    /**
     * Retrieves all matches played by a specific user.
     * @param userId The id of the user to retrieve matches from.
     * @return A list of all matches with the given ID in the database.
     */
    public List<Match> getAllMatchesById(String userId);

    /**
     * Retrieves all matches of registered users in the database.
     * @return A list of all recorded matches.
     */
    public List<Match>  getAllMatches();
}
