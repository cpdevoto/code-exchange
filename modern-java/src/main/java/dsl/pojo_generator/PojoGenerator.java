package dsl.pojo_generator;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class PojoGenerator {
  private static final List<String> primitives = ImmutableList.copyOf(Arrays.asList(
      "boolean", "byte", "char", "short", "int", "long", "float", "double"));

  // @formatter: off
  private static final Map<String, String> primitiveWrappers =
      ImmutableMap.<String, String>builder()
          .put("boolean", "Boolean")
          .put("byte", "Byte")
          .put("char", "Character")
          .put("short", "Short")
          .put("int", "Integer")
          .put("long", "Long")
          .put("float", "Float")
          .put("double", "Double")
          .build();
  // @formatter: on

  private final Pojo.Builder pojoBuilder = Pojo.builder();

  public static PojoGenerator forClass(String className) {
    return new PojoGenerator(className);
  }

  public static PojoDataMemberBuilder dataMember(Consumer<PojoDataMemberBuilder> consumer) {
    requireNonNull(consumer, "consumer cannot be null");
    PojoDataMemberBuilder dataMemberBuilder = new PojoDataMemberBuilder();
    consumer.accept(dataMemberBuilder);
    return dataMemberBuilder;
  }

  private PojoGenerator(String className) {
    pojoBuilder.withClassName(className);
    pojoBuilder.withJacksonAnnotations(false);
  }

  public PojoGenerator inPackage(String packageName) {
    pojoBuilder.withPackageName(packageName);
    return this;
  }

  public PojoGenerator jacksonAnnotations() {
    pojoBuilder.withJacksonAnnotations(true);
    return this;
  }

  public PojoGenerator dataMembers(PojoDataMemberBuilder... dataMemberBuilders) {
    List<PojoDataMember> dataMembers = Stream.of(dataMemberBuilders)
        .map(PojoDataMemberBuilder::build)
        .collect(toList());
    pojoBuilder.withDataMembers(dataMembers);
    return this;
  }

  public String generate() {
    return generate(pojoBuilder.build());
  }

  private String generate(Pojo pojo) {
    Buffer buf = new Buffer();
    PojoFlags flags = new PojoFlags(pojo);

    buf.print("package ").print(pojo.getPackageName()).println(";");
    buf.println();
    buf.println("import static java.util.Objects.requireNonNull;");
    buf.println();
    buf.println("import java.util.function.Consumer;");
    if (pojo.getJacksonAnnotations()) {
      buf.println("import com.fasterxml.jackson.annotation.JsonCreator;");
      if (flags.get(PojoFlag.HAS_DATE)) {
        buf.println("import java.util.function.Consumer;");
        buf.println("import com.fasterxml.jackson.annotation.JsonFormat;");
      }
      buf.println("import com.fasterxml.jackson.annotation.JsonInclude;");
      buf.println("import com.fasterxml.jackson.annotation.JsonInclude.Include;");
      if (flags.get(PojoFlag.HAS_LOCAL_DATE) || flags.get(PojoFlag.HAS_LOCAL_DATE_TIME)) {
        buf.println("import com.fasterxml.jackson.annotation.JsonSerialize;");
      }
      buf.println("import com.fasterxml.jackson.databind.annotation.JsonDeserialize;");
      buf.println("import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;");
    }
    if (flags.get(PojoFlag.HAS_LIST)) {
      buf.println("import java.util.List;");
      buf.println("import com.google.common.collect.ImmutableList;");
    }
    if (flags.get(PojoFlag.HAS_MAP)) {
      buf.println("import java.util.Map;");
      buf.println("import com.google.common.collect.ImmutableMap;");
    }
    if (flags.get(PojoFlag.HAS_SET)) {
      buf.println("import java.util.Set;");
      buf.println("import com.google.common.collect.ImmutableSet;");
    }
    if (flags.get(PojoFlag.HAS_LOCAL_DATE)) {
      buf.println("import java.time.LocalDate;");
      if (pojo.getJacksonAnnotations()) {
        buf.println("import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;");
        buf.println("import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateSerializer;");
      }
    }
    if (flags.get(PojoFlag.HAS_LOCAL_DATE_TIME)) {
      buf.println("import java.time.LocalDateTime;");
      if (pojo.getJacksonAnnotations()) {
        buf.println(
            "import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;");
        buf.println("import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeSerializer;");
      }
    }
    if (flags.get(PojoFlag.HAS_OPTIONAL)) {
      buf.println("import java.util.Optional;");
    }
    buf.println();
    if (pojo.getJacksonAnnotations()) {
      buf.println("@JsonInclude(Include.NON_NULL)");
      buf.print("@JsonDeserialize(builder = ").print(pojo.getClassName())
          .println(".Builder.class)");
    }
    buf.print("public class ").print(pojo.getClassName()).println(" {");
    pojo.getDataMembers().stream()
        .forEach(dm -> buf.print("  private final ").print(getDataType(dm)).print(" ")
            .print(dm.getName()).println(";"));
    buf.println();
    if (pojo.getJacksonAnnotations()) {
      buf.println("  @JsonCreator");
    }
    buf.println("  public static Builder builder () {");
    buf.println("    return new Builder();");
    buf.println("  }");
    buf.println();
    buf.print("  public static Builder builder (").print(pojo.getClassName()).print(" ")
        .print(toCamel(pojo.getClassName())).println(") {");
    buf.print("    return new Builder(").print(toCamel(pojo.getClassName())).println(");");
    buf.println("  }");
    buf.println();
    buf.print("  private ").print(pojo.getClassName()).println(" (Builder builder) {");
    pojo.getDataMembers().stream()
        .forEach(dm -> buf.print("    this.").print(dm.getName()).print(" = builder.")
            .print(dm.getName()).println(";"));
    buf.println("  }");
    buf.println();
    pojo.getDataMembers().stream()
        .forEach(dm -> {
          if (pojo.getJacksonAnnotations()) {
            if (dm.getDataType().equals("LocalDate")) {
              buf.println("  @JsonSerialize(using = LocalDateSerializer.class)");
            } else if (dm.getDataType().equals("LocalDateTime")) {
              buf.println("  @JsonSerialize(using = LocalDateTimeSerializer.class)");
            } else if (dm.getDataType().equals("Date")) {
              buf.println(
                  "  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = \"dd-MM-yyyy hh:mm:ss\")");
            }
          }
          buf.print("  public ").print(getDataType(dm)).print(" get")
              .print(toHungarian(dm.getName())).println("() {");
          buf.print("    return ").print(dm.getName()).println(";");
          buf.println("  }");
          buf.println();
        });
    if (pojo.getJacksonAnnotations()) {
      buf.println("  @JsonPOJOBuilder");
    }
    buf.println("  public static class Builder {");
    pojo.getDataMembers().stream()
        .forEach(dm -> buf.print("    private ").print(getDataType(dm, true)).print(" ")
            .print(dm.getName()).print(dm.getRequired() ? "" : " = Optional.empty()").println(";"));
    buf.println();
    buf.println("    private Builder() {}");
    buf.println();
    buf.print("    private Builder(").print(pojo.getClassName()).print(" ")
        .print(toCamel(pojo.getClassName())).println(") {");
    buf.print("      requireNonNull(").print(toCamel(pojo.getClassName())).print(", \"")
        .print(toCamel(pojo.getClassName())).println(" cannot be null\");");
    pojo.getDataMembers().stream()
        .forEach(dm -> buf.print("      this.").print(dm.getName()).print(" = ")
            .print(toCamel(pojo.getClassName())).print(".")
            .print(dm.getName()).println(";"));
    buf.println("    }");
    buf.println();
    buf.println("    public Builder with(Consumer<Builder> consumer) {");
    buf.println("      requireNonNull(consumer, \"consumer cannot be null\");");
    buf.println("      consumer.accept(this);");
    buf.println("      return this;");
    buf.println("    }");
    buf.println();
    pojo.getDataMembers().stream()
        .forEach(dm -> {
          if (pojo.getJacksonAnnotations()) {
            if (dm.getDataType().equals("LocalDate")) {
              buf.println("    @JsonDeserialize(using = LocalDateDeserializer.class)");
            } else if (dm.getDataType().equals("LocalDateTime")) {
              buf.println("    @JsonDeserialize(using = LocalDateTimeDeserializer.class)");
            } else if (dm.getDataType().equals("Date")) {
              buf.println(
                  "    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = \"dd-MM-yyyy hh:mm:ss\")");
            }
          }
          buf.print("    public Builder with").print(toHungarian(dm.getName())).print("(")
              .print(dm.getDataType()).print(" ").print(dm.getName()).println(") {");
          if (dm.getRequired() && !primitives.contains(dm.getDataType())) {
            buf.print("      requireNonNull(").print(dm.getName()).print(", \"").print(dm.getName())
                .println(" cannot be null\");");
          }
          String indent = "      ";
          if (!dm.getRequired() && !primitives.contains(dm.getDataType())) {
            buf.print("      if (").print(dm.getName()).println(" == null) {");
            buf.print("        this.").print(dm.getName()).println(" = Optional.empty();");
            buf.println("      } else {");
            indent = "        ";
          }
          buf.print(indent).print("this.").print(dm.getName()).print(" = ");
          if (!dm.getRequired()) {
            buf.print("Optional.of(");
          }
          if (PojoFlag.HAS_LIST.predicate.test(dm)) {
            buf.print("ImmutableList.copyOf(").print(dm.getName()).print(")");
          } else if (PojoFlag.HAS_MAP.predicate.test(dm)) {
            buf.print("ImmutableMap.copyOf(").print(dm.getName()).print(")");
          } else if (PojoFlag.HAS_SET.predicate.test(dm)) {
            buf.print("ImmutableSet.copyOf(").print(dm.getName()).print(")");
          } else {
            buf.print(dm.getName());
          }
          if (!dm.getRequired()) {
            buf.print(")");
          }
          buf.println(";");
          if (!dm.getRequired() && !primitives.contains(dm.getDataType())) {
            buf.println("      }");
          }
          buf.println("      return this;");
          buf.println("    }");
          buf.println();
        });
    buf.print("    public ").print(pojo.getClassName()).println(" build() {");
    pojo.getDataMembers().stream()
        .forEach(dm -> {
          if (dm.getRequired()) {
            buf.print("      requireNonNull(").print(dm.getName()).print(", \"").print(dm.getName())
                .println(" cannot be null\");");
          }
        });
    buf.print("      return new ").print(pojo.getClassName()).println("(this);");
    buf.println("    }");
    buf.println("  }");
    buf.println("}");
    return buf.toString();
  }

  private String toCamel(String s) {
    return Character.toLowerCase(s.charAt(0)) + (s.length() > 1 ? s.substring(1) : "");
  }

  private String toHungarian(String s) {
    return Character.toUpperCase(s.charAt(0)) + (s.length() > 1 ? s.substring(1) : "");
  }

  private String getDataType(PojoDataMember dm) {
    String dataType = dm.getDataType();
    if (!dm.getRequired()) {
      if (primitives.contains(dataType)) {
        dataType = primitiveWrappers.get(dataType);
      }
      dataType = "Optional<" + dataType + ">";
    }
    return dataType;
  }

  private String getDataType(PojoDataMember dm, boolean forceWrapper) {
    String dataType = dm.getDataType();
    if (!dm.getRequired()) {
      if (primitives.contains(dataType)) {
        dataType = primitiveWrappers.get(dataType);
      }
      dataType = "Optional<" + dataType + ">";
    }
    if (forceWrapper && primitives.contains(dataType)) {
      dataType = primitiveWrappers.get(dataType);
    }
    return dataType;
  }

  private static enum PojoFlag {
    // @formatter:off
    HAS_LOCAL_DATE(dm -> "LocalDate".equals(dm.getDataType())), 
    HAS_LOCAL_DATE_TIME(dm -> "LocalDateTime".equals(dm.getDataType())), 
    HAS_DATE(dm -> "Date".equals(dm.getDataType())), 
    HAS_MAP(dm -> dm.getDataType().equals("Map") ||
                  dm.getDataType().startsWith("Map<") ||
                  dm.getDataType().startsWith("Map ")), 
    HAS_SET(dm -> dm.getDataType().equals("Set") ||
                  dm.getDataType().startsWith("Set<") ||
                  dm.getDataType().startsWith("Set ")), 
    HAS_LIST(dm -> dm.getDataType().equals("List") ||
                   dm.getDataType().startsWith("List<") ||
                   dm.getDataType().startsWith("List ")),
    HAS_OPTIONAL(dm -> !dm.getRequired());
    // @formatter:on

    private final Predicate<PojoDataMember> predicate;

    private PojoFlag(Predicate<PojoDataMember> predicate) {
      this.predicate = predicate;
    }

    private Predicate<PojoDataMember> getPredicate() {
      return predicate;
    }
  }

  private static class PojoFlags {
    private final Map<PojoFlag, Boolean> flags;

    private PojoFlags(Pojo pojo) {
      Map<PojoFlag, Boolean> flagMap = Maps.newHashMap();
      pojo.getDataMembers().stream()
          .forEach(dm -> {
            Arrays.stream(PojoFlag.values())
                .forEach(flag -> {
                  boolean newValue = flag.predicate.test(dm);
                  flagMap.compute(flag, (f, v) -> (v == null) ? newValue : v || newValue);
                });
          });
      flags = ImmutableMap.copyOf(flagMap);
    }

    public boolean get(PojoFlag flag) {
      return flags.get(flag);
    }

  }

  private static class Buffer {
    private static final String NEWLINE = "\n";

    private final StringBuilder buf = new StringBuilder();

    private Buffer print(String text) {
      buf.append(text);
      return this;
    }

    private Buffer println(String text) {
      buf.append(text).append(NEWLINE);
      return this;
    }

    private Buffer println() {
      buf.append(NEWLINE);
      return this;
    }

    public String toString() {
      return buf.toString();
    }
  }

}
