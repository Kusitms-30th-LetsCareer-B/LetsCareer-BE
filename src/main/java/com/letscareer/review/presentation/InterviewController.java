package com.letscareer.review.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.introduce.dto.request.ReactionReq;
import com.letscareer.review.application.InterviewService;
import com.letscareer.review.dto.request.InterviewReq;
import com.letscareer.review.dto.response.GetInterviewRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PutMapping("/interviews")
    public ResponseEntity<CommonResponse<Void>> createOrModifyInterview(@RequestParam Long recruitmentId,
                                                                        @RequestBody List<InterviewReq> request){
        interviewService.createOrModifyInterview(recruitmentId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("복기노트 면접 질문 작성이 되었습니다", null));
    }

    @GetMapping("/interviews")
    public ResponseEntity<CommonResponse<List<GetInterviewRes>>> getInterviews(@RequestParam Long recruitmentId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("복기노트 면접 질문 조회가 되었습니다", interviewService.getInterviews(recruitmentId)));
    }

    @DeleteMapping("/interviews/{interviewId}")
    public ResponseEntity<CommonResponse<Void>> deleteInterview(@PathVariable Long interviewId,
                                                                @RequestParam Long recruitmentId){
        interviewService.deleteInterview(interviewId, recruitmentId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("복기노트 면접 질문 삭제가 되었습니다", null));
    }

    @PatchMapping("/interviews/{interviewId}/reaction")
    public ResponseEntity<CommonResponse<Void>> modifyReaction(@PathVariable Long interviewId, @RequestBody ReactionReq request){
        interviewService.modifyReaction(interviewId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("복기노트 면접 질문에 대한 반응을 남겼습니다", null));
    }


}