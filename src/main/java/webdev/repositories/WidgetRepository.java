package webdev.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.models.Widget;

/**
 * Interface to represent a widget repository.
 */
public interface WidgetRepository extends CrudRepository<Widget, Integer> {
  @Modifying
  @Query("DELETE FROM Widget w WHERE w.id=:id")
  void deleteById(
          @Param("id") int id);
}
