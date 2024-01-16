package com.madcamp.yay.domain.mail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailCertificationRequest {
    @Email
    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;

    @Builder
    public EmailCertificationRequest(String email) {
        this.email = email;
    }
}