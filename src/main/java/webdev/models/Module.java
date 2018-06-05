package webdev.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Represents a module.
 */
@Entity
public class Module {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date modified;
  @ManyToOne
  @JsonIgnore
  private Course course;
  @OneToMany(mappedBy = "module", orphanRemoval = true)
  private List<Lesson> lessons;

  public Module() {
    super();
  }

  //Start of setters and getters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }

  //End of setters and getters
}

