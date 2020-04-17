package model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a log of events that have occurred with each move.
 * Static because only one is needed.
 */

public class EventLogger {

    private static Queue<String> logger = new LinkedList<>();

    public static String SPLIT_STRING = "\n";

    // EFFECTS: not accessible because all methods should be called through the class
    private EventLogger() {
    }

    // getters
    public static Queue<String> getLogger() {
        return logger;
    }

    // MODIFIES: this
    // EFFECTS: adds the given log to the logger
    public static void addToLogs(String log) {
        logger.add(log);
    }

    // MODIFIES: this
    // EFFECTS: returns a string that has all the logs in order and then clears the logger
    public static String getLogsString() {
        StringBuilder result = new StringBuilder();
        int logNumber = 1;
        for (String string : logger) {
            result.append(logNumber).append(": ").append(string).append(SPLIT_STRING);
            logNumber++;
        }
        logger.clear();
        return result.toString();
    }

}
