package org.example.demoaws.service;

import java.util.List;
import org.example.demoaws.documents.Person;

public interface PersonService {

  Person findPerson(String snn, String dateOfBirth);
  List<Person> findBySsnV2(String ssn);

}
