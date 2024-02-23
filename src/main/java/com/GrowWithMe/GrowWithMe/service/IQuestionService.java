package com.GrowWithMe.GrowWithMe.service;
import com.GrowWithMe.GrowWithMe.model.Question;
import java.util.List;
import java.util.Optional;

public interface IQuestionService {
    List<Question> getAllQuestion();
    Optional<Question> getQuestionById(Integer questionId);
    void deleteQuestionEntity(Integer questionId);
    Question createQuestionEntity(Question question);
    Question updateQuestion(Question questionToUpdate);
}
