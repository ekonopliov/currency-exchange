package lt.kono.exchange.services;

import lt.kono.exchange.domain.Currency;
import lt.kono.exchange.domain.ExchangeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceIMockedTest {

    @Mock
    ExchangeService service;

    @Value("${exchange.amount.precision}")
    private int AMOUNT_PRECISION;

    @Value("${exchange.rate.precision}")
    private int RATE_PRECISION;

    private Currency fromCurrency = Currency.builder().name("fromCurrency").rate(BigDecimal.valueOf(0.809552722)).build();
    private Currency toCurrency = Currency.builder().name("toCurrency").rate(BigDecimal.valueOf(0.025)).build();
    private ExchangeRequest validRequest = ExchangeRequest.builder().from("fromCurrency").to("toCurrency").amount(BigDecimal.TEN).build();

    @BeforeEach
    void setUp(){
        when(service.getCurrency("fromCurrency")).thenReturn(fromCurrency);
        when(service.getCurrency("toCurrency")).thenReturn(toCurrency);

        doCallRealMethod().when(service)
                .exchange(Mockito.any(ExchangeRequest.class));
    }

    @Test
    @DisplayName("Should exchange correctly")
    void whenInputIsValid_thenExchangesCorrectly(){
        BigDecimal expectedExchangeRate = toCurrency.getRate().divide(fromCurrency.getRate(), RATE_PRECISION, RoundingMode.CEILING);
        BigDecimal expectedExchangeAmount = validRequest.getAmount().divide(expectedExchangeRate, AMOUNT_PRECISION, RoundingMode.CEILING);

        assertEquals(expectedExchangeRate, service.exchange(validRequest).getExchangeRate());
        assertEquals(expectedExchangeAmount, service.exchange(validRequest).getExchangeAmount());
    }


    @Test
    @DisplayName("Should return with precision")
    void whenInputIsValid_thenReturnsWithPrecision(){
        assertEquals(RATE_PRECISION, service.exchange(validRequest).getExchangeRate().scale());
        assertEquals(AMOUNT_PRECISION, service.exchange(validRequest).getExchangeAmount().scale());
    }

    @Test
    @DisplayName("Should return exact request")
    void whenInputIsValid_thenReturnsExactRequest(){
        assertEquals(validRequest.getAmount(),  service.exchange(validRequest).getRequest().getAmount());
        assertEquals(validRequest.getFrom(),  service.exchange(validRequest).getRequest().getFrom());
        assertEquals(validRequest.getTo(),  service.exchange(validRequest).getRequest().getTo());
    }
}