package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CurrencyPairConfig {
    private String currencyPair;
    private String tenor;
    private Long fixedDelay;
    private BigDecimal minBidPrice, maxBidPrice, minAskPrice, maxAskPrice;
    private Byte pricePrecision;
}
