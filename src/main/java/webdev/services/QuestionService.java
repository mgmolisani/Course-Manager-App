package webdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import webdev.models.Question;
import webdev.repositories.QuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionService {
  @Autowired
  QuestionRepository questionRepository;

  @GetMapping("/api/question/{questionId}")
  public int findQuestionTypeById(@PathVariable int questionId) throws QuestionNotFoundException {
    Optional<Question> data = questionRepository.findById(questionId);
    if (data.isPresent()) {
      return data.get().getQuestionType();
    }
    throw new QuestionNotFoundException();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class QuestionNotFoundException extends Exception {

    public QuestionNotFoundException() {
      super("Question could not be found.");
    }
  }
}