package webdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import webdev.models.Lesson;
import webdev.models.Widget;
import webdev.repositories.LessonRepository;
import webdev.repositories.WidgetRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WidgetService {
  @Autowired
  WidgetRepository widgetRepository;
  @Autowired
  LessonRepository lessonRepository;

  @PostMapping("/api/lesson/{lessonId}/widget")
  public Widget createWidget(@PathVariable int lessonId,
                             @RequestBody Widget widget) throws Exception {
    Optional<Lesson> data = lessonRepository.findById(lessonId);
    if (data.isPresent()) {
      Lesson lesson = data.get();
      widget.setLesson(lesson);
      return widgetRepository.save(widget);
    }
    throw new Exception("new exception");
  }

  @GetMapping("/api/lesson/{lessonId}/widget")
  public List<Widget> findAllWidgetsForLesson(@PathVariable int lessonId) throws Exception {
    Optional<Lesson> data = lessonRepository.findById(lessonId);
    if (data.isPresent()) {
      Lesson lesson = data.get();
      return lesson.getWidgets();
    }
    throw new Exception("hi");
  }

  @PostMapping("/api/lesson/{lessonId}/widget/save")
  public void saveAllWidgets(@PathVariable int lessonId, @RequestBody List<Widget> widgets) {
    widgetRepository.deleteAll();
    for(Widget widget: widgets) {
      Optional<Lesson> data = lessonRepository.findById(lessonId);
      if (data.isPresent()) {
        Lesson lesson = data.get();
        widget.setLesson(lesson);
      }
      widgetRepository.save(widget);
    }
  }
}
