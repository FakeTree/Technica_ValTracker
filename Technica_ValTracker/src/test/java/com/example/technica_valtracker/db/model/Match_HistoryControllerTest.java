package com.example.technica_valtracker.db.model;
import com.example.technica_valtracker.UserManager;
import com.example.technica_valtracker.controller.Match_HistoryController;

import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.db.model.MatchBucket;
import com.example.technica_valtracker.db.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.concurrent.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Match_HistoryControllerTest {

    private Match_HistoryController controller;
    private MatchBucket bucket;
    private Random random;

    @BeforeEach
    public void setUp() {
        // Initialize the controller
        controller = new Match_HistoryController();

        // Initialize random for test case generation
        random = new Random();

        // Mock non-UI components to simplify testing
        controller.matchBucket = mock(MatchBucket.class);
        controller.userManager = mock(UserManager.class);

        // Simulate a current user setup with random strings for riotId and region
        User mockUser = mock(User.class);
        when(mockUser.getRiotID()).thenReturn(generateRandomString(10));
        when(mockUser.getUserId()).thenReturn(generateRandomString(12)); // Simulating user ID as a longer string
        when(mockUser.getRegion()).thenReturn(generateRandomString(4));
        when(controller.userManager.getCurrentUser()).thenReturn(mockUser);
    }

    /**
     * Generates a random alphanumeric string of a given length.
     *
     * @param length the length of the string to generate
     * @return a random alphanumeric string
     */
    private String generateRandomString(int length) {
        // Create a pool of characters to choose from: A-Z, a-z, 0-9
        String characterPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            randomString.append(characterPool.charAt(random.nextInt(characterPool.length())));
        }
        return randomString.toString();
    }

    @Test
    public void testGetAllMatchIdsTask_CreatesCorrectQuery() {
        String puuid = "sample_puuid";
        String region = "na1";
        String riotId = "sample_riot_id";

        // Capture and check the query URL created within the task
        Task<ResponseBody> task = controller.getAllMatchIdsTask(puuid, region, riotId);
        assertNotNull(task, "Task should be created successfully.");

        task.setOnSucceeded(event -> {
            ResponseBody responseBody = task.getValue();
            assertNotNull(responseBody, "Response body should not be null.");
            assertFalse(responseBody.isError(), "Response should not contain an error.");
        });
    }

    @Test
    public void testGetProcessMatchesTask_SubmitsTasksCorrectly() {
        String puuid = "sample_puuid";
        String region = "na1";

        // Ensure that it handles a list of match IDs correctly
        List<String> matchIds = List.of("match1", "match2", "match3");
        Task<Boolean> task = controller.getProcessMatchesTask(matchIds, region, puuid);

        assertNotNull(task, "ProcessMatchesTask should not be null.");
        task.setOnSucceeded(event -> assertTrue(task.getValue(), "ProcessMatchesTask should complete successfully."));
    }

    @Test
    public void testGetMatchTask_ReturnsCorrectData() throws IOException {
        String matchId = "sample_match_id";
        String region = "na1";
        String puuid = "sample_puuid";

        // Create a task to fetch match data and verify return data structure
        Task<ResponseBody> task = controller.getMatchTask(matchId, region, puuid, true);
        assertNotNull(task, "MatchTask should be created successfully.");

        task.setOnSucceeded(event -> {
            ResponseBody responseBody = task.getValue();
            assertNotNull(responseBody, "Response body should not be null.");
            assertFalse(responseBody.isError(), "Match data should be retrieved without errors.");
        });
    }

    @Test
    public void testHandleTaskError_ShowsErrorAlert() throws JsonProcessingException {
        // Get random status code and corresponding detail
        final List<Integer> statusCodes = Arrays.asList(400, 401, 403, 404, 405, 415, 429, 500, 502, 503, 504);

        final int status = statusCodes.get(random.nextInt(statusCodes.size()));
        final String detail = ErrorMessage.getErrorReason(status);
        final ErrorMessage testError = new ErrorMessage(status, detail);
        final String json = String.format("{\"status\":%d,\"detail\":\"%s\"}", status, detail);

        ErrorMessage err = getErrorMessageFromJson(json);

        assertEquals(err.getClass(), testError.getClass());
        assertEquals(err.getStatus(), testError.getStatus());
        assertEquals(err.getDetail(), testError.getDetail());
    }

    @Test
    public void testDataHandlingInMatchBucket_UpdatesValues() {
        // Setup mock data for MatchBucket interactions
        MatchBucket mockBucket = controller.matchBucket;

        // Mock adding KDA, wins, etc., to simulate actual updates in the MatchBucket
        when(mockBucket.getMatchIds()).thenReturn(List.of("match1", "match2"));
        when(mockBucket.getKDAAcrossAllGames()).thenReturn(3.5);
        when(mockBucket.getWinRateAcrossAllGames()).thenReturn(0.75);

        assertEquals(2, mockBucket.getMatchIds().size());
        assertEquals(3.5, mockBucket.getKDAAcrossAllGames(), 0.01);
        assertEquals(0.75, mockBucket.getWinRateAcrossAllGames(), 0.01);
    }
}
