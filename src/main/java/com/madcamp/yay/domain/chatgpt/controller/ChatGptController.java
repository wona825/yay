package com.madcamp.yay.domain.chatgpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.madcamp.yay.domain.chatgpt.service.ChatGptService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/chat-gpt")
public class ChatGptController {

    private final ChatGptService chatGptService;

    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @GetMapping(value="ask-stream/v1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ask(@RequestParam String question){
        try {
            return chatGptService.ask(question);
        }catch (JsonProcessingException je){
            return Flux.empty();
        }
    }
}

