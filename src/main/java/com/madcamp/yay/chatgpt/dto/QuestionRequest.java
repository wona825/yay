package com.madcamp.yay.chatgpt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class QuestionRequest implements Serializable {
    private String question;

    public QuestionRequest(String question) {
        this.question = question;
    }
}