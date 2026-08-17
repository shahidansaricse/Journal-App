package com.journalApp.service;

import com.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String API_KEY = "d266ddb33db035eacae6fc65f54ae036";

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {

        String finalAPI = API.replace("CITY",city).replace("API_KEY",API_KEY);

        RestTemplate restTemplate = new RestTemplate();

        WeatherResponse body = restTemplate.getForObject(
                finalAPI,
                WeatherResponse.class
        );

        return body;
    }
}