package com.letscareer.global.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String fileKey) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        String uploadImageUrl = putS3(uploadFile, fileKey);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileKey) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileKey, uploadFile));
        return amazonS3.getUrl(bucket, fileKey).toString();
    }

    public void deleteFile(String fileKey) {
        if (amazonS3.doesObjectExist(bucket, fileKey)) {
            log.info("파일이 존재합니다. 파일 삭제를 진행합니다. Key: {}", fileKey);
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileKey));
            log.info("파일 삭제 성공. Key: {}", fileKey);
        } else {
            log.warn("삭제할 파일이 존재하지 않습니다. Key: {}", fileKey);
            throw new CustomException(ExceptionContent.NOT_FOUND_FILE);
        }
    }

    public byte[] downloadFile(String fileKey) throws IOException {
        try {
            log.info("Downloading file from S3. Key: {}", fileKey);
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, fileKey));
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            return inputStream.readAllBytes();
        } catch (AmazonS3Exception e) {
            log.error("Error downloading file from S3. Key: {}", fileKey, e);
            if (e.getStatusCode() == 404) {
                log.error("S3 파일을 찾을 수 없습니다. Key: {}", fileKey);
                throw new CustomException(ExceptionContent.NOT_FOUND_FILE);
            } else {
                throw e;
            }
        }
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = File.createTempFile("upload_", "_" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }
        return Optional.of(convertFile);
    }
}
