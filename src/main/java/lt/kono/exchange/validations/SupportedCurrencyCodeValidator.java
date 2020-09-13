package lt.kono.exchange.validations;

import lt.kono.exchange.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Supported currency code validator
 * Validates that exchange service has currency on a list of supported
 *
 * @author ekonopliov
 */
class SupportedCurrencyCodeValidator implements ConstraintValidator<SupportedCurrencyCode, String> {

  @Autowired
  ExchangeService exchangeService;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return exchangeService.getAllCurrencies().stream()
            .anyMatch(currency -> currency.getName().equals(value));
  }
}
