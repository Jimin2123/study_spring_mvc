package com.springmvc.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Controller
@RequestMapping("/bookimage")
public class ImageDownloadController {

  @Value("${uploadPath}")
  private String uploadPath;

  public ImageDownloadController() {}

  @GetMapping("/{fileName}")
  @ResponseBody // 반환 값을 뷰가 아닌 HTTP 응답 바디로 직접 전송
  public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
    // 업로드 경로(uploadPath)와 파일명을 조합하여 다운로드 대상 파일 객체 생성
    File donwloadFile = new File(this.uploadPath, fileName);

    // 파일이 존재하지 않으면 404 오류 응답 전송 후 종료
    if (!donwloadFile.exists()) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    // 파일의 MIME 타입을 자동으로 추정하여 Content-Type 설정
    String mimeType = Files.probeContentType(donwloadFile.toPath());
    response.setContentType(mimeType);

    // 1KB 크기의 버퍼를 생성하여 스트리밍 처리 준비
    byte[] buffer = new byte[1024];
    ServletOutputStream out = response.getOutputStream();

    // 파일 입력 스트림을 열고, 버퍼 단위로 읽어 응답 스트림에 씀
    try (InputStream in = new FileInputStream(donwloadFile)) {
      for (int size = 0; (size = in.read(buffer)) != -1;) {
        out.write(buffer, 0, size);
      }
    }

    // 모든 데이터를 보낸 후 응답 버퍼를 플러시하여 전송 완료
    response.flushBuffer();
  }
}
