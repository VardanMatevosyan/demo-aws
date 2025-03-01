package org.example.demoaws.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.demoaws.entities.User;
import org.example.demoaws.repositories.sql.UserRepository;
import org.example.demoaws.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public User findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User Not Found"));
  }
}
