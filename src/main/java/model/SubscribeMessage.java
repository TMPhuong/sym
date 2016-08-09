package model;

import lombok.Data;

@Data
public class SubscribeMessage {
    private String currencyPair;
    private String tenor;
}
