package lt.kono.exchange.controllers;

import lombok.extern.slf4j.Slf4j;
import lt.kono.exchange.domain.ExchangeRequest;
import lt.kono.exchange.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Exchange service access controller
 *
 * @author ekonopliov
 */
@Slf4j
@RestController
@RequestMapping("/exchange")
@Validated
public class ExchangeController {

    @Autowired
    ExchangeService exchangeService;

    /**
     * Currency exchange endpoint
     *
     * @param exchangeRequest valid request
     * @return ExchangeResponse exchange service response
     */
    @PostMapping
    public ResponseEntity exchangeCurrency(@Valid @RequestBody ExchangeRequest exchangeRequest){
        log.info("Processing exchange request: " +  exchangeRequest);
        return ResponseEntity.ok(exchangeService.exchange(exchangeRequest));
    }

    /**
     *  Supported currencies listing endpoint
     *
     * @return List of Currency supported currencies listing
     */
    @GetMapping("/supportedCurrencies")
    public ResponseEntity getSupportedCurrencies(){
        return ResponseEntity.ok(exchangeService.getAllCurrencies());
    }
}
