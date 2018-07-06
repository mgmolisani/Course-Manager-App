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
import webdev.models.FillBlankQuestion;
import webdev.repositories.ExamRepository;
import webdev.repositories.FillBlankQuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FillBlankQuestionService {
  @Autowired
  FillBlankQuestionRepository questionRepository;
  @Autowired
  ExamRepository examRepository;

  @GetMapping("/api/blank/{questionId}")
  public FillBlankQuestion findFillBlankQuestionById(@PathVariable int questionId) throws QuestionNotFoundException {
    Optional<FillBlankQuestion> data = questionRepository.findById(questionId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new QuestionNotFoundException();
  }

  @PostMapping("/api/exam/{examId}/blank")
  public FillBlankQuestion createFillBlankQuestion(@PathVariable int examId,
                                                   @RequestBody FillBlankQuestion question) throws WidgetService.WidgetNotFoundException {
    Optional<Exam> data = examRepository.findById(examId);
    if (data.isPresent()) {
      Exam exam = data.get();
      question.setExam(exam);
      return questionRepository.save(question);
    }
    throw new WidgetService.WidgetNotFoundException();
  }

  @PutMapping("/api/blank")
  public FillBlankQuestion updateFillBlankQuestion(@RequestBody FillBlankQuestion updatedQuestion) throws QuestionNotFoundException {
    FillBlankQuestion question = this.findFillBlankQuestionById(updatedQuestion.getId());
    question.setTitle(updatedQuestion.getTitle());
    question.setDescription(updatedQuestion.getDescription());
    question.setPoints(updatedQuestion.getPoints());
    question.setAnswers(updatedQuestion.getAnswers());
    return questionRepository.save(question);
  }

  @DeleteMapping("/api/blank/{questionId}")
  public void deleteFillBlankQuestion(@PathVariable int questionId) throws QuestionNotFoundException {
    this.findFillBlankQuestionById(questionId);
    questionRepository.deleteById(questionId);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class QuestionNotFoundException extends Exception {

    public QuestionNotFoundException() {
      super("Question could not be found.");
    }
  }
}