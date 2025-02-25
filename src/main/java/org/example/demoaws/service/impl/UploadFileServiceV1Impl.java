package org.example.demoaws.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demoaws.service.UploadedFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Profile("aws_v1")
@Service
@Slf4j
@RequiredArgsConstructor
public class UploadFileServiceV1Impl implements UploadedFileService {

  private final TransferManager transferManager;
  private final AmazonS3 s3Client;

  @Value("${cloud.aws.s3.bucket_name}")
  private String bucketName;

  @Override
  public void upload(MultipartFile file) {

    try (InputStream content = file.getInputStream()) {
      // here need to apply the security validation for the received file
      ObjectMetadata metadata = buildMetadata(file, content);
      String fileName = buildFileName(file);
      transferManager.upload(bucketName, fileName, content, metadata).waitForUploadResult();
    } catch (IOException | InterruptedException e) {
      log.error("Can't save file to s3: Error message: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private String buildFileName(MultipartFile file) {
    String prefix = "demo_app-kms/";
    String transformedName = file.getName().replace(" ", "_").toLowerCase();
    return prefix + transformedName + LocalDateTime.now();
  }

  private ObjectMetadata buildMetadata(MultipartFile file, InputStream content) throws IOException {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(content.available());
    metadata.setContentType(file.getContentType());
//    metadata.setSSEAlgorithm("AES256"); // this is for AWS S3 SSE-S3 encryption type
    metadata.setSSEAlgorithm("aws:kms"); // this is for AWS S3 SSE-KMS encryption type
    return metadata;
  }
}
