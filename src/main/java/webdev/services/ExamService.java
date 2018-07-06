package webdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import webdev.models.Exam;
import webdev.models.Lesson;
import webdev.models.Widget;
import webdev.repositories.ExamRepository;
import webdev.repositories.LessonRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExamService {
  @Autowired
  ExamRepository examRepository;
  @Autowired
  LessonRepository lessonRepository;

  @GetMapping("/api/exam")
  public Iterable<Exam> findAllExams() {
    return examRepository.findAll();
  }

  @GetMapping("/api/exam/{examId}")
  public Exam findExamById(@PathVariable int examId) throws WidgetService.WidgetNotFoundException {
    Optional<Exam> data = examRepository.findById(examId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new WidgetService.WidgetNotFoundException();
  }

  @GetMapping("/api/lesson/{lessonId}/exam")
  public List<Exam> findAllExamsForLesson(@PathVariable int lessonId) throws LessonService.LessonNotFoundException {
    Optional<Lesson> data = lessonRepository.findById(lessonId);
    if (data.isPresent()) {
      Lesson lesson = data.get();
      List<Widget> widgets = lesson.getWidgets();
      List<Exam> exams = new ArrayList<>();
      for (Widget widget : widgets) {
        String widgetType = widget.getWidgetType();
        if (widgetType != null && widgetType.equals("exam")) {
          exams.add((Exam) widget);
        }
      }
      return exams;
    }
    throw new LessonService.LessonNotFoundException();
  }

  @PostMapping("/api/lesson/{lessonId}/exam")
  public Exam createExam(@PathVariable int lessonId,
                         @RequestBody Exam exam) throws LessonService.LessonNotFoundException {
    Optional<Lesson> data = lessonRepository.findById(lessonId);
    if (data.isPresent()) {
      Lesson lesson = data.get();
      exam.setLesson(lesson);
      return examRepository.save(exam);
    }
    throw new LessonService.LessonNotFoundException();
  }

  @PutMapping("/api/exam/{examId}")
  public Exam updateExam(@PathVariable int examId, @RequestBody Exam updatedExam) throws WidgetService.WidgetNotFoundException {
    Exam exam = this.findExamById(examId);
    exam.setTitle(updatedExam.getTitle());
    exam.setDescription(updatedExam.getDescription());
    exam.setPoints(updatedExam.getPoints());
    return examRepository.save(exam);
  }

  @DeleteMapping("/api/exam/{examId}")
  public void deleteExam(@PathVariable int examId) throws WidgetService.WidgetNotFoundException {
    this.findExamById(examId);
    examRepository.deleteById(examId);
  }
}
