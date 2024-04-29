package com.example.crypto.config;

import lombok.Getter;

@Getter
public enum CoinData {
    COIN_BASE_URL("https://openapiv1.coinstats.app");

    private final String url;

    CoinData(String url) {
        this.url = url;
    }
}
