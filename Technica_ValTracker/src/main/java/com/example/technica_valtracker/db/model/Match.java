package com.example.technica_valtracker.db.model;

import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {

    private Metadata metadata;
    private Info info;

    // Getters and Setters
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metadata {
        private String dataVersion;
        private String matchId;
        private List<String> participants;

        // Getters and Setters
        public String getDataVersion() {
            return dataVersion;
        }

        public void setDataVersion(String dataVersion) {
            this.dataVersion = dataVersion;
        }

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public List<String> getParticipants() {
            return participants;
        }

        public void setParticipants(List<String> participants) {
            this.participants = participants;
        }
    }

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

        // Getters and Setters
        public String getEndOfGameResult() {
            return endOfGameResult;
        }

        public void setEndOfGameResult(String endOfGameResult) {
            this.endOfGameResult = endOfGameResult;
        }

        public long getGameCreation() {
            return gameCreation;
        }

        public void setGameCreation(long gameCreation) {
            this.gameCreation = gameCreation;
        }

        public long getGameDuration() {
            return gameDuration;
        }

        public void setGameDuration(long gameDuration) {
            this.gameDuration = gameDuration;
        }

        public long getGameEndTimestamp() {
            return gameEndTimestamp;
        }

        public void setGameEndTimestamp(long gameEndTimestamp) {
            this.gameEndTimestamp = gameEndTimestamp;
        }

        public long getGameId() {
            return gameId;
        }

        public void setGameId(long gameId) {
            this.gameId = gameId;
        }

        public String getGameMode() {
            return gameMode;
        }

        public void setGameMode(String gameMode) {
            this.gameMode = gameMode;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public long getGameStartTimestamp() {
            return gameStartTimestamp;
        }

        public void setGameStartTimestamp(long gameStartTimestamp) {
            this.gameStartTimestamp = gameStartTimestamp;
        }

        public String getGameType() {
            return gameType;
        }

        public void setGameType(String gameType) {
            this.gameType = gameType;
        }

        public String getGameVersion() {
            return gameVersion;
        }

        public void setGameVersion(String gameVersion) {
            this.gameVersion = gameVersion;
        }

        public int getMapId() {
            return mapId;
        }

        public void setMapId(int mapId) {
            this.mapId = mapId;
        }

        public List<Participant> getParticipants() {
            return participants;
        }

        public void setParticipants(List<Participant> participants) {
            this.participants = participants;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Participant {
        private int assists;
        private int baronKills;
        private int deaths;
        private String championName;
        private String puuid;
        private Challenges challenges; // Change from Map to Challenges class

        // Getters and Setters
        public int getAssists() {
            return assists;
        }

        public void setAssists(int assists) {
            this.assists = assists;
        }

        public int getBaronKills() {
            return baronKills;
        }

        public void setBaronKills(int baronKills) {
            this.baronKills = baronKills;
        }

        public int getDeaths() {
            return deaths;
        }

        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }

        public String getChampionName() {
            return championName;
        }

        public void setChampionName(String championName) {
            this.championName = championName;
        }

        public String getPuuid() {
            return puuid;
        }

        public void setPuuid(String puuid) {
            this.puuid = puuid;
        }

        public Challenges getChallenges() {
            return challenges;
        }

        public void setChallenges(Challenges challenges) {
            this.challenges = challenges;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Challenges {
        private double kda;
        private int assists;
        private int kills;
        private int deaths;
        // Add all the other fields present in challenges

        // Getters and Setters
        public double getKda() {
            return kda;
        }

        public void setKda(double kda) {
            this.kda = kda;
        }

        public int getAssists() {
            return assists;
        }

        public void setAssists(int assists) {
            this.assists = assists;
        }

        public int getKills() {
            return kills;
        }

        public void setKills(int kills) {
            this.kills = kills;
        }

        public int getDeaths() {
            return deaths;
        }

        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }
    }
}
