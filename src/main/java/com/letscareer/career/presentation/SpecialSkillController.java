package com.letscareer.career.presentation;

import com.letscareer.career.dto.request.SpecialSkillRequest;
import com.letscareer.global.domain.CommonResponse;
import com.letscareer.career.application.SpecialSkillService;
import com.letscareer.career.dto.response.SpecialSkillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/careers/special-skills")
public class SpecialSkillController {

    private final SpecialSkillService specialSkillService;

    /**
     * 필살기 경험 목록 조회
     *
     * @param userId the user id
     * @return the special skills list
     */
    @GetMapping("")
    public ResponseEntity<CommonResponse<SpecialSkillResponse>> getSpecialSkills(
            @RequestParam Long userId) {
        SpecialSkillResponse skills = specialSkillService.getSpecialSkillsByUserId(userId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("필살기 경험 조회에 성공하였습니다.", skills));
    }

    /**
     * 필살기 생성
     *
     * @param userId        the user id
     * @param request       the special skill create request
     * @return the response entity with special skill id
     */
    @PostMapping("")
    public ResponseEntity<CommonResponse<Long>> addSpecialSkill(
            @RequestParam Long userId,
            @RequestBody SpecialSkillRequest request) {

        Long specialSkillId = specialSkillService.addSpecialSkill(request, userId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("필살기 생성에 성공하였습니다.", specialSkillId));
    }

    /**
     * 필살기 수정
     *
     * @param specialSkillId the special skill id
     * @param request        the special skill update request
     * @return the response entity
     */
    @PatchMapping("")
    public ResponseEntity<CommonResponse<?>> updateSpecialSkill(
            @RequestParam Long specialSkillId,
            @RequestBody SpecialSkillRequest request) {

        specialSkillService.updateSpecialSkill(request, specialSkillId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("필살기 수정에 성공하였습니다.", null));
    }

    /**
     * 필살기 경험 삭제
     *
     * @param skillId the special skill id
     * @return the response entity
     */
    @DeleteMapping("")
    public ResponseEntity<CommonResponse<?>> deleteSpecialSkill(
            @RequestParam Long skillId) {
        specialSkillService.deleteSpecialSkill(skillId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("필살기 경험 삭제에 성공하였습니다.", null));
    }
}
