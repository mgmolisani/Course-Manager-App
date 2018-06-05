package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Course;

/**
 * Interface to represent a course repository.
 */
public interface CourseRepository extends CrudRepository<Course, Integer> {
}

