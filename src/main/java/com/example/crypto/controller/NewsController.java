package com.example.crypto.controller;

import com.example.crypto.config.CoinConfig;
import com.example.crypto.config.CoinData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

@RestController
public class NewsController {
    @Autowired
    CoinConfig coinConfig;
    RestClient restClient = RestClient.create();

    @GetMapping("/news")
    String getNews(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to
    ) {

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.putIfAbsent("page", page);
        parameterMap.putIfAbsent("limit", limit);
        parameterMap.putIfAbsent("from", from);
        parameterMap.putIfAbsent("to", to);

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
                .uri(CoinData.COIN_BASE_URL.getUrl() + "/news" + parameters)
                .header("X-Api-Key", coinConfig.getCoinApiKey())
                .retrieve()
                .body(String.class);
    }
}
