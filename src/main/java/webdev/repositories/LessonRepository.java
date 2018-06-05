package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Lesson;
/**
 * Interface to represent a lesson repository.
 */
public interface LessonRepository extends CrudRepository<Lesson, Integer> {
}
