package com.letscareer.archiving.presentation;

import com.letscareer.archiving.application.ArchivingService;
import com.letscareer.archiving.dto.request.ArchivingRequest;
import com.letscareer.archiving.dto.response.ArchivingDetailResponse;
import com.letscareer.archiving.dto.response.ArchivingFileResponse;
import com.letscareer.archiving.dto.response.ArchivingResponse;
import com.letscareer.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archivings")
public class ArchivingController {

    private final ArchivingService archivingService;

    /**
     * 새로운 아카이빙 추가
     *
     * @param recruitmentId    the recruitment id
     * @param file             the file
     * @param archivingRequest the archiving request
     * @return the response entity
     * @throws IOException the io exception
     */
    @PostMapping(path = "")
    public ResponseEntity<CommonResponse<Long>> addArchiving(
            @RequestParam Long recruitmentId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("archivingRequest") ArchivingRequest archivingRequest) throws IOException {

        Long archivingId = archivingService.addArchiving(recruitmentId, archivingRequest.title(), archivingRequest.content(), file);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 추가에 성공하였습니다.", archivingId));
    }

    /**
     * 채용 일정에 대한 아카이빙 목록 조회
     *
     * @param recruitmentId the recruitment id
     * @param page          the page
     * @param size          the size
     * @return the archivings by recruitment id
     */
    @GetMapping("/recruitment")
    public ResponseEntity<CommonResponse<List<ArchivingResponse>>> getArchivingsByRecruitmentId(
            @RequestParam Long recruitmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        List<ArchivingResponse> archivings = archivingService.getArchivingsByRecruitmentId(recruitmentId, page, size);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 목록 조회에 성공하였습니다.", archivings));
    }

    /**
     * 특정 아카이빙의 상세 정보 조회(파일 제외)
     *
     * @param archivingId the archiving id
     * @return the archiving detail
     */
    @GetMapping("/detail")
    public ResponseEntity<ArchivingDetailResponse> getArchivingDetail(
            @RequestParam Long archivingId) {

        ArchivingDetailResponse archivingDetail = archivingService.getArchivingDetail(archivingId);
        return ResponseEntity.ok(archivingDetail);
    }


    /**
     * 특정 아카이빙의 파일만 조회
     *
     * @param archivingId the archiving id
     * @return the response entity
     * @throws IOException the io exception
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadArchivingFile(
            @RequestParam Long archivingId) throws IOException {

        ArchivingFileResponse archivingFile = archivingService.getArchivingFile(archivingId);
        return buildFileResponse(archivingFile);
    }

    private ResponseEntity<byte[]> buildFileResponse(ArchivingFileResponse archivingFile) {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\""
                        + URLEncoder.encode(archivingFile.fileName(), StandardCharsets.UTF_8) + "\"")
                .body(archivingFile.fileData());
    }


    /**
     * 특정 아카이빙 내용 및 파일 업데이트
     *
     * @param archivingId      the archiving id
     * @param file             the file
     * @param archivingRequest the archiving request
     * @return the response entity
     * @throws IOException the io exception
     */
    @PatchMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<?>> updateArchiving(
            @RequestParam Long archivingId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("archivingRequest") ArchivingRequest archivingRequest) throws IOException {

        archivingService.updateArchiving(archivingId, archivingRequest.title(), archivingRequest.content(), file);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 수정에 성공하였습니다.", null));
    }

    /**
     * 특정 아카이빙 삭제
     *
     * @param archivingId the archiving id
     * @return the response entity
     */
    @DeleteMapping("")
    public ResponseEntity<CommonResponse<?>> deleteArchiving(
            @RequestParam Long archivingId) {

        archivingService.deleteArchiving(archivingId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("아카이빙 삭제에 성공하였습니다.", null));
    }
}
