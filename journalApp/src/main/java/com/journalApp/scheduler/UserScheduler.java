package com.journalApp.scheduler;

import com.journalApp.cache.AppCache;
import com.journalApp.entity.JournalEntry;
import com.journalApp.entity.User;
import com.journalApp.enums.Sentiment;
import com.journalApp.repository.UserRepositoryImpl;
import com.journalApp.service.EmailService;
import com.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class UserScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

   // @Scheduled(cron = "0 0 9 ? * SUN *")
    public void fetchUserAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate()
                    .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x->sentimentAnalysisService.getSentiment(x.getContent())).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentence = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentence = entry.getKey();
                }
            }
            if (mostFrequentSentence != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentence.toString());
            }
        }
    }
}