package com.example.ingda.member.service;

import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.member.dto.MemberRequestDto;
import com.example.ingda.member.entity.Member;
import com.example.ingda.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    @Value("${spring.mail.username}")
    String senderEmail;

    @Transactional
    public void sendEmailForVerified(MemberRequestDto memberRequestDto) {

        Optional<Member> member = memberRepository.findByEmail(memberRequestDto.getEmail());
        if(member.isPresent()) throw new CustomException(ErrorCode.EMAIL_DUPLICATED);

        log.info("mail sender : {}", senderEmail);
        String code = UUID.randomUUID().toString().substring(0, 6);
        log.info("secret code : {}", code);
        MimeMessage message = javaMailSender.createMimeMessage();
        String email = memberRequestDto.getEmail();
        log.info("mail receiver : {}", email);
        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
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
    }
}
