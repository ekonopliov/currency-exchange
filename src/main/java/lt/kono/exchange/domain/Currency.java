package lt.kono.exchange.domain;

import lombok.*;

import java.math.BigDecimal;

/**
 * Currency domain class
 *
 * @author ekonopliov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Currency {

    @NonNull
    private String name;

    @NonNull
    private BigDecimal rate;
}
