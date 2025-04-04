package org.example.demoaws.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.demoaws.entities.User;
import org.example.demoaws.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

  UserService userService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/users")
  public ResponseEntity<Void> saveUser(@RequestBody User user) {
    userService.save(user);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAuthority('PERMISSION_ADMIN:READ')")
  @GetMapping("/users/{id}")
  public ResponseEntity<User> saveUser(@PathVariable Long id) {
    User user = userService.findById(id);
    return ResponseEntity.ok(user);
  }

  @PreAuthorize("hasAuthority('SCOPE_aws-demo-rs/USER:READ')")
  @GetMapping("/users/{code}/deployment7")
  public ResponseEntity<String> saveUser(@PathVariable String code) {
    return ResponseEntity.ok(code);
  }


}
