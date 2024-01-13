package com.madcamp.yay.chatgpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.madcamp.yay.chatgpt.ChatGptConfig;
import com.madcamp.yay.chatgpt.dto.ChatGptRequest;
import com.madcamp.yay.chatgpt.dto.Message;
import com.madcamp.yay.chatgpt.dto.QuestionRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ChatGptService {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @Value("${chatgpt.api-key}")
    private String chatGptKey;

    public Flux<String> ask(QuestionRequest questionRequest) throws JsonProcessingException {

        WebClient client = WebClient.builder()
                    .baseUrl(ChatGptConfig.URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + chatGptKey)
                    .build();

        List<Message> messages = new ArrayList<>();

        messages.add(Message.builder()
                .role(ChatGptConfig.ROLE)
                .content(questionRequest.getQuestion())
                .build());
        ChatGptRequest chatGptRequest = new ChatGptRequest(
                ChatGptConfig.MODEL,
                ChatGptConfig.TEMPERATURE,
                ChatGptConfig.STREAM,
                messages
        );

        Flux<String> eventStream = client.post()
                .bodyValue(objectMapper.writeValueAsString(chatGptRequest))
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response).path("choices").get(0).path("delta").path("content").asText();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return "";
                    }
                });

        return eventStream;
    }
}