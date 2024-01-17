package com.madcamp.yay.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginInfo {

    private String email;
    private String password;

    @Builder
    public LoginInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

}