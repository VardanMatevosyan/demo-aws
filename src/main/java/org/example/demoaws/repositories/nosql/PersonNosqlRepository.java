package org.example.demoaws.repositories.nosql;

import java.util.List;
import java.util.Optional;
import org.example.demoaws.documents.Person;


public interface PersonNosqlRepository {

  Optional<Person> findBySsnAndDateOfBirth(String ssn);
  Optional<Person> findBySsnAndDateOfBirthV2(String ssn, String dateOfBirth);
  List<Person> findBySsnV2(String ssn);

}
