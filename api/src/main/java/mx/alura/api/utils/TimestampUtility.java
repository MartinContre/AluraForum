package mx.alura.api.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimestampUtility {

    private TimestampUtility() {

    }

    public static Timestamp getTimeNowRounded() {
        LocalDateTime now = LocalDateTime.now();

        long nanos = now.getNano();
        long roundedNanos = Math.round((double) nanos / 10000000) * 10000000;

        now = now.withNano((int) roundedNanos);

        return Timestamp.valueOf(now);
    }
}
