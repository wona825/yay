package com.madcamp.yay.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendInfo {
    private String email;
    private String nickname;

    @Builder
    public FriendInfo(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}