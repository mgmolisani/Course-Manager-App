package webdev.models;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

/**
 * Representa fill in the blank question
 */
@Entity
public class FillBlankQuestion
        extends Question {
  @ElementCollection
  @CollectionTable
  private Map<String, String> answers;

  public FillBlankQuestion() {
    super();
  }

  public Map<String, String> getAnswers() {
    return answers;
  }

  public void setAnswers(Map<String, String> answers) {
    this.answers = answers;
  }
}