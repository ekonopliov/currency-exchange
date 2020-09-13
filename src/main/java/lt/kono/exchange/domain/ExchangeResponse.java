package lt.kono.exchange.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Exchange response domain class
 *
 * @author ekonopliov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "exchange_amount",
        "exchange_rate",
        "from_currency",
        "to_currency",
        "request",
        "timestamp"
})
public class ExchangeResponse {

    @JsonProperty("exchange_amount")
    private BigDecimal exchangeAmount;

    @JsonProperty("exchange_rate")
    private BigDecimal exchangeRate;

    @JsonProperty("from_currency")
    private Currency fromCurrency;

    @JsonProperty("to_currency")
    private Currency toCurrency;

    private ExchangeRequest request;

    @Builder.Default
    private Date timestamp = new Date();

}
