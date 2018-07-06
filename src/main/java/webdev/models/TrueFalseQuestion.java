package webdev.models;

import javax.persistence.Entity;

@Entity
public class TrueFalseQuestion
        extends Question {
  private boolean isTrue;

  public TrueFalseQuestion() {
    super();
  }

  public boolean isTrue() {
    return isTrue;
  }

  public void setTrue(boolean aTrue) {
    isTrue = aTrue;
  }
}