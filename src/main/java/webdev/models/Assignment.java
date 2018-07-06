package webdev.models;

import javax.persistence.Entity;

@Entity
public class Assignment extends Widget {
  private String title;
  private String description;
  private double points;

  public Assignment() {
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
}
