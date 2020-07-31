package com.resolute.utils.simple;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import org.junit.Test;

public class TimezoneUtilsTest {
  
  private static final String AMERICA_NEW_YORK_ID = "America/New_York";
  private static final String AMERICA_NEW_YORK_LABEL = "Eastern Time (US & Canada)";

  @Test
  public void test_get_timezone () {
    TimeZone americaNewYork = TimeZone.getTimeZone(AMERICA_NEW_YORK_ID);
    assertThat(americaNewYork.getID(), equalTo(AMERICA_NEW_YORK_ID));
    
    TimeZone result = TimezoneUtils.getTimezone(AMERICA_NEW_YORK_ID);
    assertThat(result, equalTo(americaNewYork));
    
    result = TimezoneUtils.getTimezone(AMERICA_NEW_YORK_ID.toUpperCase());
    assertThat(result, equalTo(americaNewYork));

    result = TimezoneUtils.getTimezone(AMERICA_NEW_YORK_ID.toLowerCase());
    assertThat(result, equalTo(americaNewYork));

    result = TimezoneUtils.getTimezone(AMERICA_NEW_YORK_LABEL);
    assertThat(result, equalTo(americaNewYork));

    result = TimezoneUtils.getTimezone(AMERICA_NEW_YORK_LABEL.toUpperCase());
    assertThat(result, equalTo(americaNewYork));
  
    result = TimezoneUtils.getTimezone(AMERICA_NEW_YORK_LABEL.toLowerCase());
    assertThat(result, equalTo(americaNewYork));
  }

  @Test
  public void test_get_timezone_by_alias () {

    // STEP 1: ARRANGE
    TimeZone americaDetroitTimeZone = TimeZone.getTimeZone("America/Detroit");
    
    
    // STEP 2: ACT
    String rubyTimezoneLabel = TimezoneUtils.getLabel(americaDetroitTimeZone);
    
    
    // STEP 3: ASSERT
    assertThat(rubyTimezoneLabel, equalTo("Eastern Time (US & Canada)"));
  }
}
