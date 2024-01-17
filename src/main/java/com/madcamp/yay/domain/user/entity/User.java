package com.madcamp.yay.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    protected String email;
    protected String password;
    protected String nickname;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Friend> friends;
}
