package com.example.ingda.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(timeToLive = 3600)
public class TempEmail {
    @Id
    private String email;

    private String code;
    private boolean verified;

    private LocalDateTime timeStamp;

    public void verifying(){
        this.verified = true;
    }
}
