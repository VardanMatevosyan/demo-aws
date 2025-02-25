package org.example.demoaws.config.aws;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class AwsCredential {

  @Value("${cloud.aws.credentials.access-key}")
  private String accessKeyId;
  @Value("${cloud.aws.credentials.secret-key}")
  private String secretAccessKey;

}
