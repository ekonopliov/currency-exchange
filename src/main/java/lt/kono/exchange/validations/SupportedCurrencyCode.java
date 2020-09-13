package lt.kono.exchange.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Supported currency code validation annotation
 *
 * @author ekonopliov
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = SupportedCurrencyCodeValidator.class)
@Documented
public @interface SupportedCurrencyCode {

  String message() default "Currency code is not supported";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}