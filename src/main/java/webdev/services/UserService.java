package webdev.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import webdev.models.User;
import webdev.repositories.UserRepository;

@RestController
public class UserService {
  @Autowired
  private UserRepository repository;
  private List<User> users = new ArrayList<>();

  @PostMapping("/api/user")
  public User createUser(@RequestBody User user) {
    return repository.save(user);
  }

  @GetMapping("/api/user")
  public List<User> findAllUsers() {
    return (List<User>) repository.findAll();
  }

  @GetMapping("/api/user/{userId}")
  public User findUserById(@PathVariable("userId") int userId) throws Exception {
    Optional<User> data = repository.findById(userId);
    if (data.isPresent()) {
      return data.get();
    }
    throw new Exception("No user found with id = " + userId);
  }

  @GetMapping(value = "/api/user", params = "username")
  public User findUserByUsername(@RequestParam String username) throws Exception {
    Iterator<User> matchingUsers = repository.findUserByUsername(username).iterator();
    //Selects the first user in the returned list if any matching users exist.
    if (matchingUsers.hasNext()) {
      return matchingUsers.next();
    }
    throw new Exception("User does not exist.");
  }

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

  @DeleteMapping("/api/user/{userId}")
  public void deleteUser(@PathVariable("userId") int id) {
    repository.deleteById(id);
  }

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
}