package webdev.repositories;

import org.springframework.data.repository.CrudRepository;
import webdev.models.Module;

/**
 * Interface to represent a module repository.
 */
public interface ModuleRepository extends CrudRepository<Module, Integer> {

}
