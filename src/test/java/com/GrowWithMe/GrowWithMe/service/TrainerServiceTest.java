package com.GrowWithMe.GrowWithMe.service;


import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Role;
import com.GrowWithMe.GrowWithMe.model.Trainer;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.repository.ITrainerRepository;
import com.GrowWithMe.GrowWithMe.service.impl.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {
    private static final int TRAINER_ID = 5;
    private static final Role ROLE_CLIENT = new Role(1, "CLIENT");
    private static final User USER_TRAINER = new User(3, "login", "name", "surname", "password", Set.of(ROLE_CLIENT));
    private static final Trainer TRAINER = new Trainer(TRAINER_ID, 123456789, USER_TRAINER, emptyList());

    @Mock
    private ITrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService tested;

    @Test
    void getAllTrainer() {
        //given
        List<Trainer> trainers = List.of(TRAINER);

        //when
        when(trainerRepository.findAll()).thenReturn(trainers);
        List<Trainer> result = tested.getAllTrainer();

        //then
        assertThat(result).isEqualTo(trainers);
    }

    @Test
    void getTrainerClient__1() {
        //given //when
        when(trainerRepository.findById(TRAINER_ID)).thenReturn(Optional.of(TRAINER));
        List<Client> result = tested.getTrainerClient(TRAINER_ID);

        //then
        assertThat(result).isEqualTo(TRAINER.getClientList());
    }

    @Test
    void getTrainerClient__2() {
        //given //when
        when(trainerRepository.findById(TRAINER_ID)).thenReturn(Optional.empty());
        List<Client> result = tested.getTrainerClient(TRAINER_ID);

        //then
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void getTrainerClient__3() {
        //given //when //then
        when(trainerRepository.findById(TRAINER_ID)).thenThrow(new RuntimeException());

        assertThatThrownBy(() -> tested.getTrainerClient(TRAINER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Trainer with id " + TRAINER_ID + "not found.");
    }

    @Test
    void getTrainerById__1() {
        //given //when
        when(trainerRepository.findById(TRAINER_ID)).thenReturn(Optional.of(TRAINER));
        Optional<Trainer> result = tested.getTrainerById(TRAINER_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(TRAINER);
    }

    @Test
    void getTrainerById__2() {
        //given //when
        when(trainerRepository.findById(TRAINER_ID)).thenReturn(Optional.empty());
        Optional<Trainer> result = tested.getTrainerById(TRAINER_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteTrainerById__1() {
        //given //when
        tested.deleteTrainerById(TRAINER_ID);

        //then
        verify(trainerRepository).deleteById(TRAINER_ID);
    }

    @Test
    void deleteTrainerById__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(trainerRepository).deleteById(TRAINER_ID);

        assertThatThrownBy(() -> tested.deleteTrainerById(TRAINER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Trainer entity with id " + TRAINER_ID + " not found");
    }

    @Test
    void deleteTrainerById__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(trainerRepository).deleteById(TRAINER_ID);

        assertThatThrownBy(() -> tested.deleteTrainerById(TRAINER_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting Trainer");
    }

    @Test
    void createTrainerEntity__1() {
        //given //when
        Trainer result = tested.createTrainerEntity(TRAINER);

        //then
        verify(trainerRepository).save(TRAINER);
        assertThat(result).isEqualTo(TRAINER);
    }

    @Test
    void createTrainerEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(trainerRepository).save(TRAINER);

        assertThatThrownBy(() -> tested.createTrainerEntity(TRAINER))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new Trainer.");
    }

    @Test
    void updateTrainer__1() {
        //given
        Trainer trainerToUpdate = new Trainer();
        trainerToUpdate.setTrainerId(TRAINER_ID);
        trainerToUpdate.setClientList(List.of(new Client())); // Update clientList


        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setTrainerId(TRAINER_ID);
        expectedTrainer.setClientList(List.of(new Client()));

        //when
        when(trainerRepository.findById(TRAINER_ID)).thenReturn(Optional.of(TRAINER));
        when(trainerRepository.save(any(Trainer.class))).thenReturn(expectedTrainer);
        Trainer result = tested.updateTrainer(trainerToUpdate);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getClientList()).isEqualTo(List.of(new Client()));
        verify(trainerRepository).save(argThat(t -> t.getTrainerId().equals(TRAINER_ID) && t.getClientList().equals(List.of(new Client()))));
    }
}