package org.example.demoaws.repositories.sql;

import org.example.demoaws.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
