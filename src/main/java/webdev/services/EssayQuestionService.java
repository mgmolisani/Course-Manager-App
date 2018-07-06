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

import java.util.List;
import java.util.Optional;

import webdev.models.Course;
import webdev.models.EssayQuestion;
import webdev.models.Exam;
import webdev.models.Module;
import webdev.models.Question;
import webdev.repositories.CourseRepository;
import webdev.repositories.EssayQuestionRepository;
import webdev.repositories.ExamRepository;
import webdev.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class EssayQuestionService {
  @Autowired
  EssayQuestionRepository questionRepository;
  @Autowired
  ExamRepository examRepository;

  @GetMapping("/api/essay/{questionId}")
  public EssayQuestion findEssayQuestionById(@PathVariable int questionId) throws QuestionNotFoundException {
    Optional<EssayQuestion> data = questionRepository.findById(questionId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new QuestionNotFoundException();
  }

  @PostMapping("/api/exam/{examId}/essay")
  public Question createEssayQuestion(@PathVariable int examId,
                             @RequestBody EssayQuestion question) throws WidgetService.WidgetNotFoundException {
    Optional<Exam> data = examRepository.findById(examId);
    if (data.isPresent()) {
      Exam exam = data.get();
      question.setExam(exam);
      return questionRepository.save(question);
    }
    throw new WidgetService.WidgetNotFoundException();
  }

  @PutMapping("/api/essay")
  public EssayQuestion updateEssayQuestion(@RequestBody EssayQuestion updatedQuestion) throws QuestionNotFoundException {
    EssayQuestion question = this.findEssayQuestionById(updatedQuestion.getId());
    question.setTitle(updatedQuestion.getTitle());
    question.setDescription(updatedQuestion.getDescription());
    question.setPoints(updatedQuestion.getPoints());
    return questionRepository.save(question);
  }

  @DeleteMapping("/api/essay/{questionId}")
  public void deleteEssayQuestion(@PathVariable int questionId) throws QuestionNotFoundException {
    this.findEssayQuestionById(questionId);
    questionRepository.deleteById(questionId);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class QuestionNotFoundException extends Exception {

    public QuestionNotFoundException() {
      super("Question could not be found.");
    }
  }
}