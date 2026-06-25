# Tests Unitarios

## Ejecutar

```bash
cd back
./gradlew test
./gradlew test --tests "com.prueba.service.*"   # solo servicios
```

## Estructura

```
src/test/java/com/prueba/
├── service/
│   └── SumServiceTest.java
└── controller/
    └── SumControllerTest.java
```

## Patrones con Mockito

### Test de servicio

```java
@ExtendWith(MockitoExtension.class)
class SumServiceTest {

    @Mock
    EmailService emailService;

    @InjectMocks
    SumServiceImpl sumService;

    @Test
    void suma_retornaResultadoCorrecto() {
        SumRequestDTO req = new SumRequestDTO();
        req.setA(5); req.setB(3); req.setEmail("a@b.com");

        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        SumResponseDTO response = sumService.sum(req);

        assertEquals(8, response.getResult());
        verify(emailService, times(1)).sendEmail(eq("a@b.com"), anyString(), anyString());
    }

    @Test
    void suma_lanzaEmailSendException_cuandoResendFalla() {
        SumRequestDTO req = new SumRequestDTO();
        req.setA(1); req.setB(2); req.setEmail("a@b.com");

        doThrow(new EmailSendException("Resend error"))
            .when(emailService).sendEmail(anyString(), anyString(), anyString());

        assertThrows(EmailSendException.class, () -> sumService.sum(req));
    }
}
```

### Test de controller con MockMvc

```java
@WebMvcTest(SumController.class)
@Import(SecurityConfig.class)
class SumControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean SumService sumService;

    @Test
    void postSum_retorna200() throws Exception {
        when(sumService.sum(any())).thenReturn(new SumResponseDTO(8, "Result sent to a@b.com"));

        mockMvc.perform(post("/api/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"a":5,"b":3,"email":"a@b.com"}"""))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value(8));
    }

    @Test
    void postSum_retorna400_cuandoEmailInvalido() throws Exception {
        mockMvc.perform(post("/api/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"a":5,"b":3,"email":"no-es-email"}"""))
            .andExpect(status().isBadRequest());
    }
}
```
