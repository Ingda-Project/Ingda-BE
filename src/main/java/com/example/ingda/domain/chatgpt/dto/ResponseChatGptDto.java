package com.example.ingda.domain.chatgpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseChatGptDto {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;

    private Usage usage;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage{
        @JsonProperty("prompt_tokens")
        private Integer promptTokens;
        @JsonProperty("completion_tokens")
        private Integer completionTokens;
        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }
}
