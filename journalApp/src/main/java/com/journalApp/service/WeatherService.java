package com.journalApp.service;

import com.journalApp.api.response.WeatherResponse;
import com.journalApp.cache.AppCache;
import com.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String API_KEY;
   // private static final String API_KEY = "d266ddb33db035eacae6fc65f54ae036";

  //  private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse= redisService.get("weather_of_"+city,WeatherResponse.class);

        String finalAPI = appCache.appCache.get("APPCache.keys.Weather_API").toString().replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,API_KEY);

        if(weatherResponse != null){
            return weatherResponse;
        }

        RestTemplate restTemplate = new RestTemplate();

        WeatherResponse body = restTemplate.getForObject(
                finalAPI,
                WeatherResponse.class
        );

        return body;
    }
}