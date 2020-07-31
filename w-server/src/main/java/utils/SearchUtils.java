package utils;

public class SearchUtils {

  public static String toTextSearchQuery(String rawQuery) {
    StringBuilder buf = new StringBuilder();
    boolean firstLoop = true;
    for (String searchTerm : rawQuery.split("\\s")) {
      if (firstLoop) {
        firstLoop = false;
      } else {
        buf.append(" & ");
      }
      buf.append("'" + searchTerm.replaceAll("'", "''") + "':*");
    }
    String search = buf.toString();
    return search;
  }

  private SearchUtils() {}

}
