package handler;

import model.CurrencyPairConfig;
import model.PriceData;
import model.PriceSide;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PriceDataGenerator {
    @Autowired
    private EventBus eventBus;

    @Autowired
    private Long priceGeneratorFixedDelay;

    @Autowired
    private Long priceGeneratorInitialDelay;

    @Autowired
    private Map<String, CurrencyPairConfig> currencyPairDelayMap;

    public PriceDataGenerator() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        for (Map.Entry<String, CurrencyPairConfig> entry : currencyPairDelayMap.entrySet()) {
            CurrencyPairConfig currencyPairConfig = entry.getValue();
            String currencyPair = entry.getKey();
            executorService.scheduleWithFixedDelay(() -> {
                BigDecimal bidPrice = generateFakePrice(currencyPairConfig.getMinBidPrice(), currencyPairConfig.getMaxBidPrice(), currencyPairConfig.getPricePrecision(), PriceSide.BUY);
                BigDecimal askPrice = generateFakePrice(currencyPairConfig.getMinAskPrice(), currencyPairConfig.getMaxAskPrice(), currencyPairConfig.getPricePrecision(), PriceSide.SELL);
                eventBus.notify("new_price", Event.wrap(new PriceData(currencyPair, "SP", bidPrice, askPrice)));
            }, priceGeneratorInitialDelay, currencyPairConfig.getFixedDelay(), TimeUnit.MILLISECONDS);
        }

    }

    private BigDecimal generateFakePrice(BigDecimal min, BigDecimal max, Byte precision, PriceSide side) {
        double randomPrice = RandomUtils.nextDouble(min.doubleValue(), max.doubleValue());
        return BigDecimal.valueOf(randomPrice).setScale(precision, getRoundingMode(side));
    }

    private int getRoundingMode(PriceSide side) {
        switch (side) {
            case BUY: return BigDecimal.ROUND_FLOOR;
            case SELL: return BigDecimal.ROUND_CEILING;
            default: return BigDecimal.ROUND_HALF_UP;
        }
    }
}
