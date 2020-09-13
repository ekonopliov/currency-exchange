package lt.kono.exchange.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lt.kono.exchange.validations.CurrencyCode;
import lt.kono.exchange.validations.SupportedCurrencyCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Exchange request domain class
 * Custom validators: @CurrencyCode, @SupportedCurrencyCode
 *
 * @author ekonopliov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRequest {

    @CurrencyCode
    @SupportedCurrencyCode
    @NotEmpty(message = "Sell currency code must be provided")
    private String from;

    @CurrencyCode
    @SupportedCurrencyCode
    @NotEmpty(message = "Exchange currency code must be provided")
    private String to;

    @NotNull(message = "Amount must be set")
    private BigDecimal amount;

}
