package com.market.coursework.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.market.coursework.model.Item;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

@Slf4j
public class MarketBenchmarkApiClient {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient okHttpClient;

    private final HttpUrl aiEndpoint;

    private final String apiKey;
    private final ObjectMapper objectMapper;

    public MarketBenchmarkApiClient(String baseUrl, String apiKey, OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.apiKey = apiKey;
        this.aiEndpoint = HttpUrl.parse(baseUrl);

        if (aiEndpoint == null) {
            throw new IllegalArgumentException("Provided an empty or invalid base url for market api");
        }
    }

    public double getBenchmark(Item item) {

//        """
//                {
//                  "Inputs": {
//                    "data": [
//                      {
//                        "manufacturer": "example_value",
//                        "cpuName": "example_value",
//                        "cores": 0,
//                        "threads": 0,
//                        "baseClock": 0.0,
//                        "turboClock": 0.0
//                      }
//                    ]
//                  },
//                  "GlobalParameters": 0.0
//                }""";
        JSONObject json = new JSONObject("""
                {
                  "Inputs": {
                    "data": [
                      {
                        "manufacturer": %s,
                        "cpuName": %s,
                        "cores": %d,
                        "threads": %d,
                        "baseClock": %d,
                        "turboClock": %d
                      }
                    ]
                  },
                  "GlobalParameters": 0.0
                }
                """.formatted(item.getManufacturer(), item.getName(), item.getCores(), item.getThreads(), item.getBaseClock(), item.getTurboClock()));

        RequestBody requestBody = RequestBody.create(json.toString(), JSON);
        // Replace this with the primary/secondary key or AMLToken for the endpoint
        if (apiKey.isEmpty()) {
            throw new IllegalArgumentException("A key should be provided to invoke the endpoint");
        }

        HttpUrl endpointUrl = aiEndpoint.newBuilder().addPathSegment("score").build();

        Request request = new Request.Builder()
                .url(endpointUrl)
                .header("Authorization", "Bearer " + apiKey)
                .post(requestBody)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String answer = response.body().string();
                return Double.parseDouble(answer.substring(answer.indexOf('[')+1, answer.indexOf(']')));
            } else {
                log.error("log-tracking=5f2f548e-2752-45d2-8040-5fd477a8293a, message=\"Failed to check benchmark\", "
                        + " responseCode={}, responseMessage={}", response.code(), response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }
}
