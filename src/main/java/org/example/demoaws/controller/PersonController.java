package org.example.demoaws.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.demoaws.documents.Person;
import org.example.demoaws.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonController {

  private final PersonService personService;

  @GetMapping("/persons/{ssn}")
  public Person findBySnn(@PathVariable String ssn, @RequestParam String dateOfBirth) {
    return personService.findPerson(ssn, dateOfBirth);
  }

  @GetMapping("/persons")
  public List<Person> findAllBySsn(@RequestParam String ssn) {
    return personService.findBySsnV2(ssn);
  }

}
