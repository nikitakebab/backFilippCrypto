package com.example.crypto.controller;

import com.example.crypto.config.CoinConfig;
import com.example.crypto.config.CoinData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController()
public class CoinController {

    @Autowired
    CoinConfig coinConfig;

    RestClient restClient = RestClient.create();

    @GetMapping("/coins")
    String getCoins() {

        return restClient.get()
                .uri(CoinData.COIN_BASE_URL.getUrl() + "/coins")
                .header("X-Api-Key", coinConfig.getCoinApiKey())
                .retrieve()
                .body(String.class);
    }

    @GetMapping("/coins/{coinId}")
    String getCoinById(@PathVariable(name = "coinId") String coinId) {

        return restClient.get()
                .uri(CoinData.COIN_BASE_URL.getUrl() + "/coins/" + coinId)
                .header("X-Api-Key", coinConfig.getCoinApiKey())
                .retrieve()
                .body(String.class);
    }

    @GetMapping("/coins/{coinId}/charts")
    String getChartsByCoinId(@PathVariable(name = "coinId") String coinId, @RequestParam(name = "period", defaultValue = "all") String period) {

        return restClient.get()
                .uri(CoinData.COIN_BASE_URL.getUrl() + "/coins/" + coinId + "/charts?period=" + period)
                .header("X-Api-Key", coinConfig.getCoinApiKey())
                .retrieve()
                .body(String.class);
    }
}
