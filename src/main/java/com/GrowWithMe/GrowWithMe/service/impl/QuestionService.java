package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.Question;
import com.GrowWithMe.GrowWithMe.repository.IQuestionRepository;
import com.GrowWithMe.GrowWithMe.service.IQuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    private IQuestionRepository questionRepository;

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Integer questionId) {
        return questionRepository.findById(questionId);
    }

    @Override
    public void deleteQuestionEntity(Integer questionId) {
        try {
            questionRepository.deleteById(questionId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Question entity with id " + questionId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Question", e);
        }
    }

    @Override
    public Question createQuestionEntity(Question question) {
        try {
            questionRepository.save(question);
            return question;
        } catch (Exception e) {
            throw new RuntimeException("Error in creating new Exercise.", e);
        }
    }

    @Override
    public Question updateQuestion(Question questionToUpdate) {
        Optional<Question> questionOptional = questionRepository.findById(questionToUpdate.getQuestionId());
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();

            if (questionToUpdate.getQuestionContent() != null && !questionToUpdate.getQuestionContent().equals(question.getQuestionContent()))
                question.setQuestionContent(questionToUpdate.getQuestionContent());
            if (questionToUpdate.getQuestionClientAnswer() != null && !questionToUpdate.getQuestionClientAnswer().equals(question.getQuestionClientAnswer()))
                question.setQuestionClientAnswer(questionToUpdate.getQuestionClientAnswer());

            return questionRepository.save(question);
        } else {
            throw new EntityNotFoundException("Question entity with id " + questionToUpdate.getQuestionId() + " not found, update failed");
        }
    }

}
