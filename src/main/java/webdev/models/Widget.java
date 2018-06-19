package webdev.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

/**
 * This class represents a single table inheritance of all widget types.
 * We were asked to do inheritance with extension but the lectures did not go over that and instead just made
 * one single large class (which is similar to how the table would have been made.
 * This class also makes sub tables to handle the array of class names and the style object map.
 */
@Entity
public class Widget {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  @Column(columnDefinition = "text")
  private String text;
  private int position;
  private String widgetType;
  private int size;
  @ElementCollection
  @CollectionTable
  private List<String> className;
  @ElementCollection
  @CollectionTable
  private Map<String, String> style;
  @Column(columnDefinition = "text")
  private String link;
  @Column(columnDefinition = "text")
  private String image;
  private String width;
  private String height;
  @Enumerated(EnumType.STRING)
  private ListType listType;
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date modified;
  @ManyToOne
  @JsonIgnore
  private Lesson lesson;

  public Widget() {
    super();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public List<String> getClassName() {
    return className;
  }

  public void setClassName(List<String> className) {
    this.className = className;
  }

  public Map<String, String> getStyle() {
    return style;
  }

  public void setStyle(Map<String, String> style) {
    this.style = style;
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

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  public String getWidgetType() {
    return widgetType;
  }

  public void setWidgetType(String widgetType) {
    this.widgetType = widgetType;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public ListType getListType() {
    return listType;
  }

  public void setListType(ListType listType) {
    this.listType = listType;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
