package lt.kono.exchange.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.kono.exchange.domain.Currency;
import lt.kono.exchange.domain.ExchangeRequest;
import lt.kono.exchange.services.ExchangeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExchangeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExchangeService exchangeService;

    private ExchangeRequest validRequest = ExchangeRequest.builder().from("EUR").to("USD").amount(BigDecimal.ONE).build();
    private ExchangeRequest invalidRequestWithoutAmount = ExchangeRequest.builder().from("EUR").to("USD").build();


    @Test
    @DisplayName("Should pass validation and return 200")
    void whenInputIsValid_thenReturnsStatus200() throws Exception {

        String body = objectMapper.writeValueAsString(validRequest);

        mvc.perform(post("/exchange")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Should return ordered JSON")
    void whenInputIsValid_thenReturnsOrderedJSON() throws Exception {

        String body = objectMapper.writeValueAsString(validRequest);
        String expectedBody = objectMapper.writeValueAsString(exchangeService.exchange(validRequest));

        mvc.perform(post("/exchange")
                .contentType("application/json")
                .content(body))
                .andExpect(content().json(expectedBody, true));
    }

    @Test
    @DisplayName("Should fail validation and return 400")
    void whenInputIsInvalid_thenReturnsStatus400() throws Exception {

        String body = objectMapper.writeValueAsString(invalidRequestWithoutAmount);

        mvc.perform(post("/exchange")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return supported currencies")
    void whenInputIsValid_thenReturnsAllSupportedCurrencies() throws Exception {

        List<Currency> supportedCurrencies = exchangeService.getAllCurrencies();
        String expectedBody = objectMapper.writeValueAsString(supportedCurrencies);

        mvc.perform(get("/exchange/supportedCurrencies"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedBody));
    }
}
