package webdev.models;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class MultipleChoiceQuestion
        extends Question {
  @ElementCollection
  @CollectionTable
  private List<String> choices;
  private int correctChoice;

  public MultipleChoiceQuestion() {
    super();
  }

  public List<String> getChoices() {
    return choices;
  }

  public void setChoices(List<String> choices) {
    this.choices = choices;
  }

  public int getCorrectChoice() {
    return correctChoice;
  }

  public void setCorrectChoice(int correctChoice) {
    this.correctChoice = correctChoice;
  }
}