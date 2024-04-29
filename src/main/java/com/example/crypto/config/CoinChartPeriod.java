package com.example.crypto.config;

import lombok.Getter;

@Getter
public enum CoinChartPeriod {
    all("all"), _24h("24h"), _1w("1w"), _1m("1m"), _3m("3m"), _6m("6m"), _1y("1y");

    private final String period;

    CoinChartPeriod(String period) {
        this.period = period;
    }

}
