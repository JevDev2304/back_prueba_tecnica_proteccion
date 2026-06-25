package com.prueba.service.impl;

import com.prueba.dto.SumRequestDTO;
import com.prueba.dto.SumResponseDTO;
import com.prueba.exception.EmailSendException;
import com.prueba.model.SumResult;
import com.prueba.repository.SumResultRepository;
import com.prueba.service.EmailService;
import com.prueba.service.SumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SumServiceImpl implements SumService {

    private static final Logger log = LoggerFactory.getLogger(SumServiceImpl.class);

    private final EmailService emailService;
    private final SumResultRepository sumResultRepository;

    public SumServiceImpl(EmailService emailService, SumResultRepository sumResultRepository) {
        this.emailService = emailService;
        this.sumResultRepository = sumResultRepository;
    }

    @Override
    public SumResponseDTO sum(SumRequestDTO request) {
        int result = request.getA() + request.getB();

        SumResult sumResult = new SumResult(request.getA(), request.getB(), result, request.getEmail());
        sumResultRepository.save(sumResult);

        boolean emailSent = true;
        try {
            emailService.sendEmail(
                request.getEmail(),
                "Resultado de tu suma",
                "El resultado de " + request.getA() + " + " + request.getB() + " = " + result
            );
        } catch (EmailSendException e) {
            emailSent = false;
            log.warn("No se pudo enviar email a {}: {}", request.getEmail(), e.getMessage());
        }

        String message = emailSent
            ? "Result sent to " + request.getEmail()
            : "Result calculated for " + request.getEmail() + " (email delivery failed)";

        return new SumResponseDTO(result, message);
    }

    @Override
    public List<SumResult> findByEmail(String email) {
        return sumResultRepository.findByEmailOrderByCreatedAtDesc(email);
    }
}
