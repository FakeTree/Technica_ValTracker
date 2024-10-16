package com.example.technica_valtracker.db.model;

import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a match in the game. Contains match metadata and information such as participants, game mode, and game results.
 * This class is used to deserialize the JSON response from the API for match data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {

    private Metadata metadata;
    private Info info;

    /**
     * Gets the metadata of the match.
     *
     * @return the match metadata.
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata of the match.
     *
     * @param metadata the metadata to set.
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Gets the information of the match.
     *
     * @return the match information.
     */
    public Info getInfo() {
        return info;
    }

    /**
     * Sets the information of the match.
     *
     * @param info the information to set.
     */
    public void setInfo(Info info) {
        this.info = info;
    }

    /**
     * Retrieves the index of a participant in the match by their PUUID.
     *
     * @param participants the list of participants in the match.
     * @param puuid        the PUUID of the participant to search for.
     * @return the index of the participant in the list, or -1 if not found.
     */
    public static int getParticipantIndexByPuuid(List<Match.Participant> participants, String puuid) {
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).getPuuid().equals(puuid)) {
                return i; // Return index of matched participant
            }
        }
        return -1; // Return -1 if not found
    }

    /**
     * Represents the metadata of a match, including data version, match ID, and participants.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metadata {
        private String dataVersion;
        private String matchId;
        private List<String> participants;

        /**
         * Gets the data version of the match.
         *
         * @return the data version.
         */
        public String getDataVersion() {
            return dataVersion;
        }

        /**
         * Sets the data version of the match.
         *
         * @param dataVersion the data version to set.
         */
        public void setDataVersion(String dataVersion) {
            this.dataVersion = dataVersion;
        }

        /**
         * Gets the match ID.
         *
         * @return the match ID.
         */
        public String getMatchId() {
            return matchId;
        }

        /**
         * Sets the match ID.
         *
         * @param matchId the match ID to set.
         */
        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        /**
         * Gets the list of participants in the match.
         *
         * @return the list of participants.
         */
        public List<String> getParticipants() {
            return participants;
        }

        /**
         * Sets the list of participants in the match.
         *
         * @param participants the list of participants to set.
         */
        public void setParticipants(List<String> participants) {
            this.participants = participants;
        }
    }

    /**
     * Represents detailed information about the match, including game mode, game duration, and participants.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Info {
        private String endOfGameResult;
        private long gameCreation;
        private long gameDuration;
        private long gameEndTimestamp;
        private long gameId;
        private String gameMode;
        private String gameName;
        private long gameStartTimestamp;
        private String gameType;
        private String gameVersion;
        private int mapId;
        private List<Participant> participants;
        private int queueId;
        @JsonIgnore
        private String queueMode;

        /**
         * Gets the result at the end of the game.
         *
         * @return the end of game result.
         */
        public String getEndOfGameResult() {
            return endOfGameResult;
        }

        /**
         * Sets the result at the end of the game.
         *
         * @param endOfGameResult the result to set.
         */
        public void setEndOfGameResult(String endOfGameResult) {
            this.endOfGameResult = endOfGameResult;
        }

        /**
         * Gets the game creation time as a timestamp.
         *
         * @return the game creation time.
         */
        public long getGameCreation() {
            return gameCreation;
        }

        /**
         * Sets the game creation time as a timestamp.
         *
         * @param gameCreation the game creation time to set.
         */
        public void setGameCreation(long gameCreation) {
            this.gameCreation = gameCreation;
        }

        /**
         * Gets the duration of the game in milliseconds.
         *
         * @return the game duration.
         */
        public long getGameDuration() {
            return gameDuration;
        }

        /**
         * Sets the duration of the game in milliseconds.
         *
         * @param gameDuration the game duration to set.
         */
        public void setGameDuration(long gameDuration) {
            this.gameDuration = gameDuration;
        }

        /**
         * Gets the game end timestamp.
         *
         * @return the game end timestamp.
         */
        public long getGameEndTimestamp() {
            return gameEndTimestamp;
        }

        /**
         * Sets the game end timestamp.
         *
         * @param gameEndTimestamp the timestamp to set.
         */
        public void setGameEndTimestamp(long gameEndTimestamp) {
            this.gameEndTimestamp = gameEndTimestamp;
        }

        /**
         * Gets the unique game ID.
         *
         * @return the game ID.
         */
        public long getGameId() {
            return gameId;
        }

        /**
         * Sets the unique game ID.
         *
         * @param gameId the game ID to set.
         */
        public void setGameId(long gameId) {
            this.gameId = gameId;
        }

        /**
         * Gets the game mode.
         *
         * @return the game mode.
         */
        public String getGameMode() {
            return gameMode;
        }

        /**
         * Sets the game mode.
         *
         * @param gameMode the game mode to set.
         */
        public void setGameMode(String gameMode) {
            this.gameMode = gameMode;
        }

        /**
         * Gets the name of the game.
         *
         * @return the game name.
         */
        public String getGameName() {
            return gameName;
        }

        /**
         * Sets the name of the game.
         *
         * @param gameName the game name to set.
         */
        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        /**
         * Gets the game start timestamp.
         *
         * @return the game start timestamp.
         */
        public long getGameStartTimestamp() {
            return gameStartTimestamp;
        }

        /**
         * Sets the game start timestamp.
         *
         * @param gameStartTimestamp the game start timestamp to set.
         */
        public void setGameStartTimestamp(long gameStartTimestamp) {
            this.gameStartTimestamp = gameStartTimestamp;
        }

        /**
         * Gets the game type.
         *
         * @return the game type.
         */
        public String getGameType() {
            return gameType;
        }

        /**
         * Sets the game type.
         *
         * @param gameType the game type to set.
         */
        public void setGameType(String gameType) {
            this.gameType = gameType;
        }

        /**
         * Gets the game version.
         *
         * @return the game version.
         */
        public String getGameVersion() {
            return gameVersion;
        }

        /**
         * Sets the game version.
         *
         * @param gameVersion the game version to set.
         */
        public void setGameVersion(String gameVersion) {
            this.gameVersion = gameVersion;
        }

        /**
         * Gets the map ID where the game was played.
         *
         * @return the map ID.
         */
        public int getMapId() {
            return mapId;
        }

        /**
         * Sets the map ID where the game was played.
         *
         * @param mapId the map ID to set.
         */
        public void setMapId(int mapId) {
            this.mapId = mapId;
        }

        /**
         * Gets the list of participants in the match.
         *
         * @return the list of participants.
         */
        public List<Participant> getParticipants() {
            return participants;
        }

        /**
         * Sets the list of participants in the match.
         *
         * @param participants the participants to set.
         */
        public void setParticipants(List<Participant> participants) {
            this.participants = participants;
        }

        /**
         * Gets the queueId of the match.
         * @return The queueId as an integer.
         */
        public int getQueueId() { return queueId; }

        /**
         * Sets the queueId of the match.
         * @param queueId The queueId to set.
         */
        public void setQueueId(int queueId) { this.queueId = queueId; }

        /**
         * Gets the queueMode of the match.
         * @return The queue mode as a String.
         */
        public String getQueueMode() { return queueMode; }

        /**
         * Sets the queueMode based on the queueId.
         */
        public void setQueueMode() {
            if (queueId == 420) {
                queueMode = "Ranked Solo";
            }
            else if (queueId == 440) {
                queueMode = "Ranked Flex";
            }
            else {
                queueMode = "N/A";
            }
        }



    }

    /**
     * Represents a participant in the match, including details such as champion name, kills, deaths, assists, and whether they won the game.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Participant {
        private int assists;
        private int baronKills;
        private int deaths;
        private int kills;
        private int goldEarned;
        private int championId;
        private String championName;
        private String puuid;
        private Challenges challenges; // Change from Map to Challenges class
        private int totalMinionsKilled;
        private int neutralMinionsKilled;
        @JsonIgnore
        private int minionKillTotal;
        private boolean win;

        /**
         * Gets the number of assists the participant achieved.
         *
         * @return the number of assists.
         */
        public int getAssists() {
            return assists;
        }

        /**
         * Sets the number of assists the participant achieved.
         *
         * @param assists the number of assists to set.
         */
        public void setAssists(int assists) {
            this.assists = assists;
        }

        /**
         * Gets the number of baron kills by the participant.
         *
         * @return the number of baron kills.
         */
        public int getBaronKills() {
            return baronKills;
        }

        /**
         * Sets the number of baron kills by the participant.
         *
         * @param baronKills the number of baron kills to set.
         */
        public void setBaronKills(int baronKills) {
            this.baronKills = baronKills;
        }

        /**
         * Gets the number of deaths the participant had.
         *
         * @return the number of deaths.
         */
        public int getDeaths() {
            return deaths;
        }

        /**
         * Sets the number of deaths the participant had.
         *
         * @param deaths the number of deaths to set.
         */
        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }

        /**
         * Gets the number of kills completed by the participant.
         *
         * @return the number of kills.
         */
        public int getKills() {
            return kills;
        }

        /**
         * Sets the number of kills completed by the participant.
         *
         * @param kills the number of kills to set.
         */
        public void setKills(int kills) {
            this.kills = kills;
        }

        /**
         * Sets the champion ID the participant played.
         * @return The integer indicating champion ID.
         */
        public int getChampionId() { return championId; }

        /**
         * Gets the champion ID the participant played.
         * @param championID The integer of the champion ID to set.
         */
        public void setChampionId(int championID) { this.championId = championID; }

        /**
         * Gets the name of the champion the participant played.
         *
         * @return the champion name.
         */
        public String getChampionName() {
            return championName;
        }

        /**
         * Sets the name of the champion the participant played.
         *
         * @param championName the champion name to set.
         */
        public void setChampionName(String championName) {
            this.championName = championName;
        }

        /**
         * Gets the PUUID (Player Unique ID) of the participant.
         *
         * @return the PUUID of the participant.
         */
        public String getPuuid() {
            return puuid;
        }

        /**
         * Sets the PUUID (Player Unique ID) of the participant.
         *
         * @param puuid the PUUID to set.
         */
        public void setPuuid(String puuid) {
            this.puuid = puuid;
        }

        /**
         * Gets the amount of gold earned by the participant.
         *
         * @return the gold earned.
         */
        public int getGoldEarned() {
            return goldEarned;
        }

        /**
         * Sets the amount of gold earned by the participant.
         *
         * @param goldEarned the gold earned to set.
         */
        public void setGoldEarned(int goldEarned) {
            this.goldEarned = goldEarned;
        }

        /**
         * Gets the number of total minions killed by the participant.
         *
         * @return the number of minions killed.
         */
        public int gettotalMinionsKilled() {
            return totalMinionsKilled;
        }

        /**
         * Sets the number of total minions killed by the participant.
         *
         * @param totalMinionsKilled the number of minions killed to set.
         */
        public void settotalMinionsKilled(int totalMinionsKilled) {
            this.totalMinionsKilled = totalMinionsKilled;
        }

        /**
         * Gets the number of neutral minions killed by the participant.
         * @return the number of neutral minions killed.
         */
        public int getNeutralMinionsKilled() {
            return neutralMinionsKilled;
        }

        /**
         * Sets the number of neutral minions killed by the participant.
         * @param neutralMinionsKilled the number of neutral minions killed to set.
         */
        public void setNeutralMinionsKilled(int neutralMinionsKilled) {
            this.neutralMinionsKilled = neutralMinionsKilled;
        }

        /**
         * Get total number of creeps killed (totalMinionsKilled + neutralMinionsKilled)
         */
        public int getMinionKillTotal() { return minionKillTotal; }

        /**
         * Calculate and set total number of creeps killed (totalMinionsKilled + neutralMinionsKilled)
         */
        public void setMinionKillTotal() {
            minionKillTotal = totalMinionsKilled + neutralMinionsKilled;
        }


        /**
         * Gets the challenges completed by the participant.
         *
         * @return the challenges.
         */
        public Challenges getChallenges() {
            return challenges;
        }

        /**
         * Sets the challenges completed by the participant.
         *
         * @param challenges the challenges to set.
         */
        public void setChallenges(Challenges challenges) {
            this.challenges = challenges;
        }

        /**
         * Gets whether the participant won the match.
         *
         * @return true if the participant won, false otherwise.
         */
        public Boolean getWin() {
            return win;
        }

        /**
         * Sets whether the participant won the match.
         *
         * @param win true if the participant won, false otherwise.
         */
        public void setWin(Boolean win) {
            this.win = win;
        }
    }

    /**
     * Represents various challenges completed by the participant, such as KDA, assists, kills, and deaths.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Challenges {
        private double kda;
        private int assists;
        private int kills;
        private int deaths;

        /**
         * Gets the participant's KDA (Kills, Deaths, Assists) ratio.
         *
         * @return the KDA ratio.
         */
        public double getKda() {
            return kda;
        }

        /**
         * Sets the participant's KDA (Kills, Deaths, Assists) ratio.
         *
         * @param kda the KDA ratio to set.
         */
        public void setKda(double kda) {
            this.kda = kda;
        }

        /**
         * Gets the number of assists completed by the participant.
         *
         * @return the number of assists.
         */
        public int getAssists() {
            return assists;
        }

        /**
         * Sets the number of assists completed by the participant.
         *
         * @param assists the number of assists to set.
         */
        public void setAssists(int assists) {
            this.assists = assists;
        }

        /**
         * Gets the number of kills completed by the participant.
         *
         * @return the number of kills.
         */
        public int getKills() {
            return kills;
        }

        /**
         * Sets the number of kills completed by the participant.
         *
         * @param kills the number of kills to set.
         */
        public void setKills(int kills) {
            this.kills = kills;
        }

        /**
         * Gets the number of deaths the participant had in the match.
         *
         * @return the number of deaths.
         */
        public int getDeaths() {
            return deaths;
        }

        /**
         * Sets the number of deaths the participant had in the match.
         *
         * @param deaths the number of deaths to set.
         */
        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }
    }
}
