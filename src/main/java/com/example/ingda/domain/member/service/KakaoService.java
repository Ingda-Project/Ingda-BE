package com.example.ingda.domain.member.service;

import com.example.ingda.common.ResponseMessage;
import com.example.ingda.domain.member.dto.KakaoMemberDto;
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
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final MemberRepository memberRepository;
    private final ScoreRepository scoreRepository;
    private final JwtUtil jwtUtil;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Transactional
    public ResponseEntity<ResponseMessage> login(String code, HttpServletResponse response) throws JsonProcessingException{
        String accessToken = getToken(code);
        KakaoMemberDto kakaoMemberDto = getKakaoUserInfo(accessToken);
        Member kakaoMember = createKakaoUser(kakaoMemberDto);

        String createToken = jwtUtil.createToken(kakaoMember.getEmail());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, createToken);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("로그인 성공", 200, createToken), HttpStatus.OK);
    }

    private Member createKakaoUser(KakaoMemberDto kakaoMemberDto) {
        String kakaoEmail = kakaoMemberDto.getEmail();
        Member member = memberRepository.findByEmail(kakaoEmail).orElse(null);

        if(member != null){
            member = member.oAuthUpdate(OAuthType.KAKAO);
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
                            .email(kakaoMemberDto.getEmail())
                            .nickname(kakaoMemberDto.getNickname())
                            .password("kakaoUser")
                            .userRole(UserRoleType.MEMBER)
                            .score(score)
                            .oAuthType(OAuthType.KAKAO)
                            .build();

            memberRepository.save(member);
        }
        return member;
    }

    private KakaoMemberDto getKakaoUserInfo(String accessToken) throws JsonProcessingException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String id = jsonNode.get("id").asText();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        return new KakaoMemberDto(id, email, nickname);
    }

    private String getToken(String code) throws JsonProcessingException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }
}
