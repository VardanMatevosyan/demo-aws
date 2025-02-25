package org.example.demoaws.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
@RequiredArgsConstructor
public class StaticCredentialsProviderConfig {

  private final AwsCredential awsCredentials;

  @Bean
  public StaticCredentialsProvider getStaticCredentialsProvider() {
    return StaticCredentialsProvider.create(
        AwsBasicCredentials.create(
            awsCredentials.getAccessKeyId(),
            awsCredentials.getSecretAccessKey()));
  }

  @Bean
  public AWSStaticCredentialsProvider getAWSStaticCredentialsProvider() {
    return new AWSStaticCredentialsProvider(
        new BasicAWSCredentials(
            awsCredentials.getAccessKeyId(),
            awsCredentials.getSecretAccessKey()));
  }

}
