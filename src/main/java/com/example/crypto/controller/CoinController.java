package com.example.crypto.controller;

import com.example.crypto.config.CoinConfig;
import com.example.crypto.config.CoinData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

@RestController()
@CrossOrigin
public class CoinController {

    @Autowired
    CoinConfig coinConfig;

    RestClient restClient = RestClient.create();

    @GetMapping("/coins")
    String getCoins(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(name = "currency", required = false) String currency,
            @RequestParam(name = "blockchain", required = false) String blockchain
    ) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.putIfAbsent("page", page);
        parameterMap.putIfAbsent("limit", limit);
        parameterMap.putIfAbsent("currency", currency);
        parameterMap.putIfAbsent("blockchain", blockchain);

        parameterMap.values().removeIf(Objects::isNull);

        String parameters = "";

        if(!parameterMap.isEmpty()){
            parameters = parameterMap.entrySet()
                    .stream()
                    .map(e -> e.getKey()+"="+e.getValue())
                    .collect(joining("&"));
            parameters = "?" + parameters;
        }

        return restClient.get()
                .uri(CoinData.COIN_BASE_URL.getUrl() + "/coins" + parameters)
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
