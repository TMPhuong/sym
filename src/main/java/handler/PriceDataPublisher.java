package handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class PriceDataPublisher {
    @Autowired
    private SimpMessagingTemplate template;
}
