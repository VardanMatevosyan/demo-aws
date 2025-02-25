package org.example.demoaws.documents;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@DynamoDBTable(tableName = "persons")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = {"ssn"}, callSuper = false)
@Builder
@ToString
public class PersonV1 extends Person {

  @DynamoDBHashKey
  private String ssn;

  @DynamoDBRangeKey
  private String dateOfBirth;

  @DynamoDBAttribute
  private String email;

  @DynamoDBAttribute
  private String firstName;

  @DynamoDBAttribute
  private String lastName;



}
