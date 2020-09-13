package lt.kono.exchange.services;

import lombok.Data;
import lt.kono.exchange.domain.Currency;
import lt.kono.exchange.domain.ExchangeRequest;
import lt.kono.exchange.domain.ExchangeResponse;
import lt.kono.exchange.utils.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Exchange service
 *
 * @author ekonopliov
 */
@Service
@Validated
public class ExchangeService {

    @Value("${exchange.data.file}")
    private Resource dataResource;

    @Value("${exchange.amount.precision}")
    private int AMOUNT_PRECISION;

    @Value("${exchange.rate.precision}")
    private int RATE_PRECISION;

    @Autowired
    private CSVReader csvReader;

    private List<Currency> currencies = new ArrayList<>();

    /**
     * Reads currencies from data.csv file, post construct
     */
    @PostConstruct
    void postConstruct(){
        currencies = csvReader.loadObjectList(Currency.class, dataResource.getFilename());
    }

    /**
     * Exchanges currency
     *
     * @param exchangeRequest valid exchange request
     * @return ExchangeResponse detailed exchange info response
     */
    @Cacheable(value = "exchanges")
    public ExchangeResponse exchange(@Valid ExchangeRequest exchangeRequest){

        Currency fromCurrency = getCurrency(exchangeRequest.getFrom());

        Currency toCurrency = getCurrency(exchangeRequest.getTo());

        BigDecimal exchangeRate = toCurrency.getRate()
                .divide(fromCurrency.getRate(), RATE_PRECISION, RoundingMode.CEILING);
        BigDecimal exchangeAmount = exchangeRequest.getAmount()
                .divide(exchangeRate, AMOUNT_PRECISION, RoundingMode.CEILING);

        return ExchangeResponse.builder()
                .exchangeAmount(exchangeAmount)
                .exchangeRate(exchangeRate)
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .request(exchangeRequest)
                .build();
    }

    /**
     * Returns all stored currencies
     *
     * @return List of Currency
     */
    @Cacheable(value = "currencies")
    public List<Currency> getAllCurrencies() {
        return new ArrayList<>(currencies);
    }

    /**
     * Returns one Currency obj by code
     *
     * @param code currency code
     * @return Currency
     */
    public Currency getCurrency(String code){
        return currencies.stream()
                .filter(currency -> currency.getName().equals(code))
                .findFirst().get();
    }
}
