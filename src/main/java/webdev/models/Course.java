package webdev.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Represents a course.
 */
@Entity
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private String author;
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date modified;
  @OneToMany(mappedBy = "course", orphanRemoval = true)
  private List<Module> modules;

  public Course() {
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

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
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

  public List<webdev.models.Module> getModules() {
    return modules;
  }

  public void setModules(List<Module> modules) {
    this.modules = modules;
  }

  //End of setters and getters
}
