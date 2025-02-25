package org.example.demoaws.config.aws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class S3ConfigV2 {

  @Value("${cloud.aws.region}")
  private String region;

  @Bean
  public S3AsyncClient s3AsyncClient(StaticCredentialsProvider staticCredentialsProvider) {
    return S3AsyncClient.builder()
        .region(Region.of(region))
        .credentialsProvider(staticCredentialsProvider)
        .build();
  }


  @Bean
  public S3TransferManager s3TransferManager(S3AsyncClient s3AsyncClient) {
    return S3TransferManager.builder()
        .s3Client(s3AsyncClient)
        .build();
  }

}
