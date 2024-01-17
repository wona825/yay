package com.madcamp.yay.domain.user.repository;

import com.madcamp.yay.domain.user.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByEmail(String email);

    @Query("SELECT f FROM Friend f WHERE f.user.id = :userId AND f.email = :friendEmail" )
    Optional<Friend> findByUserIdAndFriendEmail(@Param("userId") Integer userId, @Param("friendEmail") String friendEmail);
}
