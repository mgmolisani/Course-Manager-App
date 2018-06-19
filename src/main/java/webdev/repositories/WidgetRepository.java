package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Widget;

/**
 * Interface to represent a lesson repository.
 */
public interface WidgetRepository extends CrudRepository<Widget, Integer> {
}
