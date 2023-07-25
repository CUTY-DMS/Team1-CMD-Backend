package com.example.cmd.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Indexed;

import java.io.Serializable;

@Getter
@Builder
@RedisHash(value = "refreshToken")
public class RefreshToken implements Serializable {
    @Id
    private String email;

    private String refreshToken;

    private Long expiration;

    public RefreshToken updateExpiration(Long expiration) {
        this.expiration = expiration;
        return this;
    }
}