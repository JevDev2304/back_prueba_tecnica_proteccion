package com.prueba.service.impl;

import com.prueba.exception.EmailSendException;
import com.prueba.service.EmailService;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final Resend resend;
    private final String fromEmail;

    public EmailServiceImpl(Resend resend, @Value("${resend.from-email}") String fromEmail) {
        this.resend = resend;
        this.fromEmail = fromEmail;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            CreateEmailOptions options = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(to)
                .subject(subject)
                .html("<p>" + body + "</p>")
                .build();

            resend.emails().send(options);
        } catch (ResendException e) {
            throw new EmailSendException("Error al enviar email a " + to + ": " + e.getMessage(), e);
        }
    }
}
