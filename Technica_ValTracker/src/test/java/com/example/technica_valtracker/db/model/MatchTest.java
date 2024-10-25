package com.example.technica_valtracker.db.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    private Match match;
    private Match.Info info;
    private Match.Participant participant;
    private Match.Challenges challenges;

    @BeforeEach
    void setUp() {
        // Initialize a Match instance with mock data
        match = new Match();

        // Create and set metadata
        Match.Metadata metadata = new Match.Metadata();
        metadata.setMatchId("sample_match_id");
        metadata.setParticipants(List.of("puuid1", "puuid2", "puuid3"));
        match.setMetadata(metadata);

        // Create and set match info
        info = new Match.Info();
        info.setGameMode("Ranked");
        info.setQueueId(420);  // Ranked Solo
        match.setInfo(info);

        // Create and set participant data
        participant = new Match.Participant();
        participant.setPuuid("puuid1");
        participant.setKills(10);
        participant.setAssists(5);
        participant.setDeaths(2);
        participant.settotalMinionsKilled(100);
        participant.setNeutralMinionsKilled(20);
        participant.setWin(true);

        // Create and set challenges
        challenges = new Match.Challenges();
        challenges.setKda(7.5);
        participant.setChallenges(challenges);

        // Add participants to match info
        info.setParticipants(List.of(participant));
    }

    @Test
    void testGetParticipantIndexByPuuid() {
        // Test finding participant by PUUID
        int index = Match.getParticipantIndexByPuuid(info.getParticipants(), "puuid1");
        assertEquals(0, index);

        index = Match.getParticipantIndexByPuuid(info.getParticipants(), "puuid2");
        assertEquals(-1, index, "Participant with PUUID 'puuid2' should not be found");
    }

    @Test
    void testGetSetGameMode() {
        // Test getting and setting the game mode
        assertEquals("Ranked", info.getGameMode());

        info.setGameMode("Normal");
        assertEquals("Normal", info.getGameMode());
    }

    @Test
    void testSetQueueMode() {
        // Test queue mode is set based on queue ID
        info.setQueueMode();
        assertEquals("Ranked Solo", info.getQueueMode());

        info.setQueueId(440);  // Ranked Flex
        info.setQueueMode();
        assertEquals("Ranked Flex", info.getQueueMode());

        info.setQueueId(999);  // Invalid queue ID
        info.setQueueMode();
        assertEquals("N/A", info.getQueueMode());
    }

    @Test
    void testParticipantKillStats() {
        // Test participant kills, assists, and deaths
        assertEquals(10, participant.getKills());
        assertEquals(5, participant.getAssists());
        assertEquals(2, participant.getDeaths());

        participant.setKills(15);
        assertEquals(15, participant.getKills());
    }

    @Test
    void testParticipantMinionKillTotal() {
        // Test calculation of total minion kills
        participant.setMinionKillTotal();
        assertEquals(120, participant.getMinionKillTotal(), "Minion kill total should be the sum of total and neutral minions");
    }

    @Test
    void testParticipantWinStatus() {
        // Test participant win status
        assertTrue(participant.getWin());

        participant.setWin(false);
        assertFalse(participant.getWin());
    }

    @Test
    void testChallengesKDA() {
        // Test participant KDA calculation
        assertEquals(7.5, challenges.getKda(), 0.01);

        challenges.setKda(5.0);
        assertEquals(5.0, challenges.getKda(), 0.01);
    }

    @Test
    void testMetadata() {
        // Test metadata fields
        assertEquals("sample_match_id", match.getMetadata().getMatchId());
        assertEquals(3, match.getMetadata().getParticipants().size());
    }
}
