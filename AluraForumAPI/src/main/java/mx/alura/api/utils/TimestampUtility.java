package mx.alura.api.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Utility class for working with timestamps.
 */
public class TimestampUtility {

    private TimestampUtility() {
        // Private constructor to prevent instantiation.
    }

    /**
     * Gets the current timestamp with rounded nanoseconds to 10 milliseconds' precision.
     *
     * @return A Timestamp object representing the current time with rounded nanoseconds.
     */
    public static Timestamp getTimeNowRounded() {
        LocalDateTime now = LocalDateTime.now();

        long nanos = now.getNano();
        long roundedNanos = Math.round((double) nanos / 10000000) * 10000000;

        now = now.withNano((int) roundedNanos);

        return Timestamp.valueOf(now);
    }
}
