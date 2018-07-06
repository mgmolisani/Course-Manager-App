package webdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import webdev.models.Exam;
import webdev.models.TrueFalseQuestion;
import webdev.repositories.ExamRepository;
import webdev.repositories.TrueFalseQuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrueFalseQuestionService {
  @Autowired
  TrueFalseQuestionRepository questionRepository;
  @Autowired
  ExamRepository examRepository;

  @GetMapping("/api/truefalse/{questionId}")
  public TrueFalseQuestion findTrueFalseQuestionById(@PathVariable int questionId) throws QuestionNotFoundException {
    Optional<TrueFalseQuestion> data = questionRepository.findById(questionId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new QuestionNotFoundException();
  }

  @PostMapping("/api/exam/{examId}/truefalse")
  public TrueFalseQuestion createTrueFalseQuestion(@PathVariable int examId,
                                                   @RequestBody TrueFalseQuestion question) throws WidgetService.WidgetNotFoundException {
    Optional<Exam> data = examRepository.findById(examId);
    if (data.isPresent()) {
      Exam exam = data.get();
      question.setExam(exam);
      return questionRepository.save(question);
    }
    throw new WidgetService.WidgetNotFoundException();
  }

  @PutMapping("/api/truefalse")
  public TrueFalseQuestion updateTrueFalseQuestion(@RequestBody TrueFalseQuestion updatedQuestion) throws QuestionNotFoundException {
    TrueFalseQuestion question = this.findTrueFalseQuestionById(updatedQuestion.getId());
    question.setTitle(updatedQuestion.getTitle());
    question.setDescription(updatedQuestion.getDescription());
    question.setPoints(updatedQuestion.getPoints());
    question.setIsTrue(updatedQuestion.getIsTrue());
    return questionRepository.save(question);
  }

  @DeleteMapping("/api/truefalse/{questionId}")
  public void deleteTrueFalseQuestion(@PathVariable int questionId) throws QuestionNotFoundException {
    this.findTrueFalseQuestionById(questionId);
    questionRepository.deleteById(questionId);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class QuestionNotFoundException extends Exception {

    public QuestionNotFoundException() {
      super("Question could not be found.");
    }
  }
}