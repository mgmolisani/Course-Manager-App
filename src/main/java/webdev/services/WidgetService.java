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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

  /**
   * Gets all widgets
   * @return all widgets
   */
  @GetMapping("/api/widget")
  public Iterable<Widget> findAllWidgets() {
    return widgetRepository.findAll();
  }

  /**
   * Gets a widget by its ID
   * @param widgetId the widget ID
   * @return the widget
   * @throws WidgetNotFoundException if the widget isnt found
   */
  @GetMapping("/api/widget/{widgetId}")
  public Widget findWidgetById(@PathVariable int widgetId) throws WidgetNotFoundException {
    Optional<Widget> data = widgetRepository.findById(widgetId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new WidgetNotFoundException();
  }

  /**
   * Gets all of a lessons widgets
   * @param lessonId the lesson to find
   * @return the widgets of the lesson
   * @throws Exception if the lesson isnt found
   */
  @GetMapping("/api/lesson/{lessonId}/widget")
  public List<Widget> findAllWidgetsForLesson(@PathVariable int lessonId) throws LessonService.LessonNotFoundException {
    Optional<Lesson> data = lessonRepository.findById(lessonId);
    if (data.isPresent()) {
      Lesson lesson = data.get();
      return lesson.getWidgets();
    }
    throw new LessonService.LessonNotFoundException();
  }

  /**
   * Creates a new widget and returns it
   * @param lessonId the lesson to add to
   * @param widget the new widget
   * @return the widget reated
   * @throws LessonService.LessonNotFoundException if the lesson doesnt exist
   */
  @PostMapping("/api/lesson/{lessonId}/widget")
  public Widget createWidget(@PathVariable int lessonId,
                             @RequestBody Widget widget) throws LessonService.LessonNotFoundException {
    Optional<Lesson> data = lessonRepository.findById(lessonId);
    if (data.isPresent()) {
      Lesson lesson = data.get();
      widget.setLesson(lesson);
      return widgetRepository.save(widget);
    }
    throw new LessonService.LessonNotFoundException();
  }

  /**
   * Saves all of the widgets to the lesson
   * @param lessonId the lesson to save
   * @param widgets the widgets to save to the lesson
   * @throws LessonService.LessonNotFoundException if the lesson doesnt exist
   */
  @PostMapping("/api/lesson/{lessonId}/widget/save")
  public void saveAllWidgets(@PathVariable int lessonId, @RequestBody List<Widget> widgets) throws LessonService.LessonNotFoundException {
    widgetRepository.deleteAll();
    for (Widget widget : widgets) {
      Optional<Lesson> data = lessonRepository.findById(lessonId);
      if (data.isPresent()) {
        Lesson lesson = data.get();
        widget.setLesson(lesson);
        widgetRepository.save(widget);
      } else {
        throw new LessonService.LessonNotFoundException();
      }
    }
  }

  /**
   * Updates a widget
   * @param widgetId the widget to update
   * @param updatedWidget the widget info to update with
   * @return the updated widget
   * @throws WidgetNotFoundException if the widget doesnt exist
   */
  @PutMapping("/api/widget/{widgetId}")
  public Widget updateWidget(@PathVariable int widgetId, @RequestBody Widget updatedWidget) throws WidgetNotFoundException {
    Widget widget = this.findWidgetById((widgetId));
    widget.setName(updatedWidget.getName());
    widget.setText(updatedWidget.getText());
    widget.setPosition(updatedWidget.getPosition());
    widget.setWidgetType(updatedWidget.getWidgetType());
    widget.setSize(updatedWidget.getSize());
    widget.setStyle(updatedWidget.getStyle());
    widget.setClassName(updatedWidget.getClassName());
    widget.setLink(updatedWidget.getLink());
    widget.setImage(updatedWidget.getImage());
    widget.setWidth(updatedWidget.getWidth());
    widget.setHeight(updatedWidget.getHeight());
    widget.setHeight(updatedWidget.getHeight());
    return widgetRepository.save(widget);
  }

  /**
   * Deletes a widget
   * @param widgetId the id to delete
   * @throws WidgetNotFoundException if the widget doesnt exist
   */
  @DeleteMapping("/api/widget/{widgetId}")
  public void deleteWidget(@PathVariable int widgetId) throws WidgetNotFoundException {
    this.findWidgetById(widgetId);
    widgetRepository.deleteById(widgetId);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class WidgetNotFoundException extends Exception {

    public WidgetNotFoundException() {
      super("Widget could not be found.");
    }
  }
}
