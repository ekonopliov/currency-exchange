package lt.kono.exchange.services;

import lt.kono.exchange.domain.ExchangeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExchangeServiceIntegrationTest {

    @Autowired
    private ExchangeService service;

    private ExchangeRequest validRequest = ExchangeRequest.builder().from("EUR").to("USD").amount(BigDecimal.ONE).build();
    private ExchangeRequest invalidRequestWithoutAmount = ExchangeRequest.builder().from("EUR").to("USD").build();
    private ExchangeRequest invalidRequestWithoutFrom = ExchangeRequest.builder().to("USD").amount(BigDecimal.ONE).build();
    private ExchangeRequest invalidRequestWithoutTo = ExchangeRequest.builder().from("EUR").amount(BigDecimal.ONE).build();
    private ExchangeRequest invalidRequestNotExistingCurrency = ExchangeRequest.builder().from("notExisting").to("USD").amount(BigDecimal.ONE).build();
    private ExchangeRequest invalidRequestNotSupportedCurrency = ExchangeRequest.builder().from("LT").to("USD").amount(BigDecimal.ONE).build();


    @Test
    @DisplayName("Should throw ConstraintViolationException when not valid request")
    void whenInputIsInvalid_thenThrowsException(){

        assertThrows(ConstraintViolationException.class, () -> {
            service.exchange(invalidRequestWithoutAmount);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            service.exchange(invalidRequestWithoutFrom);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            service.exchange(invalidRequestWithoutTo);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            service.exchange(invalidRequestNotExistingCurrency);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            service.exchange(invalidRequestNotSupportedCurrency);
        });
    }

    @Test
    @DisplayName("Should return valid response")
    void whenInputIsValid_thenReturnsValidResponse(){
        assertEquals(validRequest.getAmount(),  service.exchange(validRequest).getRequest().getAmount());
        assertEquals(validRequest.getFrom(),  service.exchange(validRequest).getRequest().getFrom());
        assertEquals(validRequest.getTo(),  service.exchange(validRequest).getRequest().getTo());
    }
}