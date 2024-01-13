package com.madcamp.yay.chatgpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class Choice implements Serializable {

    private Integer index;

    private Message message;

    @JsonProperty("finish_reason")
    private String finishReason;

    @Builder
    public Choice(Integer index, Message message, String finishReason) {
        this.index = index;
        this.message = message;
        this.finishReason = finishReason;
    }
}