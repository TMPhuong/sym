package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PriceData {
    private String currencyPair;
    private String tenor;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
}
