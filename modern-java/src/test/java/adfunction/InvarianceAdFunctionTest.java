package adfunction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class InvarianceAdFunctionTest {


  @Test
  public void test() {
    Map<String, ConstArg> constArgs = ImmutableMap.of(
        "HighBand", new ConstArg("HighBand", "73.3"),
        "LowBand", new ConstArg("LowBand", "68.3"));
    Map<String, String> state = Maps.newHashMap();

    InvarianceAdFunction func = new InvarianceAdFunction();

    Map<String, InputArg> inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 0L, "0"),
        "ZoneTemp", new InputArg("ZoneTemp", 0L, "75.33"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 5L, "0"),
        "ZoneTemp", new InputArg("ZoneTemp", 5L, "75.35"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 10L, "1"),
        "ZoneTemp", new InputArg("ZoneTemp", 10L, "75.30"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(true));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 15L, "1"),
        "ZoneTemp", new InputArg("ZoneTemp", 15L, "73.00"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 20L, "1"),
        "ZoneTemp", new InputArg("ZoneTemp", 20L, "73.10"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 25L, "1"),
        "ZoneTemp", new InputArg("ZoneTemp", 25L, "72.00"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 30L, "1"),
        "ZoneTemp", new InputArg("ZoneTemp", 30L, "70.00"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 35L, "1"),
        "ZoneTemp", new InputArg("ZoneTemp", 35L, "70.00"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 40L, "1"),
        "ZoneTemp", new InputArg("ZoneTemp", 40L, "70.00"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 45L, "1"),
        "ZoneTemp", new InputArg("ZoneTemp", 45L, "70.00"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 50L, "1"),
        "ZoneTemp", new InputArg("ZoneTemp", 50L, "70.00"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(true));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 55L, "0"),
        "ZoneTemp", new InputArg("ZoneTemp", 55L, "70.00"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

    inputArgs = ImmutableMap.of(
        "FanSts", new InputArg("FanSts", 60L, "0"),
        "ZoneTemp", new InputArg("ZoneTemp", 60L, "70.00"));

    assertThat(func.process(inputArgs, constArgs, state), equalTo(false));

  }

}
