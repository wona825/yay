package com.madcamp.yay.domain.user.service;

import com.madcamp.yay.domain.user.dto.FriendInfo;
import com.madcamp.yay.domain.user.dto.LoginInfo;
import com.madcamp.yay.domain.user.entity.Friend;
import com.madcamp.yay.domain.user.entity.User;
import com.madcamp.yay.domain.user.repository.FriendRepository;
import com.madcamp.yay.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public ResponseEntity<?> registerFriend(Long userId, FriendInfo friendInfo) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        if (friendRepository.findByEmail(friendInfo.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 친구 이메일입니다.");
        }

        Friend friend = Friend.builder()
                .email(friendInfo.getEmail())
                .nickname(friendInfo.getNickname())
                .user(user)
                .build();

        friendRepository.save(friend);

        return ResponseEntity.ok(null);
    }

    public List<FriendInfo> getFriendList(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        List<FriendInfo> friendInfos = user.getFriends().stream().map(friend ->
                    FriendInfo.builder()
                            .nickname(friend.getNickname())
                            .email(friend.getEmail())
                            .build()
        ).toList();

        return friendInfos;
    }

    public ResponseEntity<?> deleteFriend(Long userId, String friendEmail) {

        Friend friend = friendRepository.findByUserIdAndFriendEmail(userId, friendEmail).orElseThrow(() -> new RuntimeException("해당 친구를 찾을 수 없습니다."));

        friendRepository.delete(friend);

        return ResponseEntity.ok(null);
    }

    public ResponseEntity<?> login(LoginInfo loginInfo) {
        User user = userRepository.findByEmail(loginInfo.getEmail()).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        if (!user.getPassword().equals(loginInfo.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok(user.getId());
    }
}
