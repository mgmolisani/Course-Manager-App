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
  public User findUserById(@PathVariable("userId") int userId) {
    Optional<User> data = repository.findById(userId);
    return data.orElse(null);
  }

  private User findUserByUsername(String username) {
    Iterator<User> matchingUsers = repository.findUserByUsername(username).iterator();
    //Selects the first user in the returned list if any matching users exist.
    if (matchingUsers.hasNext()) {
      return matchingUsers.next();
    }
    return null;
  }

  @PostMapping("/api/register")
  public User register(@RequestBody User user, HttpSession session) {
    session.setAttribute("currentUser", user);
    users.add(user);
    return user;
  }

  @PutMapping("/api/user/{userId}")
  public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser) {
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
    return null;
  }

  @DeleteMapping("/api/user/{userId}")
  public void deleteUser(@PathVariable("userId") int id) {
    repository.deleteById(id);
  }
}