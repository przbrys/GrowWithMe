package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Question;
import com.GrowWithMe.GrowWithMe.repository.IQuestionRepository;
import com.GrowWithMe.GrowWithMe.service.impl.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    private static final int QUESTION_ID = 1;
    private static final Question QUESTION = generateQuestion();

    @Mock
    private IQuestionRepository questionRepository;

    @InjectMocks
    private QuestionService tested;

    @Test
    void getAllQuestion() {
        //given
        List<Question> questions = List.of(QUESTION);

        //when
        when(questionRepository.findAll()).thenReturn(questions);
        List<Question> result = tested.getAllQuestion();

        //then
        assertThat(result).isEqualTo(questions);
    }

    @Test
    void getQuestionById__1() {
        //given //when
        when(questionRepository.findById(QUESTION_ID)).thenReturn(Optional.of(QUESTION));
        Optional<Question> result = tested.getQuestionById(QUESTION_ID);

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(QUESTION);
    }

    @Test
    void getQuestionById__2() {
        //given //when
        when(questionRepository.findById(QUESTION_ID)).thenReturn(Optional.empty());
        Optional<Question> result = tested.getQuestionById(QUESTION_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteQuestionEntity__1() {
        //given //when
        tested.deleteQuestionEntity(QUESTION_ID);

        //then
        verify(questionRepository).deleteById(QUESTION_ID);
    }

    @Test
    void deleteQuestionEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(questionRepository).deleteById(QUESTION_ID);

        assertThatThrownBy(() -> tested.deleteQuestionEntity(QUESTION_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Question entity with id " + QUESTION_ID + " not found");
    }

    @Test
    void deleteQuestionEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(questionRepository).deleteById(QUESTION_ID);

        assertThatThrownBy(() -> tested.deleteQuestionEntity(QUESTION_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting Question");
    }

    @Test
    void createQuestionEntity__1() {
        //given //when
        Question result = tested.createQuestionEntity(QUESTION);

        //then
        verify(questionRepository).save(QUESTION);
        assertThat(result).isEqualTo(QUESTION);
    }

    @Test
    void createQuestionEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(questionRepository).save(QUESTION);

        assertThatThrownBy(() -> tested.createQuestionEntity(QUESTION))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new Exercise.");
    }

    @Test
    void updateQuestion__1() {
        //given
        Question questionToUpdate = new Question();
        questionToUpdate.setQuestionId(QUESTION_ID);
        questionToUpdate.setQuestionContent("Updated Question Content");
        questionToUpdate.setQuestionClientAnswer("Updated Client Answer");

        Question expectedQuestion = new Question();
        expectedQuestion.setQuestionId(QUESTION_ID);
        expectedQuestion.setQuestionContent("Updated Question Content");
        expectedQuestion.setQuestionClientAnswer("Updated Client Answer");

        //when
        when(questionRepository.findById(QUESTION_ID)).thenReturn(Optional.of(QUESTION));
        when(questionRepository.save(any(Question.class))).thenReturn(expectedQuestion);

        Question updatedQuestion = tested.updateQuestion(questionToUpdate);

        //then
        assertThat(updatedQuestion).isNotNull();
        assertThat(updatedQuestion.getQuestionContent()).isEqualTo("Updated Question Content");
        assertThat(updatedQuestion.getQuestionClientAnswer()).isEqualTo("Updated Client Answer");
        verify(questionRepository).save(argThat(q -> q.getQuestionContent().equals("Updated Question Content")
                && q.getQuestionClientAnswer().equals("Updated Client Answer")));
    }

    @Test
    void updateQuestion__2() {
        //given
        Question questionToUpdate = new Question();
        questionToUpdate.setQuestionId(QUESTION_ID);

        //when
        when(questionRepository.findById(QUESTION_ID)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> tested.updateQuestion(questionToUpdate))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Question entity with id " + QUESTION_ID + " not found, update failed");
    }

    private static Question generateQuestion() {
        Question question = new Question();
        question.setQuestionId(QUESTION_ID);
        question.setQuestionContent("Test Question Content");
        question.setQuestionClientAnswer("Test Client Answer");
        return question;
    }
}