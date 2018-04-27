package utils;

import org.junit.Test;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;

public class GpsCoordsTest {

  @Test
  public void test_distance_to() {
    GpsCoords home = new GpsCoords("42.392430", "-83.455331");
    GpsCoords work = new GpsCoords("42.546242", "-83.213497");

    assertThat(GpsCoords.formatDistance(home.distanceTo(work)), equalTo("16.3"));
  }


}
