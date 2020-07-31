package datetime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;

public class LocalDateTest {

  public static void main(String[] args) {
    // ALL CLASSES IMMUTABLE

    // LocalDate
    LocalDate date = LocalDate.of(2017, 9, 21);

    printDate(date);

    date = LocalDate.now();
    Arrays.asList(
        "Year: " + date.get(ChronoField.YEAR),
        "Month: " + date.get(ChronoField.MONTH_OF_YEAR),
        "Day of Month: " + date.get(ChronoField.DAY_OF_MONTH))
        .forEach(System.out::println);
    System.out.println();

    date = LocalDate.parse("2017-09-21");
    printDate(date);
    LocalDate date1 = date;
    LocalDate date2 = LocalDate.now();
    printDate(date2);


    // Modifying Attributes produces a new LocalDate
    LocalDate date3 = date1.withYear(2011);
    printDate(date3);
    LocalDate date4 = date3.withDayOfMonth(25);
    printDate(date4);
    LocalDate date5 = date4.with(ChronoField.DAY_OF_MONTH, 2);
    printDate(date5);
    LocalDate date6 = date5.plusWeeks(1);
    printDate(date6);
    LocalDate date7 = date6.minusYears(6);
    printDate(date7);
    LocalDate date8 = date7.plus(6, ChronoUnit.MONTHS);
    printDate(date8);


    // LocalTime
    LocalTime time = LocalTime.of(13, 45, 20);
    printTime(time);
    LocalTime time1 = time;
    LocalTime time2 = LocalTime.now();


    time = LocalTime.parse("13:45:20");
    printTime(time);

    // LocalDateTime
    LocalDateTime dt1 = LocalDateTime.of(2017, Month.SEPTEMBER, 21, 13, 45, 20);
    printDateTime(dt1);
    LocalDateTime dt2 = LocalDateTime.of(date, time);
    printDateTime(dt2);
    LocalDateTime dt3 = date.atTime(13, 45, 20);
    printDateTime(dt3);
    LocalDateTime dt4 = date.atTime(time);
    printDateTime(dt4);
    LocalDateTime dt5 = time.atDate(date);
    printDateTime(dt5);

    date1 = dt1.toLocalDate();
    printDate(date1);
    time1 = dt1.toLocalTime();
    printTime(time1);

    // Instant represents the number of seconds since Unix epoch time; supports nanosecond precision
    // (between 0 and 999,999,999)
    // The following invocations all return the same Instant
    Instant instant1 = Instant.ofEpochSecond(3);
    printInstant(instant1);
    instant1 = Instant.ofEpochSecond(3, 0);
    printInstant(instant1);
    instant1 = Instant.ofEpochSecond(2, 1_000_000_000);
    printInstant(instant1);
    instant1 = Instant.ofEpochSecond(4, -1_000_000_000);
    printInstant(instant1);
    Instant instant2 = Instant.now();
    printInstant(instant2);
    System.out.println();

    // Duration represents an amount of time in seconds and nanoseconds (can't use LocalDates)
    Duration d1 = Duration.between(time1, time2);
    printDuration(d1);
    Duration d2 = Duration.between(dt1, dt2);
    printDuration(d2);
    Duration d3 = Duration.between(instant1, instant2);
    printDuration(d3);
    Duration threeMinutes = Duration.ofMinutes(3);
    printDuration(threeMinutes);
    threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
    printDuration(threeMinutes);
    System.out.println();

    // Period represents an amount of time in years, months, and days.
    Period tenDays = Period.between(LocalDate.of(2017, 9, 11), LocalDate.of(2017, 9, 21));
    printPeriod(tenDays);
    tenDays = Period.ofDays(10);
    printPeriod(tenDays);
    Period threeWeeks = Period.ofWeeks(3);
    printPeriod(threeWeeks);
    Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
    printPeriod(twoYearsSixMonthsOneDay);
    System.out.println();

    // TemporalAdjusters provide a more customizable way to define the manipulation
    // needed to operation on a specific date (e.g. adjust a date to the next Sunday, the next
    // working day, the last day of the month, etc.).
    LocalDate date21 = LocalDate.of(2014, 3, 18);
    printDate(date21);
    LocalDate date22 = date21.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    printDate(date22);
    LocalDate date23 = date22.with(TemporalAdjusters.lastDayOfMonth());
    printDate(date23);
    LocalDate date24 = date23.with(new NextWorkingDay()); // see implementation below
    printDate(date24);

    LocalDate bday = LocalDate.of(1967, 12, 19);
    printDate(bday);

    ZonedDateTime zdt1 = LocalDateTime.now().atZone(ZoneId.of("America/New_York"));
    String s = zdt1.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    System.out.println(s);
    System.out.println();

    Instant now = Instant.ofEpochMilli(1551672723887L);
    LocalDateTime ldt1 = LocalDateTime.ofInstant(now, ZoneId.of("America/New_York"));
    printDateTime(ldt1);
  }

  private static void printTime(LocalTime time) {
    Arrays.asList(
        "Hour: " + time.getHour(),
        "Minute: " + time.getMinute(),
        "Second: " + time.getSecond())
        .forEach(System.out::println);
    System.out.println();
  }

  private static void printDate(LocalDate date) {
    Arrays.asList(
        "Year: " + date.getYear(),
        "Month: " + date.getMonth(),
        "Day of Month: " + date.getDayOfMonth(),
        "Day of Week: " + date.getDayOfWeek(),
        "Length of Month: " + date.lengthOfMonth(),
        "Leap Year?: " + date.isLeapYear())
        .forEach(System.out::println);
    System.out.println();
  }

  private static void printDateTime(LocalDateTime dateTime) {
    Arrays.asList(
        "Year: " + dateTime.getYear(),
        "Month: " + dateTime.getMonth(),
        "Day of Month: " + dateTime.getDayOfMonth(),
        "Day of Week: " + dateTime.getDayOfWeek(),
        "Hour: " + dateTime.getHour(),
        "Minute: " + dateTime.getMinute(),
        "Second: " + dateTime.getSecond())
        .forEach(System.out::println);
    System.out.println();
  }

  private static void printInstant(Instant instant) {
    System.out.println("Instant: " + instant);
  }

  private static void printDuration(Duration duration) {
    System.out.println("Duration: " + duration);
  }

  private static void printPeriod(Period period) {
    System.out.println("Period: " + period);
  }

  private static class NextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
      DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
      int dayToAdd = 1;
      if (dow == DayOfWeek.FRIDAY)
        dayToAdd = 3;
      else if (dow == DayOfWeek.SATURDAY)
        dayToAdd = 2;
      return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    }

  }
}
