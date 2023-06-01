package com.market.coursework.config;

import com.market.coursework.client.MarketBenchmarkApiClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpClientFactory {

    @Value("${market-benchmark-api-client-service.url}")
    private String serviceUrl;

    @Value("${market-benchmark-api-client-service.connectTimeout:10000}")
    private int clientConnectTimeout;

    @Value("${market-benchmark-api-client-service.readTimeout:10000}")
    private int clientReadTimeout;

    @Value("${market-benchmark-api-client-service.writeTimeout:10000}")
    private int clientWriteTimeout;

    @Value("${market-benchmark-api-client-service.apikey}")
    private String apiKey;

    @Bean
    public MarketBenchmarkApiClient securityManagerApiClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(clientConnectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(clientReadTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(clientWriteTimeout, TimeUnit.MILLISECONDS)
                .build();

        return new MarketBenchmarkApiClient(serviceUrl, apiKey, okHttpClient);
    }

}
