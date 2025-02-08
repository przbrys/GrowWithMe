package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.service.impl.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "asdw3123asdq556";
    private static final UsernamePasswordAuthenticationToken TOKEN = new UsernamePasswordAuthenticationToken(LOGIN, PASSWORD);

    private static MockedStatic<Instant> INSTANT;

    @Mock
    private JwtEncoder jwtEncoder;
    @Mock
    private JwtDecoder jwtDecoder;
    @Mock
    private Instant instant;
    @Mock
    private Jwt jwt;

    @InjectMocks
    private TokenService tested;

    @BeforeEach
    void setUp() {
        INSTANT = mockStatic(Instant.class);
    }

    @AfterEach
    void cleanUp() {
        INSTANT.close();
    }

    @Test
    void generateJWT() {
        //given //when
        INSTANT.when(Instant::now).thenReturn(instant);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);
        when(jwt.getTokenValue()).thenReturn("123241");

        String result = tested.generateJWT(TOKEN);

        //then
        assertThat(result).isEqualTo("123241");
    }
}
