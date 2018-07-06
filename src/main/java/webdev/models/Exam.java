package webdev.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Represents an Exam (single table).
 */
@Entity
public class Exam extends Widget {
  private String title;
  @Column(columnDefinition = "text")
  private String description;
  private double points;
  @OneToMany(mappedBy="exam", orphanRemoval = true)
  private List<Question> questions;

  public Exam() {
    super();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPoints() {
    return points;
  }

  public void setPoints(double points) {
    this.points = points;
  }

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }
}
