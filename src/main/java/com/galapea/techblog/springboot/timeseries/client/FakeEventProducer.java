package com.galapea.techblog.springboot.timeseries.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galapea.techblog.springboot.timeseries.EventTrackerConstants;

import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;

@Component
public class FakeEventProducer {
    private final Logger log = LoggerFactory.getLogger(FakeEventProducer.class);
    private Faker faker;

    @PostConstruct
    private void fill() {
        this.faker = new Faker();
    }

    @Scheduled(fixedDelay = 1, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void scheduleEventCreation() throws IOException, InterruptedException {
        log.info("scheduleEventCreation()");

        HttpClient client = HttpClient.newHttpClient();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        for (int i = 0; i <= 10; i++) {
            Runnable runnableTask = () -> {
                try {
                    postEvent(client);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executorService.schedule(runnableTask, i, TimeUnit.SECONDS);
        }
    }

    private void postEvent(HttpClient client)
            throws JsonProcessingException, IOException, InterruptedException {
        Instant instant = Instant.now();
        var values = new HashMap<String, String>() {
            {
                put("appId", "APP_1");
                put("eventType", getRandomElement(EventTrackerConstants.EVENT_TYPE_LIST));
                put("externalId", faker.idNumber().valid());
                put("timestamp", instant.toString());
            }
        };

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/track"))
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .header("Content-type", "application/json").build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

}
