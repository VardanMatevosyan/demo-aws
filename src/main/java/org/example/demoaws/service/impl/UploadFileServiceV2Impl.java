package org.example.demoaws.service.impl;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demoaws.service.UploadedFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.Upload;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadFileServiceV2Impl implements UploadedFileService {

  private final S3TransferManager s3TransferManager;
  private final S3AsyncClient s3AsyncClient;

  @Value("${cloud.aws.s3.bucket_name}")
  private String bucketName;

  @Override
  public void upload(MultipartFile file) {
    try (InputStream content = file.getInputStream()) {
      // here need to apply the security validation for the received file
      PutObjectRequest putObjectRequest = buildPutObjectRequest(file, content);
      UploadRequest uploadRequest = buildUploadRequest(putObjectRequest, content);
      Upload upload = s3TransferManager.upload(uploadRequest);
      upload.completionFuture().join(); // Blocks until the upload completes
    } catch (IOException e) {
      log.error("Can't save file to s3: Error message: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private UploadRequest buildUploadRequest(
      PutObjectRequest putObjectRequest, InputStream content) throws IOException {

    ExecutorService executorService = Executors.newFixedThreadPool(5);
    AsyncRequestBody body = AsyncRequestBody
        .fromInputStream(content, (long) content.available(), executorService);
    return UploadRequest.builder()
        .putObjectRequest(putObjectRequest)
        .requestBody(body)
        .build();
  }

  private String buildFileName(MultipartFile file) {
    String prefix = "demo_app-kms-v2/";
    String transformedName = Objects
        .requireNonNull(file.getOriginalFilename())
        .replace(" ", "_")
        .toLowerCase();
    return String.format("%s_%s_%s", prefix, transformedName, LocalDateTime.now());
  }

  private PutObjectRequest buildPutObjectRequest(MultipartFile file,
      InputStream content) throws IOException {
    String fileName = buildFileName(file);
    return PutObjectRequest.builder()
        .bucket(bucketName)
        .key(fileName)
        .contentType(file.getContentType())
        .contentLength((long) content.available())
        .serverSideEncryption(ServerSideEncryption.AWS_KMS) //  AWS S3 SSE-KMS encryption type
//        .serverSideEncryption(ServerSideEncryption.AES256) // AWS S3 SSE-S3 encryption type
        .build();
  }
}
