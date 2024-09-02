package com.letscareer.archiving.presentation;

import com.letscareer.archiving.application.ArchivingService;
import com.letscareer.archiving.dto.request.ArchivingRequest;
import com.letscareer.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archiving")
public class ArchivingController {

    private final ArchivingService archivingService;

    @PostMapping(path = "")
    public ResponseEntity<CommonResponse<Long>> addArchiving(
            @RequestParam Long recruitmentId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("archivingRequest") ArchivingRequest archivingRequest) throws IOException {

        Long archivingId = archivingService.addArchiving(recruitmentId, archivingRequest.title(), archivingRequest.content(), file);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 추가에 성공하였습니다.", archivingId));
    }

    @PatchMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<?>> updateArchiving(
            @RequestParam Long archivingId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("archivingRequest") ArchivingRequest archivingRequest) throws IOException {

        archivingService.updateArchiving(archivingId, archivingRequest.title(), archivingRequest.content(), file);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 수정에 성공하였습니다.", null));
    }

    @DeleteMapping("")
    public ResponseEntity<CommonResponse<?>> deleteArchiving(
            @RequestParam Long archivingId) {

        archivingService.deleteArchiving(archivingId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 삭제에 성공하였습니다.", null));
    }
}