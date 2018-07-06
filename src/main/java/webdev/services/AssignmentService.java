package webdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import webdev.models.Assignment;
import webdev.models.Lesson;
import webdev.models.Widget;
import webdev.repositories.AssignmentRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.WidgetRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssignmentService {
  @Autowired
  AssignmentRepository assignmentRepository;
  @Autowired
  LessonRepository lessonRepository;

  @GetMapping("/api/assignment")
  public Iterable<Assignment> findAllAssignments() {
    return assignmentRepository.findAll();
  }

  @GetMapping("/api/assignment/{assignmentId}")
  public Assignment findAssignmentById(@PathVariable int assignmentId) throws WidgetService.WidgetNotFoundException {
    Optional<Assignment> data = assignmentRepository.findById(assignmentId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new WidgetService.WidgetNotFoundException();
  }

  @GetMapping("/api/lesson/{lessonId}/assignment")
  public List<Assignment> findAllAssignmentsForLesson(@PathVariable int lessonId) throws LessonService.LessonNotFoundException {
    Optional<Lesson> data = lessonRepository.findById(lessonId);
    if (data.isPresent()) {
      Lesson lesson = data.get();
      List<Widget> widgets = lesson.getWidgets();
      List<Assignment> assignments = new ArrayList<>();
      for (Widget widget : widgets) {
        String widgetType = widget.getWidgetType();
        if (widgetType != null && widgetType.equals("assignment")) {
          assignments.add((Assignment) widget);
        }
      }
      return assignments;
    }
    throw new LessonService.LessonNotFoundException();
  }

  @PostMapping("/api/lesson/{lessonId}/assignment")
  public Assignment createAssignment(@PathVariable int lessonId,
                             @RequestBody Assignment assignment) throws LessonService.LessonNotFoundException {
    Optional<Lesson> data = lessonRepository.findById(lessonId);
    if (data.isPresent()) {
      Lesson lesson = data.get();
      assignment.setLesson(lesson);
      return assignmentRepository.save(assignment);
    }
    throw new LessonService.LessonNotFoundException();
  }

  @PutMapping("/api/assignment/{assignmentId}")
  public Assignment updateAssignment(@PathVariable int assignmentId, @RequestBody Assignment updatedAssignment) throws WidgetService.WidgetNotFoundException {
    Assignment assignment = this.findAssignmentById(assignmentId);
    assignment.setTitle(updatedAssignment.getTitle());
    assignment.setDescription(updatedAssignment.getDescription());
    assignment.setPoints(updatedAssignment.getPoints());
    return assignmentRepository.save(assignment);
  }

  @DeleteMapping("/api/assignment/{assignmentId}")
  public void deleteAssignment(@PathVariable int assignmentId) throws WidgetService.WidgetNotFoundException {
    this.findAssignmentById(assignmentId);
    assignmentRepository.deleteById(assignmentId);
  }
}
