package com.example.ingda.domain.member.service;

import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.domain.member.dto.MemberRequestDto;
import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.member.entity.TempEmail;
import com.example.ingda.domain.member.repository.MemberRepository;
import com.example.ingda.domain.member.repository.TempEmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final TempEmailRepository tempEmailRepository;

    @Value("${spring.mail.username}")
    String senderEmail;

    @Transactional
    public boolean sendEmailForVerified(MemberRequestDto memberRequestDto) {

        Optional<Member> member = memberRepository.findByEmail(memberRequestDto.getEmail());
        if(member.isPresent()) {
            if(member.get().getOAuthType() != null) throw new CustomException(ErrorCode.SOCIAL_MEMBER);
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }

        log.info("mail sender : {}", senderEmail);
        String code = UUID.randomUUID().toString().substring(0, 6);
        log.info("secret code : {}", code);
        MimeMessage message = javaMailSender.createMimeMessage();
        String receiverEmail = memberRequestDto.getEmail();
        log.info("mail receiver : {}", receiverEmail);
        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, receiverEmail);
            message.setSubject("Ingda 이메일 인증 서비스");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + code + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);


        tempEmailRepository.save(TempEmail.builder()
                                                .email(receiverEmail)
                                                .code(code)
                                                .verified(false)
                                                .build());
        return true;
    }

    public void verifyingEmail(Map<String, String> bodyMap) {
        String email = bodyMap.get("email");
        String code = bodyMap.get("code");
        TempEmail emailDto = tempEmailRepository.findById(email).orElseThrow(
                () -> new CustomException(ErrorCode.VERIFYING_CODE_WRONG)
        );

        if(!emailDto.getCode().equals(code)){
           throw new CustomException(ErrorCode.VERIFYING_CODE_WRONG);
        }
        emailDto.verifying();
        tempEmailRepository.save(emailDto);

    }
}
