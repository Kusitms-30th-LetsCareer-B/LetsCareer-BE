package com.letscareer.archiving.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.archiving.application.ArchivingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archiving")
public class ArchivingController {

    private final ArchivingService archivingService;

    @PostMapping("")
    public ResponseEntity<CommonResponse<Long>> addArchiving(
            @RequestParam Long recruitmentId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam MultipartFile file) throws IOException {

        Long archivingId = archivingService.addArchiving(recruitmentId, title, content, file);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 추가에 성공하였습니다.", archivingId));
    }

    @PutMapping("")
    public ResponseEntity<CommonResponse<?>> updateArchiving(
            @RequestParam Long recruitmentId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(required = false) MultipartFile file) throws IOException {

        archivingService.updateArchiving(recruitmentId, title, content, file);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 수정에 성공하였습니다.", null));
    }

    @DeleteMapping("")
    public ResponseEntity<CommonResponse<?>> deleteArchiving(
            @RequestParam Long recruitmentId) {

        archivingService.deleteArchiving(recruitmentId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 삭제에 성공하였습니다.", null));
    }
}
