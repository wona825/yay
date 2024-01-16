package com.madcamp.yay.domain.mail.service;

import com.madcamp.yay.domain.mail.redis.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


@Service
@RequiredArgsConstructor
@Transactional
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private  RedisUtil redisUtil;
    private int authCodeber;


    public ResponseEntity<?> checkauthCode(String email, String authCode) {
        if (redisUtil.getData(authCode) == null) {
            throw new RuntimeException("이메일 인증 코드가 존재하지 않습니다.");
        } else if (!redisUtil.getData(authCode).equals(email)) {
            throw new RuntimeException("이메일 인증에 실패하였습니다.");
        }
        return ResponseEntity.ok().build();
    }

    public String createCertificationNumber() throws NoSuchAlgorithmException {
        String result;

        do {
            int num = SecureRandom.getInstanceStrong().nextInt(999999);
            result = String.valueOf(num);
        } while (result.length() != 6);

        return result;
    }


    public ResponseEntity<?> joinEmail(String email) throws NoSuchAlgorithmException {
        authCodeber = Integer.parseInt(createCertificationNumber());
        String setFrom = "chajiwon6@gmail.com";
        String toMail = email;
        String title = "[yay] 회원 가입 인증 메일";
        String content =
                "방문해주셔서 감사합니다, yay!" +
                        "<br><br>" +
                        "인증 번호는 [" + authCodeber + "] 입니다." +
                        "<br><br>" +
                        "yay로 돌아가서 인증번호를 입력해주세요." +
                        "<br><br>" +
                        "감사합니다 <( _ _ )>"; //이메일 내용 삽입
        mailSend(setFrom, toMail, title, content);
        return ResponseEntity.ok().build();
    }

    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

            helper.setFrom(setFrom);            // 이메일의 발신자 주소 설정
            helper.setTo(toMail);               // 이메일의 수신자 주소 설정
            helper.setSubject(title);           // 이메일의 제목을 설정
            helper.setText(content,true);  // 두 번째 매개 변수에 true를 설정하여 html 설정으로 한다.
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        redisUtil.setDataExpire(Integer.toString(authCodeber),toMail,60*5L);
    }
}