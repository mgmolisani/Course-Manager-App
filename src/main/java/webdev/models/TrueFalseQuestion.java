package webdev.models;

import javax.persistence.Entity;

/**
 * Represents a true false question
 */
@Entity
public class TrueFalseQuestion
        extends Question {
  private boolean isTrue;

  public TrueFalseQuestion() {
    super();
  }

  public boolean getIsTrue() {
    return isTrue;
  }

  public void setIsTrue(boolean isTrue) {
    this.isTrue = isTrue;
  }
}