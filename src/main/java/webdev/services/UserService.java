package webdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import webdev.models.User;
import webdev.repositories.UserRepository;

/**
 * A REST controller for user services.
 */
@RestController
public class UserService {
  @Autowired
  private UserRepository repository;
  @Autowired
  private JavaMailSender javaMailSender;
  //Unused as we are not using sessions
  private List<User> users = new ArrayList<>();

  /**
   * Creates a new user and returns that user.
   *
   * @param user the user to create
   * @return the created user
   */
  @PostMapping("/api/user")
  public User createUser(@RequestBody User user) {
    return repository.save(user);
  }

  /**
   * Returns a list of all the users registered.
   *
   * @return a list of all the users registered
   */
  @GetMapping("/api/user")
  public List<User> findAllUsers() {
    return (List<User>) repository.findAll();
  }

  /**
   * Returns a user from a given ID.
   *
   * @param userId the ID to search for
   * @return the user with the given ID
   * @throws Exception if no user is found with the given ID
   */
  @GetMapping("/api/user/{userId}")
  public User findUserById(@PathVariable("userId") int userId) throws Exception {
    Optional<User> data = repository.findById(userId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new Exception("No user found with id = " + userId);
  }

  /**
   * Returns a user from a given username.
   *
   * @param username the username to search for
   * @return the user with the given username
   * @throws Exception if no user is found with the given username
   */
  @GetMapping(value = "/api/user", params = "username")
  public User findUserByUsername(@RequestParam String username) throws Exception {
    Iterator<User> matchingUsers = repository.findUserByUsername(username).iterator();
    //Selects the first user in the returned list if any matching users exist.
    if (matchingUsers.hasNext()) {
      return matchingUsers.next();
    }
    throw new Exception("User does not exist.");
  }

  /**
   * Returns a user from a given username and password
   * @param user the user containing the username and password
   * @param session not currently used
   * @return the user with the given credentials
   * @throws Exception if no user is found with the given credentials
   */
  @PostMapping("/api/user/by-credentials")
  public User findUserByCredentials(@RequestBody User user, HttpSession session) throws Exception {
    Iterator<User> matchingUsers = repository.findUserByCredentials(user.getUsername(), user.getPassword()).iterator();
    //Selects the first user in the returned list if any matching users exist.
    if (matchingUsers.hasNext()) {
      return matchingUsers.next();
    }
    throw new Exception("User does not exist.");
  }

  /**
   * Registers a new user and returns the user.
   *
   * @param user    the user to register
   * @param session unused for now
   * @return the registered user
   * @throws Exception if the username is taken already
   */
  @PostMapping("/api/register")
  public User register(@RequestBody User user, HttpSession session) throws Exception {
    if (repository.findUserByUsername(user.getUsername()).iterator().hasNext()) {
      throw new Exception("Username already exists.");
    } else {
      session.setAttribute("user", user);
      users.add(user);
      return createUser(user);
    }
  }

  /**
   * Updates a user's information (specific to admin page).
   *
   * @param userId  the ID of the user to update
   * @param newUser the info to add to the user
   * @return the updated user object
   * @throws Exception if the user does not exist
   */
  @PutMapping("/api/user/{userId}")
  public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser) throws Exception {
    Optional<User> data = repository.findById(userId);
    if (data.isPresent()) {
      User user = data.get();
      user.setUsername(newUser.getUsername());
      user.setFirstName(newUser.getFirstName());
      user.setLastName(newUser.getLastName());
      user.setPhone(newUser.getPhone());
      user.setEmail(newUser.getEmail());
      user.setDateOfBirth(newUser.getDateOfBirth());
      user.setRole(newUser.getRole());
      repository.save(user);
      return user;
    }
    throw new Exception("No user found with id = " + userId);
  }

  /**
   * Updates a user's information (specific to profile page).
   * This method is identical to update user but is added in the case that
   * updating a user from the admin is different than from the user
   *
   * @param userId  the ID of the user to update
   * @param newUser the info to add to the user
   * @return the updated user object
   * @throws Exception if the user does not exist (use case: admin deletes account while user is trying to update)
   */
  @PutMapping("/api/profile/{userId}")
  public User updateProfile(@PathVariable("userId") int userId, @RequestBody User newUser) throws Exception {
    Optional<User> data = repository.findById(userId);
    if (data.isPresent()) {
      User user = data.get();
      user.setUsername(newUser.getUsername());
      user.setFirstName(newUser.getFirstName());
      user.setLastName(newUser.getLastName());
      user.setPhone(newUser.getPhone());
      user.setEmail(newUser.getEmail());
      user.setDateOfBirth(newUser.getDateOfBirth());
      user.setRole(newUser.getRole());
      repository.save(user);
      return user;
    }
    throw new Exception("No user found with id = " + userId);
  }

  /**
   * Deletes a user using a given ID.
   *
   * @param id the ID of the user to delete
   */
  @DeleteMapping("/api/user/{userId}")
  public void deleteUser(@PathVariable("userId") int id) {
    repository.deleteById(id);
  }

  /**
   * Logs a user in
   *
   * @param user    the user to log in
   * @param session unused for now
   * @return the user that was logged in
   * @throws Exception if the users credentials cannot be found
   */
  @PostMapping("/api/login")
  public User login(@RequestBody User user, HttpSession session) throws Exception {
    Iterator<User> matchingUsers = repository.findUserByCredentials(user.getUsername(), user.getPassword()).iterator();
    if (matchingUsers.hasNext()) {
      User foundUser = matchingUsers.next();
      session.setAttribute("user", foundUser);
      users.add(user);
      return foundUser;
    }
    throw new Exception("No user found with those credentials");
  }

  /**
   * Send a user an email to reset their password
   *
   * @param username the users username
   * @param request  the information about the request made
   * @throws Exception if user doesn't exist
   */
  @GetMapping("/api/password/{username}")
  public void sendPasswordReset(@PathVariable String username, HttpServletRequest request) throws Exception {
    User user = this.findUserByUsername(username);
    if (!(user.getEmail().equals(""))) {
      SimpleMailMessage mail = new SimpleMailMessage();
      mail.setTo(user.getEmail());
      mail.setSubject("Password Reset");

      StringBuffer resetURL = request.getRequestURL();
      resetURL.setLength(resetURL.indexOf(request.getRequestURI()));
      resetURL.append("/jquery/components/password/password.template.client.html?userId=")
              .append(user.getId());
      mail.setText("Click the following link to reset your password:\n" + resetURL);

      javaMailSender.send(mail);
    } else {
      throw new Exception("No email specified for user.");
    }
  }

  @PutMapping("/api/password/{userId}")
  public User updatePassword(@PathVariable int userId, @RequestBody String password) throws Exception {
    Optional<User> data = repository.findById(userId);
    if (data.isPresent()) {
      User user = data.get();
      user.setPassword(password);
      repository.save(user);
      return user;
    }
    throw new Exception("No user found with id = " + userId);
  }
}