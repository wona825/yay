package com.madcamp.yay.domain.user.controller;

import com.madcamp.yay.domain.user.dto.FriendInfo;
import com.madcamp.yay.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register-friend/v1")
    public ResponseEntity<?> registerFriend(@RequestParam Long userId, @RequestBody FriendInfo friendInfo) {

        return userService.registerFriend(userId, friendInfo);
    }

    @DeleteMapping("/delete-friend/v1")
    public ResponseEntity<?> deleteFriend(@RequestParam Long userId, @RequestParam String friendEmail) {

        return userService.deleteFriend(userId, friendEmail);
    }

    @GetMapping("/friend-list/v1")
    public List<FriendInfo> getFriendList(@RequestParam Long userId) {

        return userService.getFriendList(userId);
    }
}
