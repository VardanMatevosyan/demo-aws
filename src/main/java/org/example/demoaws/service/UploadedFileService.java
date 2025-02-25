package org.example.demoaws.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadedFileService {

  void upload(MultipartFile file);

}
