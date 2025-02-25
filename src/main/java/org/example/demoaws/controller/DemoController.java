package org.example.demoaws.controller;

import lombok.RequiredArgsConstructor;
import org.example.demoaws.service.UploadedFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DemoController {

  private final UploadedFileService uploadedFileService;

  @PostMapping("/files")
  public void uploadFile(@RequestParam("file") MultipartFile file) {
    uploadedFileService.upload(file);
  }

}
