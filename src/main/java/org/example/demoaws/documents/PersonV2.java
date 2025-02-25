package org.example.demoaws.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode(of = {"ssn"}, callSuper = false)
@Builder
@ToString
public class PersonV2 extends Person {

  private String ssn;
  private String dateOfBirth;
  private String email;
  private String firstName;
  private String lastName;

  @DynamoDbPartitionKey
  public String getSsn() {
    return ssn;
  }
  @DynamoDbSortKey
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  @DynamoDbAttribute("email")
  public String getEmail() {
    return email;
  }

  @DynamoDbAttribute("firstName")
  public String getFirstName() {
    return firstName;
  }

  @DynamoDbAttribute("lastName")
  public String getLastName() {
    return lastName;
  }
}
