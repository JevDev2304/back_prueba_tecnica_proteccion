# Tests de Integración

## Colección Postman

Importar en Postman: `File → Import → Paste Raw Text` con el JSON de abajo,
o importar el archivo `collection.json` si ya existe en este directorio.

```json
{
  "info": {
    "name": "Prueba Técnica API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    { "key": "base_url", "value": "http://localhost:8080" }
  ],
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "url": "{{base_url}}/actuator/health"
      }
    },
    {
      "name": "POST /api/sum - OK",
      "request": {
        "method": "POST",
        "url": "{{base_url}}/api/sum",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "body": {
          "mode": "raw",
          "raw": "{ \"a\": 5, \"b\": 3, \"email\": \"usuario@example.com\" }"
        }
      }
    },
    {
      "name": "POST /api/sum - Email inválido (400)",
      "request": {
        "method": "POST",
        "url": "{{base_url}}/api/sum",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "body": {
          "mode": "raw",
          "raw": "{ \"a\": 5, \"b\": 3, \"email\": \"no-es-email\" }"
        }
      }
    },
    {
      "name": "POST /api/sum - Campo nulo (400)",
      "request": {
        "method": "POST",
        "url": "{{base_url}}/api/sum",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "body": {
          "mode": "raw",
          "raw": "{ \"b\": 3, \"email\": \"usuario@example.com\" }"
        }
      }
    },
    {
      "name": "Swagger UI",
      "request": {
        "method": "GET",
        "url": "{{base_url}}/swagger-ui.html"
      }
    }
  ]
}
```

## Tests de integración con Spring Boot

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SumIntegrationTest {

    @Autowired MockMvc mockMvc;
    @MockBean EmailService emailService; // evitar llamadas reales a Resend

    @Test
    void flujoCompleto_suma() throws Exception {
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        mockMvc.perform(post("/api/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"a":10,"b":20,"email":"test@example.com"}"""))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value(30));
    }
}
```
