package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventLoggerTest {
    private String log1;
    private String log2;

    @BeforeEach
    public void setup() {
        log1 = "log1";
        log2 = "log2";
    }

    @Test
    public void testAddLogSingle() {
        EventLogger.addToLogs(log1);
        assertTrue(EventLogger.getLogger().contains(log1));
        assertEquals(1, EventLogger.getLogger().size());
    }

    @Test
    public void testAddLogMultiple() {
        EventLogger.addToLogs(log1);
        EventLogger.addToLogs(log2);
        assertTrue(EventLogger.getLogger().contains(log1));
        assertTrue(EventLogger.getLogger().contains(log2));
        assertEquals(2, EventLogger.getLogger().size());
    }

    @Test
    public void testGetLogsString() {
        EventLogger.getLogsString();
        assertEquals("", EventLogger.getLogsString());
        assertEquals(0, EventLogger.getLogger().size());
    }

    @Test
    public void testGetLogsStringClears() {
        EventLogger.addToLogs(log1);
        EventLogger.addToLogs(log2);
        assertEquals(2, EventLogger.getLogger().size());
        String logs = EventLogger.getLogsString();
        assertTrue(logs.contains(log1));
        assertTrue(logs.contains(log2));
        assertEquals(0, EventLogger.getLogger().size());
    }
}
