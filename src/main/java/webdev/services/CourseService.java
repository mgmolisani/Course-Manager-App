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

import java.util.Date;
import java.util.Optional;

import webdev.models.Course;
import webdev.repositories.CourseRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseService {
  @Autowired
  CourseRepository courseRepository;

  @GetMapping("/api/course")
  public Iterable<Course> findAllCourses() {
    return courseRepository.findAll();
  }

  @GetMapping("/api/course/{courseId}")
  public Course findCourseById(@PathVariable int courseId) {
    Optional<Course> data = courseRepository.findById(courseId);
    if (data.isPresent()) {
      return data.get();
    }
    return null;
  }

  @PostMapping("/api/course")
  public Course createCourse(@RequestBody Course course) {
    Date currentTime = new Date();
    course.setCreated(currentTime);
    course.setModified(currentTime);
    return courseRepository.save(course);
  }

  @PutMapping("/api/course")
  public Course updateCourse(@RequestBody Course updatedCourse) {
    Course course = this.findCourseById(updatedCourse.getId());
    course.setTitle(updatedCourse.getTitle());
    course.setModified(new Date());
    return courseRepository.save(course);
  }

  @DeleteMapping("/api/course/{courseId}")
  public void deleteCourse(@PathVariable("courseId") int id) {
    courseRepository.deleteById(id);
  }
}