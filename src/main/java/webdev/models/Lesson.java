package webdev.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

import javax.persistence.*;

/**
 * Represents a lesson.
 */
@Entity
public class Lesson {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
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
  private Module module;

  public Lesson() {
    super();
  }

  // Start of getters and setters

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

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  //End of getters and setters
}

