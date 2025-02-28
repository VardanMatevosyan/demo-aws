package org.example.demoaws.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

@Configuration
@RequiredArgsConstructor
public class DynamoDbConfig {

  private final StaticCredentialsProvider credentialsProvider;
  private final AWSStaticCredentialsProvider awsStaticCredentialsProvider;

  @Value("${cloud.dynamodb.endpoint}")
  private String amazonDynamoDbEndpoint;

  @Value("${cloud.aws.region}")
  private String region;

  @Bean("dynamoDbClient")
  public DynamoDbClient dynamoDbClient() {
    DynamoDbClientBuilder builder = DynamoDbClient.builder()
        .credentialsProvider(credentialsProvider)
        .region(Region.of(region))
        .endpointOverride(URI.create("https://" + amazonDynamoDbEndpoint))
        ;
    return builder.build();
  }

  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
    return DynamoDbEnhancedClient.builder()
        .dynamoDbClient(dynamoDbClient)
        .build();
  }

// below beans if for com.github.derjust it uses the com.amazonaws old repository
  @Bean
  @Primary
  public AmazonDynamoDB buildAmazonDynamoDb() {
    return AmazonDynamoDBClientBuilder
        .standard()
        .withCredentials(awsStaticCredentialsProvider)
        .withEndpointConfiguration(
            new AwsClientBuilder.EndpointConfiguration(amazonDynamoDbEndpoint, region))
        .build();
  }

  @Bean
  public DynamoDBMapper dynamoDbMapper() {
    DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig
        .builder()
        .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
        .build();
    return new DynamoDBMapper(buildAmazonDynamoDb(), mapperConfig);
  }

  @Bean
  public DynamoDB dynamoDB() {
    return new DynamoDB(buildAmazonDynamoDb());
  }

  @Bean
  public DynamoDBMapperConfig dynamoDBMapperConfig() {
    DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
    builder.setPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.LAZY_LOADING);
    builder.setTypeConverterFactory(DynamoDBTypeConverterFactory.standard());
    return builder.build();
  }




}
