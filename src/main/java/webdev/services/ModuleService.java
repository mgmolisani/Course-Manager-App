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
import webdev.models.Module;
import webdev.repositories.CourseRepository;
import webdev.repositories.ModuleRepository;

/**
 * Controller for Modules Services
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleService {
  @Autowired
  ModuleRepository moduleRepository;
  @Autowired
  CourseRepository courseRepository;

  /**
   * Returns all modules
   * @return all modules
   */
  @GetMapping("/api/module")
  public Iterable<Module> findAllModules() {
    return moduleRepository.findAll();
  }

  /**
   * Returns a module with the given ID
   * @param moduleId the module ID to find
   * @return a module with the given ID
   * @throws ModuleNotFoundException if the module isn't found
   */
  @GetMapping("/api/module/{moduleId}")
  public Module findModuleById(@PathVariable int moduleId) throws ModuleNotFoundException {
    Optional<Module> data = moduleRepository.findById(moduleId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new ModuleNotFoundException();
  }

  /**
   * Returns all modules for a course
   * @param courseId the course to find modules for
   * @return all modules fo a course
   * @throws CourseService.CourseNotFoundException if no courses are found
   */
  @GetMapping("/api/course/{courseId}/module")
  public List<Module> findAllModulesForCourse(@PathVariable int courseId) throws CourseService.CourseNotFoundException {
    Optional<Course> data = courseRepository.findById(courseId);
    if (data.isPresent()) {
      Course course = data.get();
      return course.getModules();
    }
    throw new CourseService.CourseNotFoundException();
  }

  /**
   * Creates and returns a module
   * @param courseId the course to add the module to
   * @param module the module to create
   * @return the created module
   * @throws CourseService.CourseNotFoundException if no course is found
   */
  @PostMapping("/api/course/{courseId}/module")
  public Module createModule(@PathVariable int courseId,
                             @RequestBody Module module) throws CourseService.CourseNotFoundException {
    Optional<Course> data = courseRepository.findById(courseId);
    if (data.isPresent()) {
      Course course = data.get();
      module.setCourse(course);
      return moduleRepository.save(module);
    }
    throw new CourseService.CourseNotFoundException();
  }

  /**
   * Updates a module
   * @param updatedModule the updated module
   * @return the updated module
   * @throws ModuleNotFoundException if the module didn't previously exist
   */
  @PutMapping("/api/module")
  public Module updateModule(@RequestBody Module updatedModule) throws ModuleNotFoundException {
    Module module = this.findModuleById(updatedModule.getId());
    module.setTitle(updatedModule.getTitle());
    return moduleRepository.save(module);
  }

  /**
   * Deletes a module by ID
   * @param moduleId the module to delete
   * @throws ModuleNotFoundException if the module doesn't exist
   */
  @DeleteMapping("/api/module/{moduleId}")
  public void deleteModule(@PathVariable int moduleId) throws ModuleNotFoundException {
    this.findModuleById(moduleId);
    moduleRepository.deleteById(moduleId);
  }

  /**
   * A custom exception for module not found
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class ModuleNotFoundException extends Exception {

    public ModuleNotFoundException() {
      super("Module could not be found.");
    }
  }
}