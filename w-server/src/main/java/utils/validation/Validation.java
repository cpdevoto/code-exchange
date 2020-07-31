package utils.validation;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ninja.Result;
import ninja.Results;

public class Validation {
  Logger log = LoggerFactory.getLogger(Validation.class);

  private final Validator validator;

  public Validation() {
    ValidatorFactory factory = javax.validation.Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  public Optional<Result> validateInputs(String method, Object bean) {
    Set<ConstraintViolation<Object>> violations = validator.validate(bean);
    if (violations.size() > 0) {

      List<String> errors = violations.stream()
          .map(v -> v.getPropertyPath() + " " + v.getMessage() + " (received: "
              + v.getInvalidValue() + ")")
          .collect(Collectors.toList());
      StringBuilder buf =
          new StringBuilder("[" + method + "] The following input validation errors occurred:\n");
      errors.forEach(e -> buf.append(e).append("\n"));
      log.error(buf.toString());
      return Optional.of(Results.json().render(errors).status(400));
    }
    return Optional.empty();
  }


}
