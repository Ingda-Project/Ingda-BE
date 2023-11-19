package com.example.ingda.domain.chatgpt;


import com.example.ingda.domain.chatgpt.dto.RequestChatGptDto;
import com.example.ingda.domain.chatgpt.dto.ResponseChatGptDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChatgptService {
    //Config
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    @Value("${chatgpt.api-key}")
    private String API_KEY;
    public static final String MODEL = "gpt-3.5-turbo";
    public static final Integer MAX_TOKEN = 300;
    public static final Double TEMPERATURE = 0.0;
    public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate = new RestTemplate();



    public ResponseChatGptDto sendToChatgpt(String question) {
        //header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MEDIA_TYPE));
        headers.add(AUTHORIZATION, BEARER + API_KEY);

        //body
        RequestChatGptDto requestChatGptDto = new RequestChatGptDto(MODEL, List.of(RequestChatGptDto.Message.builder().role("user").content(question).build()) , MAX_TOKEN, TEMPERATURE, TOP_P);

        ResponseEntity<ResponseChatGptDto> responseEntity = restTemplate.postForEntity(
                URL,
                new HttpEntity<>(requestChatGptDto, headers),
                ResponseChatGptDto.class);

        return responseEntity.getBody();
    }
}
