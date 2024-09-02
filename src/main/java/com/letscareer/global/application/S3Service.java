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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String DIR_NAME = "archiving_files";

    public String upload(MultipartFile multipartFile) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile);
    }

    private String upload(File uploadFile) {
        String originalFileName = uploadFile.getName();
        String newFileName = DIR_NAME + "/" + UUID.randomUUID().toString() + "_" + originalFileName;
        String uploadImageUrl = putS3(uploadFile, newFileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
//                        .withCannedAcl(CannedAccessControlList.PublicRead) //ACL을 사용하지 않는 S3 버킷에서는 사용 X
        );
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, DIR_NAME + "/" + fileName));
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

    public byte[] downloadFile(String fileUrl) throws IOException {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String key = DIR_NAME + "/" + fileName;

        try {
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, key));
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            return inputStream.readAllBytes();
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == 404) {
                log.error("S3 파일을 찾을 수 없습니다. Key: {}", key);
                throw new CustomException(ExceptionContent.NOT_FOUND_FILE);
            } else {
                throw e;
            }
        }
    }
}
