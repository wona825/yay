package com.madcamp.yay.domain.mail.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class EmailCertificationResponse {
    private String email;
    private String certificationNumber;

    @Builder
    public EmailCertificationResponse(String email, String certificationNumber) {
        this.email = email;
        this.certificationNumber = certificationNumber;
    }

}