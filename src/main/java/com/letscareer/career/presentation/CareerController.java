package com.letscareer.career.presentation;

import com.letscareer.career.application.CareerService;
import com.letscareer.career.dto.response.CareerDetailResponse;
import com.letscareer.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
