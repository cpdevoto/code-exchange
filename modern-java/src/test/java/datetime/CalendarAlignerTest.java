package datetime;

import static datetime.CalendarAligners.ceiling;
import static datetime.CalendarAligners.floor;
import static datetime.CalendarAligners.nearest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.Test;

public class CalendarAlignerTest {
  private static TemporalAdjuster floor =
      floor(15, ChronoField.MINUTE_OF_HOUR);
  private static TemporalAdjuster ceiling =
      ceiling(15, ChronoField.MINUTE_OF_HOUR);
  private static TemporalAdjuster nearest =
      nearest(15, ChronoField.MINUTE_OF_HOUR);

  private static LocalDateTime getLocalDateTime(int year, int month, int day, int hour, int minute,
      int second, int nanos) {
    LocalDate date = LocalDate.of(year, month, day);
    LocalTime time = LocalTime.of(hour, minute, second, nanos);
    return date.atTime(time);

  }

  @Test(expected = IllegalArgumentException.class)
  public void test_new_invalid_interval() {
    CalendarAligners.floor(1_000_000_000, ChronoField.NANO_OF_SECOND);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_new_invalid_interval_2() {
    CalendarAligners.floor(5, ChronoField.MONTH_OF_YEAR);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_new_invalid_field() {
    CalendarAligners.floor(2, ChronoField.DAY_OF_WEEK);
  }


  @Test(expected = UnsupportedTemporalTypeException.class)
  public void test_local_date() {
    LocalDate.of(2019, 4, 3)
        .with(floor(2, ChronoField.MONTH_OF_YEAR));
  }

  @Test
  public void test() {
    LocalDateTime adjusted = getLocalDateTime(2019, 3, 3, 19, 57, 43, 657_423_821).with(floor);
    System.out.println("Floor: " + adjusted);

    adjusted = getLocalDateTime(2019, 3, 3, 19, 47, 43, 657_423_821).with(ceiling);
    System.out.println("Ceiling: " + adjusted);

    adjusted = getLocalDateTime(2019, 3, 3, 19, 52, 30, 0).with(nearest);
    System.out.println("Nearest: " + adjusted);
    adjusted = getLocalDateTime(2019, 3, 3, 19, 52, 29, 999_999_999).with(nearest);
    System.out.println("Nearest: " + adjusted);

    adjusted = getLocalDateTime(2019, 2, 3, 19, 52, 29, 999_999_999)
        .with(floor(2, ChronoField.MONTH_OF_YEAR));
    System.out.println("Floor: " + adjusted);

    adjusted = getLocalDateTime(2019, 2, 3, 19, 52, 29, 999_999_999)
        .with(floor(2, ChronoField.YEAR));
    System.out.println("Floor: " + adjusted);

    ZonedDateTime adjustedZDT =
        getLocalDateTime(2019, 2, 3, 19, 52, 29, 999_999_999).atZone(ZoneId.of("America/New_York"))
            .with(floor(2, ChronoField.YEAR));
    System.out.println("Floor: " + adjustedZDT);

  }

}
