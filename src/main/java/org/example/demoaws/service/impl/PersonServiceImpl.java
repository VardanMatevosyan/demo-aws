package org.example.demoaws.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.demoaws.documents.Person;
import org.example.demoaws.repositories.nosql.PersonNosqlRepository;
import org.example.demoaws.service.PersonService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonNosqlRepository personNosqlRepository;

  @Override
  public Person findPerson(String ssn, String dateOfBirth) {
    return personNosqlRepository.findBySsnAndDateOfBirthV2(ssn, dateOfBirth).orElseThrow();
  }

  @Override
  public List<Person> findBySsnV2(String ssn) {
    return personNosqlRepository.findBySsnV2(ssn);
  }
}
