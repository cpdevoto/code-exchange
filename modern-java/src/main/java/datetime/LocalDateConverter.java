package datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class LocalDateConverter {

  public static void main(String[] args) {
    ZoneId zoneId = ZoneId.of("America/New_York");

    LocalDateTime startLocal = LocalDate.of(2020, 2, 3).atStartOfDay();
    LocalDateTime endLocal = LocalDate.of(2020, 2, 6).atStartOfDay().minus(1, ChronoUnit.MILLIS);

    long start = startLocal.atZone(zoneId).toEpochSecond();
    long end = endLocal.atZone(zoneId).toEpochSecond();

    System.out.println(start);
    System.out.println(end);



  }

}
