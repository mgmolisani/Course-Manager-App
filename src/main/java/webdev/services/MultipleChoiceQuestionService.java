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
import webdev.models.MultipleChoiceQuestion;
import webdev.repositories.ExamRepository;
import webdev.repositories.MultipleChoiceQuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MultipleChoiceQuestionService {
  @Autowired
  MultipleChoiceQuestionRepository questionRepository;
  @Autowired
  ExamRepository examRepository;

  @GetMapping("/api/choice/{questionId}")
  public MultipleChoiceQuestion findMultipleChoiceQuestionById(@PathVariable int questionId) throws QuestionNotFoundException {
    Optional<MultipleChoiceQuestion> data = questionRepository.findById(questionId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new QuestionNotFoundException();
  }

  @PostMapping("/api/exam/{examId}/choice")
  public MultipleChoiceQuestion createMultipleChoiceQuestion(@PathVariable int examId,
                                                             @RequestBody MultipleChoiceQuestion question) throws WidgetService.WidgetNotFoundException {
    Optional<Exam> data = examRepository.findById(examId);
    if (data.isPresent()) {
      Exam exam = data.get();
      question.setExam(exam);
      return questionRepository.save(question);
    }
    throw new WidgetService.WidgetNotFoundException();
  }

  @PutMapping("/api/choice")
  public MultipleChoiceQuestion updateMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestion updatedQuestion) throws QuestionNotFoundException {
    MultipleChoiceQuestion question = this.findMultipleChoiceQuestionById(updatedQuestion.getId());
    question.setTitle(updatedQuestion.getTitle());
    question.setDescription(updatedQuestion.getDescription());
    question.setPoints(updatedQuestion.getPoints());
    question.setChoices(updatedQuestion.getChoices());
    question.setCorrectChoice(updatedQuestion.getCorrectChoice());
    return questionRepository.save(question);
  }

  @DeleteMapping("/api/choice/{questionId}")
  public void deleteMultipleChoiceQuestion(@PathVariable int questionId) throws QuestionNotFoundException {
    this.findMultipleChoiceQuestionById(questionId);
    questionRepository.deleteById(questionId);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class QuestionNotFoundException extends Exception {

    public QuestionNotFoundException() {
      super("Question could not be found.");
    }
  }
}