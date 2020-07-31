package com.resolute.cloudfill.utils;

import org.junit.Assert;
import org.junit.Test;

import com.resolute.utils.simple.OpenTsdbStringUtils;

public class OpenTsdbStringUtilsTest {

  @Test
  public void test_toValidMetricId() {
    {
      String input = "abc";
      String expected = input;
      Assert.assertEquals(expected, OpenTsdbStringUtils.toValidMetricId(input));
    }
    {
      String ___input = "abc\\";
      String expected = "abc_";
      Assert.assertEquals(expected, OpenTsdbStringUtils.toValidMetricId(___input));
    }
    {
      String input = "a-b-c";
      String expected = input;
      Assert.assertEquals(expected, OpenTsdbStringUtils.toValidMetricId(input));
    }
    {
      String ___input = "a$b(c)A~B@C#1%1^1&-*_(.)/+='?><,";
      String expected = "a_b_c_A_B_C_1_1_1_-___._/_______";
      Assert.assertEquals(expected, OpenTsdbStringUtils.toValidMetricId(___input));
    }
  }

  @Test
  public void test_toCompactMetricId() {
    {
      String input = "abc";
      String expected = input;
      Assert.assertEquals(expected, OpenTsdbStringUtils.toCompactMetricId(input));
    }
    {
      String ___input = "/abc\\";
      String expected = "_abc_";
      Assert.assertEquals(expected, OpenTsdbStringUtils.toCompactMetricId(___input));
    }
    {
      String input = "a-b-c";
      String expected = input;
      Assert.assertEquals(expected, OpenTsdbStringUtils.toCompactMetricId(input));
    }
    {
      String ___input = "a$b(c)A~B@C#1%1^1&-*_(.)/+='?><,";
      String expected = "a_b_c_A_B_C_1_1_1_-___._________";
      Assert.assertEquals(expected, OpenTsdbStringUtils.toCompactMetricId(___input));
    }
  }

}
