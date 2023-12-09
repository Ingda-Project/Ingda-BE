package com.example.ingda.domain.chatgpt;


import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.domain.chatgpt.dto.RequestChatGptDto;
import com.example.ingda.domain.chatgpt.dto.ResponseChatGptDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
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

    public static final String ORDER =
            "Modify my English diary for grammar or expression, and reply with the modified text only. " +
            "This is my English from now on. " +
            "If no changes needed, just say perfect.\n";
    private final RestTemplate restTemplate = new RestTemplate();



    public ResponseEntity<ResponseChatGptDto> sendToChatgpt(String question) {
        //header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MEDIA_TYPE));
        headers.add(AUTHORIZATION, BEARER + API_KEY);

        //body
        List<RequestChatGptDto.Message> messages = List.of(
                RequestChatGptDto.Message.builder().role("system").content(ORDER).build(),
                RequestChatGptDto.Message.builder().role("user").content(question).build()
        );
        RequestChatGptDto requestChatGptDto = new RequestChatGptDto(MODEL, messages , MAX_TOKEN, TEMPERATURE, TOP_P);
        try {
            ResponseEntity<ResponseChatGptDto> responseEntity = restTemplate.postForEntity(
                    URL,
                    new HttpEntity<>(requestChatGptDto, headers),
                    ResponseChatGptDto.class);
            return responseEntity;
        }catch(Exception e){
            log.error("gpt networking error >>>>>> " + e.getMessage());
            throw new CustomException(ErrorCode.GPT_ERROR);
        }
    }
}
