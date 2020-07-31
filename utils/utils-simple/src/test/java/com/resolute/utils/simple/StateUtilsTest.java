package com.resolute.utils.simple;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class StateUtilsTest {
  
  @Test
  public void getStateAbbreviation() {

    // STEP 1: ARRANGE
    String stateName = " Michigan ";
    String expectedStateAbbreviation = "MI";
    
    
    // STEP 2: ACT
    String stateAbbreviation = StateUtils.getStateAbbreviation(stateName);
    
    
    // STEP 3: ASSERT
    assertThat(stateAbbreviation, equalTo(expectedStateAbbreviation));
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void getStateAbbreviation_nullArgument() {

    // STEP 1: ARRANGE
    String stateName = null;
    
    
    // STEP 2: ACT
    StateUtils.getStateAbbreviation(stateName);
  }
}
