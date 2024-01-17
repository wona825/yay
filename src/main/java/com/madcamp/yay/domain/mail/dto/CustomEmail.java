package com.madcamp.yay.domain.mail.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomEmail {
    private String toMail;
    private String subject;
    private String content;

    @Builder
    public CustomEmail(String toMail, String subject, String content) {
        this.toMail = toMail;
        this.subject = subject;
        this.content = content;
    }
}