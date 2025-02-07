package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.*;
import com.GrowWithMe.GrowWithMe.repository.IBodyInformationRepository;
import com.GrowWithMe.GrowWithMe.service.impl.BodyInformationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BodyInformationServiceTest {
    private static final int BODY_INFORMATION_ID = 1;
    private static final Role ROLE_CLIENT = new Role(1, "CLIENT");
    private static final Role ROLE_TRAINER = new Role(2, "TRAINER");

    private static final User USER_TRAINER = new User(3, "login", "name", "surname", "password", Set.of(ROLE_CLIENT));
    private static final User USER_CLIENT = new User(4, "login2", "name2", "surname2", "password2", Set.of(ROLE_TRAINER));
    private static final Trainer TRAINER = new Trainer(5, 123456789, USER_TRAINER, emptyList());
    private static final Client CLIENT = new Client(6, 185, 123456789, USER_CLIENT, TRAINER, emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
    private static final BodyInformation BODY_INFORMATION = new BodyInformation(BODY_INFORMATION_ID, 100, 20, 110, 60, 35, 90, 35, "additionalInfo", CLIENT);

    @Mock
    private IBodyInformationRepository bodyInformationRepository;

    @InjectMocks
    private BodyInformationService tested;

    @Test
    void deleteBodyInformationEntity__1() {
        //given //when
        tested.deleteBodyInformationEntity(BODY_INFORMATION_ID);

        //then
        verify(bodyInformationRepository).deleteById(BODY_INFORMATION_ID);
    }

    @Test
    void deleteBodyInformationEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(bodyInformationRepository).deleteById(BODY_INFORMATION_ID);

        assertThatThrownBy(() -> tested.deleteBodyInformationEntity(BODY_INFORMATION_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("BodyInformation entity with id " + BODY_INFORMATION_ID + " not found");
    }

    @Test
    void deleteBodyInformationEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(bodyInformationRepository).deleteById(BODY_INFORMATION_ID);

        assertThatThrownBy(() -> tested.deleteBodyInformationEntity(BODY_INFORMATION_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting BodyInformation");
    }

    @Test
    void getAllBodyInformation() {
        //given
        List<BodyInformation> bodyInformations = List.of(BODY_INFORMATION);
        //when
        when(bodyInformationRepository.findAll()).thenReturn(bodyInformations);

        List<BodyInformation> result = tested.getAllBodyInformation();

        //then
        assertThat(result).isEqualTo(bodyInformations);
    }

    @Test
    void getBodyInformationById__1() {
        //given
        Optional<BodyInformation> expected = Optional.of(BODY_INFORMATION);

        //when
        when(bodyInformationRepository.findById(BODY_INFORMATION_ID)).thenReturn(expected);

        Optional<BodyInformation> result = tested.getBodyInformationById(BODY_INFORMATION_ID);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getBodyInformationById__2() {
        //given //when
        when(bodyInformationRepository.findById(BODY_INFORMATION_ID)).thenReturn(Optional.empty());

        Optional<BodyInformation> result = tested.getBodyInformationById(BODY_INFORMATION_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void createBodyInformationEntity__1() {
        //given //when
        tested.createBodyInformationEntity(BODY_INFORMATION);

        //then
        verify(bodyInformationRepository).save(eq(BODY_INFORMATION));
    }

    @Test
    void createBodyInformationEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(bodyInformationRepository).save(BODY_INFORMATION);

        assertThatThrownBy(() -> tested.createBodyInformationEntity(BODY_INFORMATION))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new BodyInformation.");
    }

    @Test
    void updateBodyInformation__1() {
        //given
        Optional<BodyInformation> expected = Optional.of(BODY_INFORMATION);

        //when
        when(bodyInformationRepository.findById(BODY_INFORMATION_ID)).thenReturn(expected);

        BodyInformation result = tested.updateBodyInformation(BODY_INFORMATION);

        //then
        verify(bodyInformationRepository).save(BODY_INFORMATION);
    }

    @Test
    void updateBodyInformation__2() {
        //given //when //then
        when(bodyInformationRepository.findById(BODY_INFORMATION_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tested.updateBodyInformation(BODY_INFORMATION))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("BodyInformation entity with id " + BODY_INFORMATION_ID + " not found");
    }
}
