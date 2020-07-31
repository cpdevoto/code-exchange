package dsl.pojo_generator;

import static dsl.pojo_generator.PojoGenerator.dataMember;
import static dsl.pojo_generator.PojoGenerator.forClass;

public class Main {

  public static void main(String[] args) {
    // A custom DSL implemented using the three methods described

    // @formatter:off
    String source = forClass("User")
        .inPackage("com.resolutebi.orders")
        .jacksonAnnotations()
        .dataMembers(
            dataMember(dm -> dm.name("id")
                .dataType("int")
                .required()
            ),
            dataMember(dm -> dm.name("age")
                .dataType("int")
            ),
            dataMember(dm -> dm.name("name")
                .dataType("String")
                .required()
            ),
            dataMember(dm -> dm.name("startDate")
                .dataType("LocalDate")
                .required()
            ),
            dataMember(dm -> dm.name("roles")
                .dataType("Set<Role>")
            )
        )
        .generate();
    // @formatter:on

    System.out.println(source);


  }

}
