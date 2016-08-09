package handler;

import model.CurrencyPairConfig;
import model.PriceData;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
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
                BigDecimal bidPrice = generateFakePrice(currencyPairConfig.getMinBidPrice(), currencyPairConfig.getMaxBidPrice());
                BigDecimal askPrice = generateFakePrice(currencyPairConfig.getMinAskPrice(), currencyPairConfig.getMaxAskPrice());
                eventBus.notify("new_price", Event.wrap(new PriceData(currencyPair, "SP", bidPrice, askPrice)));
            }, priceGeneratorInitialDelay, currencyPairConfig.getFixedDelay(), TimeUnit.MILLISECONDS);
        }

    }

    private BigDecimal generateFakePrice(BigDecimal min, BigDecimal max, Byte precision) {
        RandomUtils.
        return null;
    }
}
