package com.resolute.utils.simple;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ExceptionUtilsTest {
  
  @SuppressWarnings("null")
  @Test
  public void extractReason() {

    // STEP 1: ARRANGE
    String reason = "";
    String expectedReason = "null  STACKTRACE:  com.resolute.utils.simple.ExceptionUtilsTest.extractReason(ExceptionUtilsTest.java:21)";
    
    
    // STEP 2: ACT
    try {
      String s = null;
      s = s.replaceAll("X", "Y");
    } catch (Throwable t) {
      reason = ExceptionUtils.extractReason(t);
    }
    
    
    // STEP 3: ASSERT
    assertThat(reason, equalTo(expectedReason));
  }
  
  @SuppressWarnings("null")
  @Test
  public void extractReason_withErrorMessagePrefix() {

    // STEP 1: ARRANGE
    String errorMessagePrefix = "Error Message Prefix. ";
    String reason = "";
    String expectedReason = errorMessagePrefix + " null  STACKTRACE:  com.resolute.utils.simple.ExceptionUtilsTest.extractReason_withErrorMessagePrefix(ExceptionUtilsTest.java:44)";
    
    
    // STEP 2: ACT
    try {
      String s = null;
      s = s.replaceAll("X", "Y");
    } catch (Throwable t) {
      reason = ExceptionUtils.extractReason(errorMessagePrefix, t);
    }
    
    
    // STEP 3: ASSERT
    assertThat(reason, equalTo(expectedReason));
  }

  @Test(expected=IllegalArgumentException.class)
  public void extractReason_nullArgument() {

    ExceptionUtils.extractReason(null);
  }
}
