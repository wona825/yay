package com.madcamp.yay.domain.chatgpt.dto;

import io.github.flashvayne.chatgpt.dto.Usage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGptResponse implements Serializable {

    private String id;
    private String object;
    private LocalDate created;
    private List<Choice> choices;
    private Usage usage;

    @Builder
    public ChatGptResponse(String id, String object,
                           LocalDate created, List<Choice> choices,
                           Usage usage) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.choices = choices;
        this.usage = usage;
    }
}

