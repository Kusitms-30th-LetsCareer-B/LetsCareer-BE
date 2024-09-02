package com.letscareer.archiving.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.archiving.domain.Archiving;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.archiving.domain.repository.ArchivingRepository;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.global.application.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Transactional
@Service
@RequiredArgsConstructor
public class ArchivingService {

    private final ArchivingRepository archivingRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final S3Service s3Service;

    public Long addArchiving(Long recruitmentId, String title, String content, MultipartFile file) throws IOException {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        String fileUrl = s3Service.upload(file);

        Archiving archiving = Archiving.of(recruitment, title, content, fileUrl);
        archivingRepository.save(archiving);

        return archiving.getId();
    }

    public void updateArchiving(Long archivingId, String title, String content, MultipartFile file) throws IOException {
        Archiving archiving = archivingRepository.findById(archivingId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ARCHIVING));

        if (file != null && !file.isEmpty()) {
            s3Service.deleteFile(archiving.getFileUrl());
            String newFileUrl = s3Service.upload(file);
            archiving.update(title, content, newFileUrl);
        } else {
            archiving.update(title, content, archiving.getFileUrl());
        }

        archivingRepository.save(archiving);
    }

    public void deleteArchiving(Long archivingId) {
        Archiving archiving = archivingRepository.findById(archivingId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ARCHIVING));

        s3Service.deleteFile(archiving.getFileUrl());
        archivingRepository.delete(archiving);
    }
}
