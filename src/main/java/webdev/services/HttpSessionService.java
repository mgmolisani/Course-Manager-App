package webdev.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * A controller for session management
 */
@RestController
public class HttpSessionService {

  /**
   * Sets a session attribute
   * @param attr the attribute to set
   * @param value the value to set the attribute to
   * @param session the session
   * @return a string representation of the attribute and its new value
   */
  @GetMapping("/api/session/set/{attr}/{value}")
  public String setSessionAttribute(
          @PathVariable String attr,
          @PathVariable String value,
          HttpSession session) {
    session.setAttribute(attr, value);
    return attr + " = " + value + session;
  }

  /**
   * gets an session attribute's value
   * @param attr the attribute to get the value of
   * @param session the session
   * @return the value of the attribute
   */
  @GetMapping("/api/session/get/{attr}")
  public Object getSessionAttribute(
          @PathVariable String attr,
          HttpSession session) {
    return session.getAttribute(attr);
  }

  /**
   * Invalidates teh session (for logout)
   * @param session the session
   * @return confirmation the session was invalidated
   */
  @GetMapping("/api/session/invalidate")
  public String invalidateSession(
          HttpSession session) {
    session.invalidate();
    return "Session invalidated";
  }
}
