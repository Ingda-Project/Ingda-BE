package com.example.ingda.domain.member.service;

import com.example.ingda.common.ResponseMessage;
import com.example.ingda.domain.member.dto.OAuthMemberDto;
import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.member.repository.MemberRepository;
import com.example.ingda.domain.member.type.OAuthType;
import com.example.ingda.domain.member.type.UserRoleType;
import com.example.ingda.domain.score.entity.Score;
import com.example.ingda.domain.score.repository.ScoreRepository;
import com.example.ingda.security.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleService implements SocialService {
    private final MemberRepository memberRepository;
    private final ScoreRepository scoreRepository;
    private final JwtUtil jwtUtil;

    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.secret}")
    private String googleClientSecret;
    @Value("${google.redirect.uri}")
    private String googleRedirectUrl;

    @Transactional
    public ResponseEntity<ResponseMessage> login(String code, HttpServletResponse response) throws JsonProcessingException{

        ResponseEntity<String> accessToken = getToken(code);

        OAuthMemberDto googleUserInfoDto = getOAuthMemberInfo(accessToken);
        Member googleMember = createOAuthMember(googleUserInfoDto);

        String createToken =  jwtUtil.createToken(googleMember.getEmail());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, createToken);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("로그인 성공", 200, null), HttpStatus.OK);
    }


    private ResponseEntity<String> getToken(String code) throws JsonProcessingException {
        RestTemplate rt = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", googleRedirectUrl);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity = rt.exchange("https://www.googleapis.com/oauth2/v4/token", HttpMethod.POST, httpEntity, String.class);

        return responseEntity;
    }


    private OAuthMemberDto getOAuthMemberInfo(ResponseEntity<String> accessToken) throws JsonProcessingException{
        String responseBody = accessToken.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String url = "https://www.googleapis.com/oauth2/v1/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer" + jsonNode.get("access_token"));
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> userinfoJson = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String userinfo = userinfoJson.getBody();

        jsonNode = objectMapper.readTree(userinfo);

        String id = jsonNode.get("id").asText();
        String nickname = jsonNode.get("name").asText();
        String email = jsonNode.get("email").asText();
        System.out.println("id : " + id + ", nickname : " + nickname + ", email : " + email );

        return new OAuthMemberDto(id, email, nickname);
    }


    private Member createOAuthMember(OAuthMemberDto OAuthMemberDto) {
        Member member = memberRepository.findByEmail(OAuthMemberDto.getEmail()).orElse(null);

        if(member != null){
            member = member.oAuthUpdate(OAuthType.GOOGLE);
        }else{
            Score score = Score.builder()
                    .loginScore(0)
                    .loginCount(1)
                    .diaryScore(0)
                    .diaryCount(1)
                    .reviewScore(0)
                    .build();
            scoreRepository.save(score);

            member = Member.builder()
                    .email(OAuthMemberDto.getEmail())
                    .nickname(OAuthMemberDto.getNickname())
                    .password("googleUser")
                    .userRole(UserRoleType.MEMBER)
                    .score(score)
                    .oAuthType(OAuthType.GOOGLE)
                    .build();

            memberRepository.save(member);
        }
        return member;
    }
}
