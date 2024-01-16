package com.madcamp.yay.domain.chatgpt.config;

public class ChatGptConfig {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final Boolean STREAM = true;
    public static final String ROLE = "user";
    public static final Double TEMPERATURE = 0.6;
    public static final String MODEL = "gpt-3.5-turbo";
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/chat/completions";
}