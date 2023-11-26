package com.example.ingda.domain.diary.service;

import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.domain.chatgpt.ChatgptService;
import com.example.ingda.domain.chatgpt.dto.Choice;
import com.example.ingda.domain.chatgpt.dto.ResponseChatGptDto;
import com.example.ingda.domain.diary.dto.ReviewRequestDto;
import com.example.ingda.domain.diary.dto.ReviewResponseDto;
import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.member.repository.MemberRepository;
import com.example.ingda.domain.score.dto.ScoreEventData;
import com.example.ingda.domain.score.type.ScoreType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ChatgptService chatgptService;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;


    @Transactional
    public ReviewResponseDto getReviewFromChatgpt(Long memberId, ReviewRequestDto requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        if(member.getReviewCount() == 0){
            throw new CustomException(ErrorCode.REVIEW_COUNT_LIMIT);
        }

        ResponseEntity<ResponseChatGptDto>  responseEntity = chatgptService.sendToChatgpt(requestDto.getContent());

        member.minusReviewCount();

        ResponseChatGptDto responseChatGptDto = responseEntity.getBody();
        ResponseChatGptDto.Usage usage = responseChatGptDto.getUsage();
        log.info("token usages >> prompt : {}, completion : {}, total : {}", usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());

        Choice choice = responseChatGptDto.getChoices().get(0);
        String review = choice.getMessage().getContent();

        publisher.publishEvent(ScoreEventData.builder()
                .member(member)
                .scoreType(ScoreType.REVIEW)
                .build());

        return ReviewResponseDto.builder().review(review).build();

    }

}
