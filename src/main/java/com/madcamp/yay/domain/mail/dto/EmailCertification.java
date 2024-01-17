package com.madcamp.yay.domain.mail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class EmailCertification {

    @Data
    @Builder
    public static class Request {
        @Email
        @NotEmpty(message = "이메일을 입력해 주세요.")
        private String email;

        @NotEmpty(message = "비밀번호를 입력해 주세요.")
        private String password;

        @NotEmpty(message = "닉네임을 입력해 주세요.")
        private String nickname;
    }

    @Data
    @Builder
    public static class Response {
        private String email;
        private String certificationNumber;
    }
}