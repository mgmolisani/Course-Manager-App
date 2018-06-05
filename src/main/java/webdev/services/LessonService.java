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
import webdev.models.Lesson;
import webdev.models.Module;
import webdev.repositories.CourseRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.ModuleRepository;

/**
 * Controller for lesson service requests.
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonService {
  @Autowired
  LessonRepository lessonRepository;
  @Autowired
  ModuleRepository moduleRepository;
  @Autowired
  CourseRepository courseRepository;

  /**
   * Returns all lessons
   * @return all lessons
   */
  @GetMapping("/api/lesson")
  public Iterable<Lesson> findAllLessons() {
    return lessonRepository.findAll();
  }

  /**
   * Find and returns a lesson by ID
   * @param lessonId the lesson to find
   * @return the found lesson
   * @throws LessonNotFoundException if no lesson is found
   */
  @GetMapping("/api/lesson/{lessonId}")
  public Lesson findLessonById(@PathVariable int lessonId) throws LessonNotFoundException {
    Optional<Lesson> data = lessonRepository.findById(lessonId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new LessonNotFoundException();
  }

  /**
   * Returns all lessons for a given module
   * @param moduleId the module to find lessons for
   * @return all lessons for a given module
   * @throws ModuleService.ModuleNotFoundException if the module is not found
   */
  @GetMapping("/api/course/{courseId}/module/{moduleId}/lesson")
  public List<Lesson> findAllLessonsForModule(@PathVariable int moduleId) throws ModuleService.ModuleNotFoundException {
    Optional<Module> data = moduleRepository.findById(moduleId);
    if (data.isPresent()) {
      Module module = data.get();
      return module.getLessons();
    }
    throw new ModuleService.ModuleNotFoundException();
  }

  /**
   * Creates a new lesson and returns it
   * @param moduleId the module to add the lesson to
   * @param lesson the lesson to create
   * @return the new lesson
   * @throws ModuleService.ModuleNotFoundException if the module is not found
   */
  @PostMapping("/api/course/{courseId}/module/{moduleId}/lesson")
  public Lesson createLesson(@PathVariable int moduleId,
                             @RequestBody Lesson lesson) throws ModuleService.ModuleNotFoundException {
    Optional<Module> data = moduleRepository.findById(moduleId);
    if (data.isPresent()) {
      Module module = data.get();
      lesson.setModule(module);
      return lessonRepository.save(lesson);
    }
    throw new ModuleService.ModuleNotFoundException();
  }

  /**
   * Updates a lesson
   * @param updatedLesson the updated lesson
   * @return the updated lesson
   * @throws LessonNotFoundException if the lesson doesn't exist
   */
  @PutMapping("/api/lesson")
  public Lesson updateLesson(@RequestBody Lesson updatedLesson) throws LessonNotFoundException {
    Lesson lesson = this.findLessonById(updatedLesson.getId());
    lesson.setTitle(updatedLesson.getTitle());
    return lessonRepository.save(lesson);
  }

  /**
   * Deletes a lesson by ID
   * @param lessonId the lesson to delete
   * @throws LessonNotFoundException if the lesson isn't found
   */
  @DeleteMapping("/api/lesson/{lessonId}")
  public void deleteLesson(@PathVariable int lessonId) throws LessonNotFoundException {
    this.findLessonById(lessonId);
    lessonRepository.deleteById(lessonId);
  }

  /**
   * A custom error for lesson not found
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public class LessonNotFoundException extends Exception {

    public LessonNotFoundException() {
      super("Lesson could not be found.");
    }
  }
}