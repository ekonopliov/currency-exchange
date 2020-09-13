package lt.kono.exchange.validations;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * Currency code validator
 *
 * @author ekonopliov
 */
class CurrencyCodeValidator implements ConstraintValidator<CurrencyCode, String> {

  @Value("#{'${exchange.currency.codes}'.split(', ')}")
  private List currencyCodes;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return currencyCodes.contains(value);
  }
}
