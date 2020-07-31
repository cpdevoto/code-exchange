package neuralnets;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

class Row {
  private final List<Double> values;

  Row(double... values) {
    this.values = Arrays.stream(values)
        .boxed()
        .collect(collectingAndThen(toList(), ImmutableList::copyOf));
  }

  public List<Double> values() {
    return values;
  }

}
