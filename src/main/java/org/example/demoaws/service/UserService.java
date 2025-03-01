package org.example.demoaws.service;

import org.example.demoaws.entities.User;

public interface UserService {

  void save(User user);
  User findById(Long id);

}
