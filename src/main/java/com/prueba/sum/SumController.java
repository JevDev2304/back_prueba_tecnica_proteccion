package com.prueba.sum;

import com.prueba.sum.SumRequestDTO;
import com.prueba.sum.SumResponseDTO;
import com.prueba.sum.SumResult;
import com.prueba.sum.SumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Demo", description = "Endpoints de demostración del stack")
public class SumController {

    private final SumService sumService;

    public SumController(SumService sumService) {
        this.sumService = sumService;
    }

    @PostMapping("/sum")
    @Operation(
        summary = "Suma dos números, persiste el resultado y envía email",
        description = "Valida los campos con Bean Validation, persiste el resultado en PostgreSQL y envía un email con AWS SES"
    )
    public ResponseEntity<SumResponseDTO> sum(@Valid @RequestBody SumRequestDTO request) {
        return ResponseEntity.ok(sumService.sum(request));
    }

    @GetMapping("/sum/history")
    @Operation(
        summary = "Historial de sumas por email",
        description = "Retorna todos los cálculos guardados en BD para un email dado"
    )
    public ResponseEntity<List<SumResult>> history(@RequestParam String email) {
        return ResponseEntity.ok(sumService.findByEmail(email));
    }
}
