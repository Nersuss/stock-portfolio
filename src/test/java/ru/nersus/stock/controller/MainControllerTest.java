package ru.nersus.stock.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.nersus.stock.jwt.JwtProvider;

import javax.crypto.SecretKey;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    JwtProvider jwtProvider;

    private final SecretKey jwtAccessSecret;

    public MainControllerTest(
            @Value("${jwt.secret.access}") String jwtAccessSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }

    @Test
    void getLandingRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isFound());
    }

    @Test
    void getLandingSuccess() throws Exception {
        mockMvc.perform(get("/?symbol=AFLT&period=month"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("landing"));
    }

    @Test
    void getPortfolioSuccess() throws Exception {
        // Given
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        String accessToken = Jwts.builder()
                .subject("email")
                .id(String.valueOf(1))
                .expiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .compact();
        //String accessToken = jwtProvider.generateAccessToken("email", 1);
        Cookie cookie = new Cookie("accessToken", accessToken);

        Mockito.when(jwtProvider.validateAccessToken(cookie.getValue())).thenReturn(true);
        Mockito.when(jwtProvider.getAccessClaims(cookie.getValue()))
                .thenReturn(
                        Jwts
                                .parser()
                                .verifyWith(jwtAccessSecret)
                                .build()
                                .parseSignedClaims(accessToken)
                                .getPayload()
                );
        // When & Then
        mockMvc.perform(get("/portfolio").cookie(cookie))
                .andExpect(status().isFound());
    }

}
