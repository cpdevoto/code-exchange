package utils;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import com.google.common.primitives.Doubles;


public class GpsCoords {
  private static final double EARTH_RADIUS_MILES = 3959;
  private final double latitude;
  private final double longitude;

  public static String formatDistance(double distance) {
    return String.format("%.1f", distance);
  }

  public GpsCoords(String latitude, String longitude) {
    requireNonNull(latitude, "latitude cannot be null");
    requireNonNull(longitude, "longitude cannot be null");
    Double lat = Doubles.tryParse(latitude);
    checkArgument(lat != null, "Expected a double precision number for the latitude");
    Double lon = Doubles.tryParse(longitude);
    checkArgument(lon != null, "Expected a double precision number for the longitude");

    validate(lat, lon);
    this.latitude = lat;
    this.longitude = lon;
  }

  public GpsCoords(double latitude, double longitude) {
    validate(latitude, longitude);
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public double distanceTo(GpsCoords g) {
    double dLat = degreesToRadians(g.latitude - latitude);
    double dLon = degreesToRadians(g.longitude - longitude);

    double lat1 = degreesToRadians(latitude);
    double lat2 = degreesToRadians(g.latitude);

    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS_MILES * c;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(latitude);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(longitude);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    GpsCoords other = (GpsCoords) obj;
    if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
      return false;
    if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "GpsCoords [latitude=" + latitude + ", longitude=" + longitude + "]";
  }

  private void validate(double latitude, double longitude) {
    checkArgument(latitude >= -90 && latitude <= 90,
        "The latitude must be a double precision number between -90 and 90");
    checkArgument(longitude >= -180 && longitude <= 180,
        "The longitude must be a double precision number between -180 and 180");
  }

  private double degreesToRadians(double degrees) {
    return degrees * Math.PI / 180;
  }
}
