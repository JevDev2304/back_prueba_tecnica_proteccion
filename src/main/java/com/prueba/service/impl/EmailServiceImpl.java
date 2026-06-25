package com.prueba.service.impl;

import com.prueba.exception.EmailSendException;
import com.prueba.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class EmailServiceImpl implements EmailService {

    private final SesClient sesClient;
    private final String fromEmail;

    public EmailServiceImpl(@Value("${ses.from-email}") String fromEmail,
                            @Value("${ses.region:us-east-1}") String region) {
        this.fromEmail = fromEmail;
        this.sesClient = SesClient.builder()
                .region(Region.of(region))
                .build();
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
