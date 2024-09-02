package com.letscareer.recruitment;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.configuration.ControllerTestConfig;
import com.letscareer.recruitment.application.RecruitmentService;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import com.letscareer.recruitment.dto.response.GetRecruitmentRes;
import com.letscareer.recruitment.presentation.RecruitmentController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecruitmentController.class)
public class RecruitmentControllerTest extends ControllerTestConfig {

    @MockBean
    private RecruitmentService recruitmentService;

    @Test
    @DisplayName("채용 일정 등록")
    public void testEnrollRecruitment() throws Exception {
        // given
        Mockito.doNothing().when(recruitmentService).enrollRecruitment(anyLong(), any(EnrollRecruitmentReq.class));

        String jsonRequest = """
                {
                    "companyName": "Test Company",
                    "isFavorite": true,
                    "task": "Software Development",
                    "isRemind": true,
                    "announcementUrl": "http://example.com",
                    "stageStartDate": "2024-09-01",
                    "stageEndDate": "2024-09-30"
                }
                """;

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/recruitments")
                .param("userId", "1")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("recruitment/enrollRecruitment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("채용 일정")
                                        .description("채용 일정 등록")
                                        .queryParameters(
                                                parameterWithName("userId").description("채용 일정을 등록할 유저의 ID")
                                        )
                                        .requestFields(
                                                fieldWithPath("companyName").description("회사 이름"),
                                                fieldWithPath("isFavorite").description("즐겨찾기 여부"),
                                                fieldWithPath("task").description("직무"),
                                                fieldWithPath("isRemind").description("리마인드 여부"),
                                                fieldWithPath("announcementUrl").description("공고 URL"),
                                                fieldWithPath("stageStartDate").description("채용 단계 시작 날짜"),
                                                fieldWithPath("stageEndDate").description("채용 단계 종료 날짜")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data").description("응답 데이터")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("채용 일정 조회")
    public void testFindRecruitment() throws Exception {
        // given
        GetRecruitmentRes getRecruitmentRes = GetRecruitmentRes.builder()
                .recruitmentId(1L)
                .companyName("Test Company")
                .isFavorite(true)
                .task("Software Development")
                .announcementUrl("http://example.com")
                .build();

        Mockito.when(recruitmentService.findRecruitment(anyLong())).thenReturn(getRecruitmentRes);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/recruitments")
                .param("recruitmentId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("recruitment/findRecruitment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("채용 일정")
                                        .description("채용 일정 조회")
                                        .queryParameters(
                                                parameterWithName("recruitmentId").description("조회할 채용 일정의 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data.recruitmentId").description("채용 일정 ID"),
                                                fieldWithPath("data.companyName").description("회사 이름"),
                                                fieldWithPath("data.isFavorite").description("즐겨찾기 여부"),
                                                fieldWithPath("data.task").description("직무"),
                                                fieldWithPath("data.announcementUrl").description("공고 URL"),
                                                fieldWithPath("data.stages").description("채용 단계 목록")
                                        )
                                        .responseSchema(Schema.schema("GetRecruitmentRes"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("채용 일정 삭제")
    public void testDeleteRecruitment() throws Exception {
        // given
        Mockito.doNothing().when(recruitmentService).deleteRecruitment(anyLong());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/recruitments")
                .queryParam("recruitmentId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("recruitment/deleteRecruitment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("채용 일정")
                                        .description("채용 일정 삭제")
                                        .queryParameters(
                                                parameterWithName("recruitmentId").description("삭제할 채용 일정의 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data").description("응답 데이터")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }
}