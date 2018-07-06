package webdev.models;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class FillBlankQuestion
        extends Question {
  @ElementCollection
  @CollectionTable
  private Map<String, String> answer;

  public FillBlankQuestion() {
    super();
  }

  public Map<String, String> getAnswer() {
    return answer;
  }

  public void setAnswer(Map<String, String> answer) {
    this.answer = answer;
  }
}