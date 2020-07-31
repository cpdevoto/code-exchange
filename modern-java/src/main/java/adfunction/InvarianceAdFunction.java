package adfunction;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Optional;

import com.google.common.primitives.Doubles;

public class InvarianceAdFunction implements AdFunction {

  @Override
  public Boolean process(Map<String, InputArg> inputArgs, Map<String, ConstArg> constArgs,
      Map<String, String> state) {
    InputArg fanStsArg =
        requireNonNull(inputArgs.get("FanSts"), "expected a FanSts input argument");
    InputArg zoneTempArg =
        requireNonNull(inputArgs.get("ZoneTemp"), "expected a ZoneTemp input argument");

    double fanSts = requireNonNull(Doubles.tryParse(fanStsArg.getValue()),
        "Expected a double value for the FanSts input argument");

    long zoneTempTs = zoneTempArg.getTs();
    double zoneTemp = requireNonNull(Doubles.tryParse(zoneTempArg.getValue()),
        "Expected a long value for the ZoneTemp input argument");

    ConstArg highBandConst =
        requireNonNull(constArgs.get("HighBand"), "expected a HighBand constant argument");
    ConstArg lowBandConst =
        requireNonNull(constArgs.get("LowBand"), "expected a LowBand constant argument");

    double highBand = requireNonNull(Doubles.tryParse(highBandConst.getValue()),
        "Expected a double value for the HighBand constant argument");
    double lowBand = requireNonNull(Doubles.tryParse(lowBandConst.getValue()),
        "Expected a double value for the HighBand constant argument");

    long lastZoneTempTs =
        Optional.ofNullable(state.get("lastZoneTempTs"))
            .map(x -> Long.parseLong(x)).orElse(-1L);
    double lastZoneTemp =
        Optional.ofNullable(state.get("lastZoneTemp"))
            .map(x -> Double.parseDouble(x)).orElse(0.0);

    if (lastZoneTempTs == -1
        || zoneTempTs != lastZoneTempTs && Math.abs(zoneTemp - lastZoneTemp) >= 0.01) {
      state.put("lastZoneTempTs", String.valueOf(zoneTempTs));
      state.put("lastZoneTemp", String.valueOf(zoneTemp));
    }

    boolean isFault = false;
    if (fanSts == 1.0) {
      if (zoneTemp > highBand || zoneTemp < lowBand) {
        isFault = true;
      } else if (Math.abs(zoneTemp - lastZoneTemp) <= 0.01 && zoneTempTs - lastZoneTempTs > 15) {
        isFault = true;
      }
    }

    return isFault;
  }

}
