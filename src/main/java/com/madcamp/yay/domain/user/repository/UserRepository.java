package com.madcamp.yay.domain.user.repository;

import com.madcamp.yay.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
