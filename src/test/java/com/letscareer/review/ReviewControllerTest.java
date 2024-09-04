package com.letscareer.review;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.review.application.ReviewService;
import com.letscareer.review.dto.request.ReviewRequest;
import com.letscareer.review.dto.response.ReviewResponse;
import com.letscareer.review.presentation.ReviewController;
import com.letscareer.configuration.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest extends ControllerTestConfig {

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("리크루트먼트 ID로 복기 노트 조회")
    public void testGetReviewsByRecruitmentId() throws Exception {
        // given
        ReviewResponse.ReviewDto documentReview = new ReviewResponse.ReviewDto(
                1L, null, "만족", List.of("지원동기", "논리적 구성"),  // 서류 리뷰에는 reviewName이 없음
                List.of("명확한 표현"), "잘한 점 설명", "개선할 점 설명", null  // 서류 리뷰에 난이도는 없으므로 null
        );

        ReviewResponse.ReviewDto interviewReview = new ReviewResponse.ReviewDto(
                2L, null, "보통", List.of("직무 적합성", "경험 활용"),  // 면접 리뷰에는 reviewName이 없음
                List.of("산업 이해도"), "면접 잘한 점", "면접 개선할 점", null  // 면접 리뷰에 난이도는 없으므로 null
        );

        ReviewResponse.ReviewDto etcReview1 = new ReviewResponse.ReviewDto(
                3L, "기타 리뷰 1", null, null, null,  // 기타 리뷰에는 reviewName이 있음
                "기타 잘한 점 1", "기타 개선할 점 1", "상"
        );

        ReviewResponse.ReviewDto etcReview2 = new ReviewResponse.ReviewDto(
                4L, "기타 리뷰 2", null, null, null,  // 기타 리뷰에는 reviewName이 있음
                "기타 잘한 점 2", "기타 개선할 점 2", "중"
        );


        ReviewResponse reviewResponse = new ReviewResponse(
                documentReview, interviewReview, List.of(etcReview1, etcReview2)
        );

        Mockito.when(reviewService.getReviewsByRecruitmentId(anyLong())).thenReturn(reviewResponse);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/reviews/recruitment")
                .param("recruitmentId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("reviews/getReviewsByRecruitmentId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("복기 노트")
                                        .description("채용 일정 ID로 복기 노트 전부 조회")
                                        .queryParameters(
                                                parameterWithName("recruitmentId").description("리크루트먼트 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),

                                                // 서류 리뷰
                                                fieldWithPath("data.document.id").description("서류 리뷰 ID"),
                                                fieldWithPath("data.document.reviewName").description("서류 리뷰 이름 (null)").optional(),  // reviewName 명시적으로 처리
                                                fieldWithPath("data.document.satisfaction").description("서류 리뷰 만족도").optional(),
                                                fieldWithPath("data.document.wellDonePoints").description("서류 리뷰에서 잘한 점").optional(),
                                                fieldWithPath("data.document.shortcomingPoints").description("서류 리뷰에서 개선할 점").optional(),
                                                fieldWithPath("data.document.wellDoneMemo").description("서류 리뷰에서 잘한 점 설명").optional(),
                                                fieldWithPath("data.document.shortcomingMemo").description("서류 리뷰에서 개선할 점 설명").optional(),
                                                fieldWithPath("data.document.difficulty").ignored().description("서류 리뷰에는 난이도가 없음"),

                                                // 면접 리뷰
                                                fieldWithPath("data.interview.id").description("면접 리뷰 ID"),
                                                fieldWithPath("data.interview.reviewName").description("면접 리뷰 이름 (null)").optional(),  // reviewName 명시적으로 처리
                                                fieldWithPath("data.interview.satisfaction").description("면접 리뷰 만족도").optional(),
                                                fieldWithPath("data.interview.wellDonePoints").description("면접 리뷰에서 잘한 점").optional(),
                                                fieldWithPath("data.interview.shortcomingPoints").description("면접 리뷰에서 개선할 점").optional(),
                                                fieldWithPath("data.interview.wellDoneMemo").description("면접 리뷰에서 잘한 점 설명").optional(),
                                                fieldWithPath("data.interview.shortcomingMemo").description("면접 리뷰에서 개선할 점 설명").optional(),
                                                fieldWithPath("data.interview.difficulty").ignored().description("면접 리뷰에는 난이도가 없음"),

                                                // 기타 리뷰
                                                fieldWithPath("data.etc[].id").description("기타 리뷰 ID"),
                                                fieldWithPath("data.etc[].reviewName").description("기타 리뷰 이름"),
                                                fieldWithPath("data.etc[].difficulty").description("기타 리뷰 난이도").optional(),
                                                fieldWithPath("data.etc[].satisfaction").description("기타 리뷰 만족도 (null 가능)").optional(),
                                                fieldWithPath("data.etc[].wellDonePoints").description("기타 리뷰에서 잘한 점 (null 가능)").optional(),
                                                fieldWithPath("data.etc[].shortcomingPoints").description("기타 리뷰에서 개선할 점 (null 가능)").optional(),
                                                fieldWithPath("data.etc[].wellDoneMemo").description("기타 리뷰에서 잘한 점 설명").optional(),
                                                fieldWithPath("data.etc[].shortcomingMemo").description("기타 리뷰에서 개선할 점 설명").optional()
                                        )
                                        .responseSchema(Schema.schema("ReviewResponse"))
                                        .build()
                        )
                ));


    }

    @Test
    @DisplayName("리뷰 저장 또는 수정")
    public void testSaveOrUpdateReviews() throws Exception {
        // given
        Mockito.doNothing().when(reviewService).saveOrUpdateReviews(any(ReviewRequest.class));

        String reviewRequestJson = """
    {
        "document": {
            "id": 1,
            "recruitmentId": 1,
            "satisfaction": "만족",
            "wellDonePoints": ["지원동기", "논리적 구성"],
            "shortcomingPoints": ["명확한 표현"],
            "wellDoneMemo": "잘한 점 설명",
            "shortcomingMemo": "개선할 점 설명"
        },
        "interview": {
            "id": 2,
            "recruitmentId": 1,
            "satisfaction": "보통",
            "wellDonePoints": ["직무 적합성", "경험 활용"],
            "shortcomingPoints": ["산업 이해도"],
            "wellDoneMemo": "면접 잘한 점",
            "shortcomingMemo": "면접 개선할 점"
        },
        "etc": [
            {
                "id": 3,
                "recruitmentId": 1,
                "reviewName": "기타 리뷰 1",
                "satisfaction": "만족",
                "difficulty": "상",
                "wellDoneMemo": "기타 잘한 점 1",
                "shortcomingMemo": "기타 개선할 점 1"
            },
            {
                "id": 4,
                "recruitmentId": 1,
                "reviewName": "기타 리뷰 2",
                "satisfaction": "보통",
                "difficulty": "중",
                "wellDoneMemo": "기타 잘한 점 2",
                "shortcomingMemo": "기타 개선할 점 2"
            }
        ]
    }
    """;


        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/reviews/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewRequestJson)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("reviews/saveOrUpdateReviews",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("복기 노트")
                                        .description("리뷰 저장 또는 수정")
                                        .requestFields(
                                                fieldWithPath("document.id").description("서류 리뷰 ID"),
                                                fieldWithPath("document.recruitmentId").description("리크루트먼트 ID"),
                                                fieldWithPath("document.satisfaction").description("서류 만족도"),
                                                fieldWithPath("document.wellDonePoints").description("서류 잘한 점"),
                                                fieldWithPath("document.shortcomingPoints").description("서류 개선할 점"),
                                                fieldWithPath("document.wellDoneMemo").description("서류 잘한 점 설명"),
                                                fieldWithPath("document.shortcomingMemo").description("서류 개선할 점 설명"),
                                                fieldWithPath("interview.id").description("면접 리뷰 ID"),
                                                fieldWithPath("interview.recruitmentId").description("리크루트먼트 ID"),
                                                fieldWithPath("interview.satisfaction").description("면접 만족도"),
                                                fieldWithPath("interview.wellDonePoints").description("면접 잘한 점"),
                                                fieldWithPath("interview.shortcomingPoints").description("면접 개선할 점"),
                                                fieldWithPath("interview.wellDoneMemo").description("면접 잘한 점 설명"),
                                                fieldWithPath("interview.shortcomingMemo").description("면접 개선할 점 설명"),
                                                fieldWithPath("etc[].id").description("기타 리뷰 ID"),
                                                fieldWithPath("etc[].recruitmentId").description("리크루트먼트 ID"),
                                                fieldWithPath("etc[].reviewName").description("기타 리뷰 이름"),
                                                fieldWithPath("etc[].satisfaction").description("기타 리뷰 만족도"),  // 기타 리뷰 만족도 추가
                                                fieldWithPath("etc[].difficulty").description("기타 리뷰 난이도"),
                                                fieldWithPath("etc[].wellDoneMemo").description("기타 잘한 점 설명"),
                                                fieldWithPath("etc[].shortcomingMemo").description("기타 개선할 점 설명")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data").description("응답 데이터, 필요 시 반환 값")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }

}
