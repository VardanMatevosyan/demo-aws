package org.example.demoaws.repositories.nosql;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.demoaws.documents.Person;
import org.example.demoaws.documents.PersonV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

@Repository
@RequiredArgsConstructor
public class PersonNosqlRepositoryImpl implements PersonNosqlRepository {

  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  @Value("${cloud.aws.dynamoDb.person.table.name}")
  private String tableName;


  @Override
  public Optional<Person> findBySsnAndDateOfBirth(String ssn) {
    //    Map<String, AttributeValue> keyMap = Map.of("ssn", AttributeValue.builder().s(ssn).build());
//    GetItemRequest getItemRequest = GetItemRequest.builder()
//        .tableName("persons")
//        .key(keyMap)
//        .build();
//
//
//    GetItemResponse response = dynamoDbClient.getItem(getItemRequest);
//    Map<String, AttributeValue> item = response.item();

    return Optional.empty();
  }

  @Override
  public Optional<Person> findBySsnAndDateOfBirthV2(String ssn, String dateOfBirth) {
    GetItemEnhancedRequest enhancedRequest = GetItemEnhancedRequest.builder()
        .key(
            Key.builder()
            .partitionValue(ssn).sortValue(dateOfBirth)
            .build())
        .build();

    DynamoDbTable<PersonV2> personsTable =
        dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(PersonV2.class));

    PersonV2 person = personsTable.getItem(enhancedRequest);
    return Optional.of(person);
  }

  @Override
  public List<Person> findBySsnV2(String ssn) {
    QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
        .partitionValue(ssn)
        .build());

    DynamoDbTable<PersonV2> personsTable =
        dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(PersonV2.class));

    return personsTable.query(queryConditional)
        .items()
        .stream()
        .map(p -> (Person) p)
        .toList();

  }


}
