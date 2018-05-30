package webdev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.models.Course;

/**
 * Interface to represent a user repository.
 */
public interface CourseRepository extends CrudRepository<Course, Integer> {
/*  @Query("SELECT u FROM User u WHERE u.username=:username")
  Iterable<User> findUserByUsername(
          @Param("username") String username);

  @Query("SELECT u FROM User u WHERE u.username=:username AND u.password=:password")
  Iterable<User> findUserByCredentials(
          @Param("username") String username,
          @Param("password") String password);*/
}

