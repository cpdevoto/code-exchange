package adfunction;

import java.util.Map;

public interface AdFunction {

  Boolean process(Map<String, InputArg> inputArgs, Map<String, ConstArg> constArgs,
      Map<String, String> state);

}
