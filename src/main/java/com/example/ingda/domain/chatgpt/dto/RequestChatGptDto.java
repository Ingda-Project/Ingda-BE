package com.example.ingda.domain.chatgpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class RequestChatGptDto {
    private String model;
    //private String prompt;

    private List<Message> messages;

    @JsonProperty("max_tokens")
    private Integer maxTokens;
    private Double temperature;
    @JsonProperty("top_p")
    private Double topP;

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Message{
        private String role;
        private String content;
    }
    @Builder
    public RequestChatGptDto(String model, List<Message> messages,
                             Integer maxTokens, Double temperature,
                             Double topP) {
        this.model = model;
        this.messages = messages;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.topP = topP;
    }
}
