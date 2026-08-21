package com.journalApp.service;

import com.journalApp.enums.Sentiment;
import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {
    public Sentiment getSentiment(String text){
        return Sentiment.HAPPY;
    }
}
