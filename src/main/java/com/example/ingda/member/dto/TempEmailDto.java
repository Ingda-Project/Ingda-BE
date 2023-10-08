package com.example.ingda.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(timeToLive = 300)
public class TempEmailDto {
    @Id
    private String email;

    private String code;
    private boolean verified;

    public void verifying(){
        this.verified = true;
    }
}
