package org.example.demoaws.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("aws_v1")
@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class S3ConfigV1 {

  private final AWSStaticCredentialsProvider awsCredentialsProvider;

  @Value("${cloud.aws.region}")
  private String region;

  @Value("${cloud.aws.s3.upload_threshold}")
  private String uploadThreshold;

  @Bean
  public AmazonS3 amazonS3() {
    return AmazonS3ClientBuilder
        .standard()
        .withCredentials(awsCredentialsProvider)
        .withRegion(region)
        .build();
  }

  @Bean
  public TransferManager transferManager(@Autowired AmazonS3 s3client) {
    return TransferManagerBuilder.standard()
        .withS3Client(s3client)
        .withMultipartUploadThreshold(Long.valueOf(uploadThreshold))
        .build();
  }

}
