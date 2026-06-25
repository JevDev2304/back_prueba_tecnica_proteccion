package com.prueba.service.impl;

import com.prueba.exception.EmailSendException;
import com.prueba.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class EmailServiceImpl implements EmailService {

    private final SesClient sesClient;
    private final String fromEmail;

    public EmailServiceImpl(SesClient sesClient,
                            @Value("${ses.from-email}") String fromEmail) {
        this.sesClient = sesClient;
        this.fromEmail = fromEmail;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SendEmailRequest request = SendEmailRequest.builder()
                    .source(fromEmail)
                    .destination(Destination.builder().toAddresses(to).build())
                    .message(Message.builder()
                            .subject(Content.builder().data(subject).charset("UTF-8").build())
                            .body(Body.builder()
                                    .html(Content.builder().data("<p>" + body + "</p>").charset("UTF-8").build())
                                    .build())
                            .build())
                    .build();

            sesClient.sendEmail(request);
        } catch (SesException e) {
            throw new EmailSendException("Error al enviar email a " + to + ": " + e.getMessage(), e);
        } catch (Exception e) {
            throw new EmailSendException("Error inesperado al enviar email a " + to + ": " + e.getMessage(), e);
        }
    }
}
