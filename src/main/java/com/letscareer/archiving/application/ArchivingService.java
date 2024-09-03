package com.letscareer.archiving.application;

import com.letscareer.archiving.dto.response.ArchivingDetailResponse;
import com.letscareer.archiving.dto.response.ArchivingFileResponse;
import com.letscareer.archiving.dto.response.ArchivingResponse;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.archiving.domain.Archiving;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.archiving.domain.repository.ArchivingRepository;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.global.application.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class ArchivingService {

    private final ArchivingRepository archivingRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final S3Service s3Service;

    private final String DIR_NAME = "archiving_files";

    public Long addArchiving(Long recruitmentId, String title, String content, MultipartFile file) throws IOException {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        String originalFileName = file.getOriginalFilename();
        String fileKey = DIR_NAME + "/" + UUID.randomUUID().toString() + "_" + originalFileName;

        String fileUrl = s3Service.upload(file, fileKey);

        Archiving archiving = Archiving.of(recruitment, title, content, originalFileName, fileUrl, fileKey);
        archivingRepository.save(archiving);

        return archiving.getId();
    }

    public List<ArchivingResponse> getArchivingsByRecruitmentId(Long recruitmentId, int page, int size) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        Pageable pageable = PageRequest.of(page, size);

        return recruitment.getArchivings().stream()
                .skip(page * size)
                .limit(size)
                .map(archiving -> new ArchivingResponse(archiving.getId(), archiving.getTitle()))
                .toList();
    }

    public ArchivingDetailResponse getArchivingDetail(Long archivingId) {
        Archiving archiving = archivingRepository.findById(archivingId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ARCHIVING));

        return new ArchivingDetailResponse(archiving.getTitle(), archiving.getContent(), archiving.getFileName());
    }

    public ArchivingFileResponse getArchivingFile(Long archivingId) throws IOException {
        Archiving archiving = archivingRepository.findById(archivingId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ARCHIVING));

        byte[] fileData = s3Service.downloadFile(archiving.getFileKey());

        return new ArchivingFileResponse(archiving.getFileName(), fileData);
    }


    public void updateArchiving(Long archivingId, String title, String content, MultipartFile file) throws IOException {
        Archiving archiving = archivingRepository.findById(archivingId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ARCHIVING));

        if (file != null && !file.isEmpty()) {
            s3Service.deleteFile(archiving.getFileKey());
            String originalFileName = file.getOriginalFilename();
            String fileKey = DIR_NAME + "/" + UUID.randomUUID().toString() + "_" + originalFileName;
            String newFileUrl = s3Service.upload(file, fileKey);
            archiving.update(title, content, originalFileName, newFileUrl, fileKey);
        } else {
            archiving.update(title, content, archiving.getFileName(), archiving.getFileUrl(), archiving.getFileKey());
        }

        archivingRepository.save(archiving);
    }

    public void deleteArchiving(Long archivingId) {
        Archiving archiving = archivingRepository.findById(archivingId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ARCHIVING));

        s3Service.deleteFile(archiving.getFileKey());
        archivingRepository.delete(archiving);
    }
}
