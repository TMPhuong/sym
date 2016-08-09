package controller;

import lombok.extern.slf4j.Slf4j;
import model.PriceData;
import model.SubscribeMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
@Slf4j
public class MyWebSocketController {

    @MessageMapping("/test")
    @SendTo("/sym/subscribe")
    public PriceData subscribe(SubscribeMessage message) throws Exception {
        log.info("Received subscribe msg for {}-{}", message.getCurrencyPair(), message.getTenor());
        Thread.sleep(500);
        return new PriceData(message.getCurrencyPair(), message.getTenor(), new BigDecimal("1.2"), new BigDecimal("1.3"));
    }

}
