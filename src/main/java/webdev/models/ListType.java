package webdev.models;

public enum ListType {
  Ordered,
  Unordered;

  public String toString(){
    switch(this){
      case Ordered:
        return "Ordered";
      case Unordered:
        return "Unordered";
    }
    return null;
  }
}
