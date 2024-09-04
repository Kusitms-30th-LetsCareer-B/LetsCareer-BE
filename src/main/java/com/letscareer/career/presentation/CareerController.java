package com.letscareer.career.presentation;

import com.letscareer.career.application.CareerService;
import com.letscareer.career.dto.request.CareerRequest;
import com.letscareer.career.dto.response.CareerDetailResponse;
import com.letscareer.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/careers")
public class CareerController {

    private final CareerService careerService;

    /**
     * 사용자의 커리어 정보 전부 조회
     *
     * @param userId the user id
     * @return the career details
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse<CareerDetailResponse>> getCareerDetails(
            @PathVariable Long userId) {
        CareerDetailResponse careerDetailResponse = careerService.getCareerDetails(userId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("사용자 커리어 관련 조회에 성공하였습니다.", careerDetailResponse));
    }

    /**
     * 기본 이력서에 필요한 데이터들을 한꺼번에 저장하거나 수정
     *
     * @param careerRequest the career request
     * @param profileImage  the profile image
     * @param portfolioFile the portfolio file
     * @return the response entity
     * @throws IOException the io exception
     */
    @PutMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> saveOrUpdateCareer(
            @RequestPart("careerRequest") CareerRequest careerRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestPart(value = "portfolioFile", required = false) MultipartFile portfolioFile) throws IOException {

        Long userId = careerService.saveOrUpdateCareer(careerRequest, profileImage, portfolioFile);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("사용자 정보가 성공적으로 저장되었습니다.", userId));
    }

}
