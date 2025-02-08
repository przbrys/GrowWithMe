package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.DTO.LoginResponseDTO;
import com.GrowWithMe.GrowWithMe.model.Role;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.repository.IRoleRepository;
import com.GrowWithMe.GrowWithMe.repository.IUserRepository;
import com.GrowWithMe.GrowWithMe.service.impl.AuthenticationService;
import com.GrowWithMe.GrowWithMe.service.impl.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private static final String LOGIN = "login";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PASSWORD = "asdw3123asdq556";
    private static final String ENCODED_PASSWORD = "pasSwOrD!";
    private static final String AUTHORITY = "CLIENT";
    private static final Role ROLE_CLIENT = new Role(1, AUTHORITY);
    private static final User USER = new User(0, LOGIN, NAME, SURNAME, ENCODED_PASSWORD, Set.of(ROLE_CLIENT));
    private static final UsernamePasswordAuthenticationToken TOKEN = new UsernamePasswordAuthenticationToken(LOGIN, PASSWORD);
    private static final LoginResponseDTO DTO = new LoginResponseDTO(USER, "token", 7, 0);

    @Mock
    private IUserRepository userRepository;
    @Mock
    private IRoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationService tested;

    @Test
    void registerUser__1() {
        //given //when
        mockRegisterUser();
        User result = tested.registerUser(LOGIN, NAME, SURNAME, PASSWORD, AUTHORITY);

        //then
        verify(userRepository).save(USER);
    }

    @Test
    void registerUser__2() {
        //given //when //then
        mockRegisterUser();

        doThrow(DataIntegrityViolationException.class).when(userRepository).save(any(User.class));
        assertThatThrownBy(() -> tested.registerUser(LOGIN, NAME, SURNAME, PASSWORD, AUTHORITY))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User with this login already exists.");
    }

    @Test
    void registerUser__3() {
        //given //when //then
        assertThatThrownBy(() -> tested.registerUser(LOGIN, NAME, SURNAME, PASSWORD, AUTHORITY))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error during user registration.");
    }

    @Test
    void loginUser__1() {
        //given //when
        when(authenticationManager.authenticate(TOKEN)).thenReturn(TOKEN);
        when(tokenService.generateJWT(TOKEN)).thenReturn("token");
        when(userRepository.findByUserLogin(LOGIN)).thenReturn(Optional.of(USER));
        when(userRepository.findTrainerIdByUserId(0)).thenReturn(7);
        when(userRepository.findClientIdByUserId(0)).thenReturn(0);

        LoginResponseDTO result = tested.loginUser(LOGIN, PASSWORD);

        //then
        assertThat(result).isEqualTo(DTO);
    }

    @Test
    void loginUser__2() {
        //given //when
        doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(TOKEN);
        LoginResponseDTO result = tested.loginUser(LOGIN, PASSWORD);

        //then
        assertThat(result).isNull();
    }

    private void mockRegisterUser() {
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(roleRepository.findByAuthority(AUTHORITY.toUpperCase())).thenReturn(Optional.of(ROLE_CLIENT));
    }
}
