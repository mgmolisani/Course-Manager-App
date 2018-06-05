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

import javax.servlet.http.HttpSession;

import webdev.models.Course;
import webdev.models.User;
import webdev.repositories.CourseRepository;
import webdev.repositories.UserRepository;

/**
 * A controller for Course Service requests
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseService {
  @Autowired
  CourseRepository courseRepository;
  @Autowired
  UserRepository userRepository;

  /**
   * Returns all courses
   * @return all courses
   */
  @GetMapping("/api/course")
  public Iterable<Course> findAllCourses() {
    return courseRepository.findAll();
  }

  /**
   * Finds and returns a course by ID
   * @param courseId the course to find
   * @return the found course
   * @throws CourseNotFoundException if the course is not found
   */
  @GetMapping("/api/course/{courseId}")
  public Course findCourseById(@PathVariable int courseId) throws CourseNotFoundException {
    Optional<Course> data = courseRepository.findById(courseId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new CourseNotFoundException();
  }

  /**
   * Creates a new course and returns it
   * @param course the course to create
   * @param session the session including the author info
   * @return the new course
   */
  @PostMapping("/api/course")
  public Course createCourse(@RequestBody Course course, HttpSession session) {
    //User user = (User) session.getAttribute("user");
    //user = userRepository.findUserByUsername(user.getUsername()).iterator().next();
    //Had the above working in postman without the next three lines but sadly the cross origin threw many errors
    User user = new User();
    user.setFirstName("Place");
    user.setLastName("Holder");
    course.setAuthor(user.getFirstName() + " " + user.getLastName());
    return courseRepository.save(course);
  }

  /**
   * Updates a course
   * @param updatedCourse the updated course
   * @return the updated course
   * @throws CourseNotFoundException if the course doesn't exist
   */
  @PutMapping("/api/course")
  public Course updateCourse(@RequestBody Course updatedCourse) throws CourseNotFoundException {
    Course course = this.findCourseById(updatedCourse.getId());
    course.setTitle(updatedCourse.getTitle());
    return courseRepository.save(course);
  }

  /**
   * Deletes a course by ID
   * @param courseId the course ID to delete
   * @throws CourseNotFoundException if the course doesn't exist
   */
  @DeleteMapping("/api/course/{courseId}")
  public void deleteCourse(@PathVariable int courseId) throws CourseNotFoundException {
      this.findCourseById(courseId);
      courseRepository.deleteById(courseId);
  }

  /**
   * A custom exception for course not found.
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class CourseNotFoundException extends Exception {

    public CourseNotFoundException() {
      super("Course could not be found.");
    }
  }
}