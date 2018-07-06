package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.FillBlankQuestion;

public interface FillBlankQuestionRepository
        extends CrudRepository<FillBlankQuestion, Integer> {
}
