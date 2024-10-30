package com.goruna.spring.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUploadUtil {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // S3 업로드 및 확장자 검증을 한 번에 처리하는 메서드
    public String uploadFile(MultipartFile file) throws IOException {
        // 파일 이름 및 확장자 검증
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        // 파일 확장자 추출 및 검증
        String fileExtension = getFileExtension(fileName);
        if (!fileExtension.equals("jpg") && !fileExtension.equals("png")) {
            throw new IllegalArgumentException("Only JPG and PNG files are allowed.");
        }

        // 고유한 파일명 생성 (UUID 사용)
        fileName = UUID.randomUUID().toString() + "_" + fileName;

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // S3에 파일 업로드
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata);
            amazonS3Client.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new IOException("Failed to upload file to S3", e);
        }

        // S3에 저장된 파일의 URL을 반환
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 파일 확장자 추출 메서드
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}
