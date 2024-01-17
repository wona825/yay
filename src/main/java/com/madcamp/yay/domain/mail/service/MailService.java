package com.madcamp.yay.domain.mail.service;

import com.madcamp.yay.domain.mail.dto.EmailCertification;
import com.madcamp.yay.domain.mail.redis.RedisUtil;
import com.madcamp.yay.domain.user.entity.User;
import com.madcamp.yay.domain.user.repository.UserRepository;
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
    private int authCode;
    private EmailCertification.Request pendingRegistrationInfo;

    private final UserRepository userRepository;


    public ResponseEntity<?> checkauthCode(String email, String authCode) {
        if (redisUtil.getData(authCode) == null) {
            throw new RuntimeException("이메일 인증 코드가 존재하지 않습니다.");
        } else if (!redisUtil.getData(authCode).equals(email)) {
            throw new RuntimeException("이메일 인증에 실패하였습니다.");
        }

        var checkUser = userRepository.findByEmail(email);
        if (checkUser.isPresent()) {
            throw new RuntimeException("중복된 이메일입니다.");
        }

        User user = User.builder()
                .nickname(pendingRegistrationInfo.getNickname())
                .password(pendingRegistrationInfo.getPassword())
                .email(pendingRegistrationInfo.getEmail())
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(userRepository.findByEmail(email).get().getId());
    }

    public String createCertificationNumber() throws NoSuchAlgorithmException {
        String result;

        do {
            int num = SecureRandom.getInstanceStrong().nextInt(999999);
            result = String.valueOf(num);
        } while (result.length() != 6);

        return result;
    }


    public ResponseEntity<?> joinEmail(EmailCertification.Request emailCertificationRequest) throws NoSuchAlgorithmException {
        pendingRegistrationInfo = emailCertificationRequest;
        authCode = Integer.parseInt(createCertificationNumber());
        String setFrom = "mail.to.yay@gmail.com";
        String toMail = emailCertificationRequest.getEmail();
        String title = "[yay] 회원 가입 인증 메일";
        String content =
                "안녕하세요." +
                "<br><br>" +
                "방문해주셔서 감사합니다, yay!" +
                "<br><br>" +
                "계정을 활성화하기 위해 아래의 인증 번호를 확인하여 이메일 인증을 완료해 주세요." +
                "<br><br>" +
                "인증 번호는 [" + authCode + "] 입니다." +
                "<br><br>" +
                "인증번호를 입력하면, 이메일 인증이 완료되며, yay의 모든 서비스를 이용하실 수 있습니다." +
                "<br><br>" +
                "yay를 이용해 주셔서 다시 한번 감사드리며, 언제든지 궁금한 점이 있으시면 저희 지원팀으로 연락주시기 바랍니다." +
                "<br><br>" +
                "감사합니다 <( _ _ )>" +
                "<br><br>" +
                "[yay 지원팀]"; //이메일 내용 삽입
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
            helper.setText(content,true);  // html 설정
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        redisUtil.setDataExpire(Integer.toString(authCode),toMail,60*5L);
    }

    public ResponseEntity<?> sendCustomEmail(Integer userId, String toMail, String subject, String content) {
        String email = userRepository.findById(userId).get().getEmail();
        String htmlContent = "[발신자]" + email + "<br><br>" + content.replace("\n", "<br>");

        String setFrom = "mail.to.yay@gmail.com";
        String title = "[yay 발신] " + subject;
        mailSend("mail.to.yay@gmail.com", toMail, title, htmlContent);

        return ResponseEntity.ok().build();
    }
}