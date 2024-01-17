package com.madcamp.yay.domain.mail.controller;

import com.madcamp.yay.domain.mail.dto.CustomEmail;
import com.madcamp.yay.domain.mail.dto.EmailCertification;
import com.madcamp.yay.domain.mail.dto.EmailCheck;
import com.madcamp.yay.domain.mail.service.MailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping ("/send/v1")
    public ResponseEntity<?> mailSend(@RequestBody @Valid EmailCertification.Request emailCertificationRequest) throws NoSuchAlgorithmException {

        return mailService.joinEmail(emailCertificationRequest);
    }

    @PostMapping("/send-custom/v1")
    public ResponseEntity<?> sendCustomEmail(@RequestParam Long userId, @RequestBody @Valid CustomEmail customEmail) {
        String toMail = customEmail.getToMail();
        String subject = customEmail.getSubject();
        String content = customEmail.getContent();

        return mailService.sendCustomEmail(userId, toMail, subject, content);
    }

    @PostMapping("/auth-check/v1")
    public ResponseEntity<?> authCheck(@RequestBody @Valid EmailCheck emailCheck) {

        return mailService.checkauthCode(emailCheck.getEmail(),emailCheck.getAuthCode());
    }
}
