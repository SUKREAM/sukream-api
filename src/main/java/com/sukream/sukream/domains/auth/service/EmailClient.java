package com.sukream.sukream.domains.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailClient {

    private final JavaMailSender mailSender;

    public HttpStatus sendOneEmail(String email, String password) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // 수신자, 제목, 본문 등 설정
            String subject = "SUKREAM 비밀번호 안내";
            String body = "귀하의 임시 비밀번호는 "+ password + " 입니다.";

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);

            // Email 전송
            mailSender.send(mimeMessage);

            return HttpStatus.OK;

        } catch (MessagingException e){
            log.error("Error sending email", e);
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
