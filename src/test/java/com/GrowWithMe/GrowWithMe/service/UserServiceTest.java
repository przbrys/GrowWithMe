package com.GrowWithMe.GrowWithMe.service;


import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.DTO.UserResponseDTO;
import com.GrowWithMe.GrowWithMe.model.Trainer;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.repository.IClientRepository;
import com.GrowWithMe.GrowWithMe.repository.ITrainerRepository;
import com.GrowWithMe.GrowWithMe.repository.IUserRepository;
import com.GrowWithMe.GrowWithMe.service.impl.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final int USER_ID = 1;
    private static final String USER_LOGIN = "testuser";
    private static final User USER = generateUser();
    private static final Trainer TRAINER = new Trainer();
    private static final Client CLIENT = new Client();

    @Mock
    private IUserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IClientRepository clientRepository;
    @Mock
    private ITrainerRepository trainerRepository;

    @InjectMocks
    private UserService tested;

    @Test
    void getAllUser() {
        //given
        List<User> users = List.of(USER);

        //when
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = tested.getAllUser();

        //then
        assertThat(result).isEqualTo(users);
    }

    @Test
    void getUserById__1() {
        //given
        UserResponseDTO expectedDTO = new UserResponseDTO();
        expectedDTO.setUserId(USER_ID);
        expectedDTO.setUserName(USER.getUserName());
        expectedDTO.setUserSurname(USER.getUserSurname());

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        when(userRepository.findTrainerIdByUserId(USER_ID)).thenReturn(null);
        when(userRepository.findClientIdByUserId(USER_ID)).thenReturn(null);
        Optional<UserResponseDTO> result = tested.getUserById(USER_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedDTO);
    }

    @Test
    void getUserById__2() {
        //given
        UserResponseDTO expectedDTO = new UserResponseDTO();
        expectedDTO.setUserId(USER_ID);
        expectedDTO.setUserName(USER.getUserName());
        expectedDTO.setUserSurname(USER.getUserSurname());
        expectedDTO.setTrainerId(2);
        expectedDTO.setTrainer(TRAINER);

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        when(userRepository.findTrainerIdByUserId(USER_ID)).thenReturn(2);
        when(userRepository.findClientIdByUserId(USER_ID)).thenReturn(null);
        when(trainerRepository.findById(2)).thenReturn(Optional.of(TRAINER));
        Optional<UserResponseDTO> result = tested.getUserById(USER_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedDTO);
    }

    @Test
    void getUserById__3() {
        //given
        UserResponseDTO expectedDTO = new UserResponseDTO();
        expectedDTO.setUserId(USER_ID);
        expectedDTO.setUserName(USER.getUserName());
        expectedDTO.setUserSurname(USER.getUserSurname());
        expectedDTO.setClientId(3);
        expectedDTO.setClient(CLIENT);

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        when(userRepository.findTrainerIdByUserId(USER_ID)).thenReturn(null);
        when(userRepository.findClientIdByUserId(USER_ID)).thenReturn(3);
        when(clientRepository.findById(3)).thenReturn(Optional.of(CLIENT));

        Optional<UserResponseDTO> result = tested.getUserById(USER_ID);
        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedDTO);
    }

    @Test
    void getUserById__4() {
        //given
        UserResponseDTO expectedDTO = new UserResponseDTO();
        expectedDTO.setUserId(USER_ID);
        expectedDTO.setUserName(USER.getUserName());
        expectedDTO.setUserSurname(USER.getUserSurname());
        expectedDTO.setTrainerId(2);
        expectedDTO.setClientId(3);
        expectedDTO.setTrainer(TRAINER);
        expectedDTO.setClient(CLIENT);

        //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        when(userRepository.findTrainerIdByUserId(USER_ID)).thenReturn(2);
        when(userRepository.findClientIdByUserId(USER_ID)).thenReturn(3);
        when(trainerRepository.findById(2)).thenReturn(Optional.of(TRAINER));
        when(clientRepository.findById(3)).thenReturn(Optional.of(CLIENT));

        Optional<UserResponseDTO> result = tested.getUserById(USER_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedDTO);
    }


    @Test
    void getUserById__5() {
        //given //when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        Optional<UserResponseDTO> result = tested.getUserById(USER_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteUserEntity__1() {
        //given //when //then
        tested.deleteUserEntity(USER_ID);

        verify(userRepository).deleteById(USER_ID);
    }

    @Test
    void deleteUserEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(userRepository).deleteById(USER_ID);

        assertThatThrownBy(() -> tested.deleteUserEntity(USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User entity with id " + USER_ID + " not found");
    }

    @Test
    void deleteUserEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(userRepository).deleteById(USER_ID);

        assertThatThrownBy(() -> tested.deleteUserEntity(USER_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting User");
    }

    @Test
    void createUserEntity__1() {
        //given //when //then
        User result = tested.createUserEntity(USER);

        verify(userRepository).save(USER);
        assertThat(result).isEqualTo(USER);
    }

    @Test
    void createUserEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(userRepository).save(USER);

        assertThatThrownBy(() -> tested.createUserEntity(USER))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new User.");
    }

    private static User generateUser() {
        User user = new User();
        user.setUserId(USER_ID);
        user.setUserName("Test");
        user.setUserSurname("User");
        user.setUserPassword("password");
        return user;
    }
}