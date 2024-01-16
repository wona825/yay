package com.madcamp.yay.domain.mail.controller;

import com.madcamp.yay.domain.mail.dto.EmailCertificationRequest;
import com.madcamp.yay.domain.mail.dto.EmailCheck;
import com.madcamp.yay.domain.mail.service.MailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping ("/send/v1")
    public ResponseEntity<?> mailSend(@RequestBody @Valid EmailCertificationRequest emailCertificationRequest) throws NoSuchAlgorithmException {

        return mailService.joinEmail(emailCertificationRequest);
    }
    @PostMapping("/auth-check/v1")
    public ResponseEntity<?> authCheck(@RequestBody @Valid EmailCheck emailCheck) {

        return mailService.checkauthCode(emailCheck.getEmail(),emailCheck.getAuthCode());
    }
}
