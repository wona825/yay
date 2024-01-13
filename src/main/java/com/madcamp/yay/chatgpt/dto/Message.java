package com.madcamp.yay.chatgpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class Message implements Serializable {
    String role;
    String content;

    @Builder
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
