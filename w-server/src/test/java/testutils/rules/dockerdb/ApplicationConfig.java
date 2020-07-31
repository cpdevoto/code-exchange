package testutils.rules.dockerdb;

import static java.util.Objects.requireNonNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jersey.repackaged.com.google.common.collect.Sets;
import ninja.utils.NinjaConstant;

public class ApplicationConfig {
  private static final Pattern SUBST_PATTERN = Pattern.compile("\\$\\{([^\\}]+)\\}");

  private final String mode;
  private final Properties conf;

  public static ApplicationConfig load() throws IOException {
    Properties conf = new Properties();
    String mode = System.getProperty(NinjaConstant.MODE_KEY_NAME, "prod");
    String externalConfig = System.getProperty("ninja.external.configuration");
    if (externalConfig != null) {
      try (InputStream in = new FileInputStream(externalConfig)) {
        conf.load(in);
      }
    } else {
      try (InputStream in =
          ApplicationConfig.class.getClassLoader().getResourceAsStream("conf/application.conf")) {
        conf.load(in);
      }
    }
    return new ApplicationConfig(conf, mode);
  }

  private ApplicationConfig(Properties conf, String mode) {
    this.conf = conf;
    this.mode = mode;
  }

  public String expectProperty(String name) {
    return requireNonNull(getProperty(name), "Expected a property named " + name);
  }

  public String getProperty(String name) {
    return getProperty(name, null);
  }

  public String getProperty(String name, String defaultValue) {
    return getProperty(name, defaultValue, Sets.newHashSet());
  }

  private String getProperty(String name, String defaultValue, Set<String> visited) {
    String value = conf.getProperty("%" + mode + "." + name);
    if (value == null) {
      value = conf.getProperty(name);
    }
    if (value == null) {
      return defaultValue;
    }
    visited.add(name);
    value = substitutions(value, visited);
    return value;
  }

  private String substitutions(String value, Set<String> visited) {
    String result = value;
    while (true) {
      Matcher m = SUBST_PATTERN.matcher(result);
      if (!m.find()) {
        break;
      }
      if (visited.contains(m.group(1))) {
        throw new ApplicationConfigLoadException(
            "Encountered a cyclical reference to property " + m.group(1));
      }
      String replacement = getProperty(m.group(1));
      if (replacement == null) {
        replacement = System.getProperty(m.group(1));
      }
      if (replacement == null) {
        throw new ApplicationConfigLoadException("Invalid property value for " + m.group(1));
      }
      result = result.replace(m.group(0), replacement);
    }
    return result;
  }

}
