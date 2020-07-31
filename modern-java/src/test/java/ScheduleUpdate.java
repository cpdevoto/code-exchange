import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.base.Preconditions;

public class ScheduleUpdate {

  public static void main(String[] args) throws IOException {

    try (BufferedReader in = new BufferedReader(new FileReader("schedule.txt"))) {
      boolean firstLine = true;
      Row lastRow = null;
      for (String line = in.readLine(); line != null; line = in.readLine()) {
        if (firstLine) {
          firstLine = false;
          System.out.println(line);
          continue;
        }
        Row currentRow = Row.parse(line);
        if (lastRow != null) {
          Row outputRow = new Row(lastRow.week, currentRow.firstResponder, currentRow.backup);
          System.out.println(outputRow);
        }
        lastRow = currentRow;
      }
    }
  }


  private static class Row {
    private final String week;
    private final String firstResponder;
    private final String backup;

    static Row parse(String line) {
      String[] fields = line.split("\\|");
      Preconditions.checkArgument(fields.length == 4);
      String week = fields[1].trim();
      String firstResponder = update(fields[2].trim());
      String backup = update(fields[3].trim());
      return new Row(week, firstResponder, backup);
    }

    private static String update(String name) {
      if ("Matt (313) 585-3680".equals(name)) {
        return "Michael (586) 484-4889";
      }
      return name;
    }

    private Row(String week, String firstResponder, String backup) {
      this.week = week;
      this.firstResponder = firstResponder;
      this.backup = backup;
    }

    public String toString() {
      return "| " + week + " | " + firstResponder + " | " + backup + " |";
    }
  }
}
