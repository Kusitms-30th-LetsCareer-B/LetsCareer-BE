package com.letscareer.recruitment;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.configuration.ControllerTestConfig;
import com.letscareer.recruitment.application.RecruitmentService;
import com.letscareer.recruitment.domain.StageStatusType;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import com.letscareer.recruitment.dto.response.FindAllRecruitmentsByTypeRes;
import com.letscareer.recruitment.dto.response.FindAllRecruitmentsRes;
import com.letscareer.recruitment.dto.response.FindRecruitmentRes;
import com.letscareer.recruitment.dto.response.GetRecruitmentsStatusRes;
import com.letscareer.recruitment.presentation.RecruitmentController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @DisplayName("채용 일정 단일 조회")
    public void testFindRecruitment() throws Exception {
        // given
        Long recruitmentId = 1L;

        FindRecruitmentRes.StageRes stageRes = FindRecruitmentRes.StageRes.builder()
                .stageId(1L)
                .stageName("서류 제출")
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 9, 30))
                .status(StageStatusType.PASSED)
                .isFinal(false)
                .build();

        FindRecruitmentRes recruitmentRes = FindRecruitmentRes.builder()
                .recruitmentId(recruitmentId)
                .companyName("Test Company")
                .isFavorite(true)
                .task("Software Development")
                .announcementUrl("http://example.com")
                .stages(List.of(stageRes))
                .build();

        Mockito.when(recruitmentService.findRecruitment(recruitmentId)).thenReturn(recruitmentRes);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/recruitments/{recruitmentId}", recruitmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("채용 일정을 조회하였습니다."))
                .andExpect(jsonPath("$.data.recruitmentId").value(recruitmentId))
                .andExpect(jsonPath("$.data.companyName").value("Test Company"))
                .andExpect(jsonPath("$.data.isFavorite").value(true))
                .andExpect(jsonPath("$.data.task").value("Software Development"))
                .andExpect(jsonPath("$.data.announcementUrl").value("http://example.com"))
                .andExpect(jsonPath("$.data.stages[0].stageId").value(1L))
                .andExpect(jsonPath("$.data.stages[0].stageName").value("서류 제출"))
                .andExpect(jsonPath("$.data.stages[0].startDate").value("2024-09-01"))
                .andExpect(jsonPath("$.data.stages[0].endDate").value("2024-09-30"))
                .andExpect(jsonPath("$.data.stages[0].status").value("PASSED"))
                .andExpect(jsonPath("$.data.stages[0].isFinal").value(false))
                .andDo(MockMvcRestDocumentationWrapper.document("recruitment/findRecruitment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("채용 일정")
                                        .description("채용 일정 단일 조회")
                                        .pathParameters(
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
                                                fieldWithPath("data.stages[].stageId").description("채용 단계 ID"),
                                                fieldWithPath("data.stages[].stageName").description("채용 단계 이름"),
                                                fieldWithPath("data.stages[].startDate").description("채용 단계 시작 날짜"),
                                                fieldWithPath("data.stages[].endDate").description("채용 단계 종료 날짜"),
                                                fieldWithPath("data.stages[].status").description("채용 단계 상태"),
                                                fieldWithPath("data.stages[].isFinal").description("최종 단계 여부")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
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

    @Test
    @DisplayName("유저의 총 채용일정 상태 개수 조회")
    public void testGetRecruitmentsStatus() throws Exception {
        // given
        Long userId = 1L;

        GetRecruitmentsStatusRes statusRes = GetRecruitmentsStatusRes.builder()
                .total(10)
                .progress(4)
                .passed(3)
                .failed(3)
                .build();

        Mockito.when(recruitmentService.getRecruitmentsStatus(userId)).thenReturn(statusRes);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/statuses")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("유저의 총 채용일정 상태 개수가 반환되었습니다."))
                .andExpect(jsonPath("$.data.total").value(10))
                .andExpect(jsonPath("$.data.progress").value(4))
                .andExpect(jsonPath("$.data.passed").value(3))
                .andExpect(jsonPath("$.data.failed").value(3))
                .andDo(MockMvcRestDocumentationWrapper.document("recruitment/getRecruitmentsStatus",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("채용 일정")
                                        .description("유저의 총 채용일정 상태 개수 조회")
                                        .queryParameters(
                                                parameterWithName("userId").description("유저의 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data.total").description("총 채용일정 수"),
                                                fieldWithPath("data.progress").description("진행 중인 채용일정 수"),
                                                fieldWithPath("data.passed").description("합격한 채용일정 수"),
                                                fieldWithPath("data.failed").description("불합격한 채용일정 수")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("유저의 채용일정 리스트 조회")
    public void testFindAllRecruitments() throws Exception {
        // given
        Long userId = 1L;

        FindAllRecruitmentsRes.RecruitmentInfo recruitmentInfo = FindAllRecruitmentsRes.RecruitmentInfo.builder()
                .recruitmentId(1L)
                .companyName("Test Company")
                .task("Software Development")
                .isFavorite(true)
                .isRemind(true)
                .announcementUrl("http://example.com")
                .stageName("면접")
                .status(StageStatusType.PROGRESS)
                .endDate(LocalDate.of(2024, 9, 30))
                .daysUntilEnd(5)
                .build();

        FindAllRecruitmentsRes findAllRecruitmentsRes = FindAllRecruitmentsRes.of(List.of(recruitmentInfo));

        Mockito.when(recruitmentService.findAllRecruitments(userId)).thenReturn(findAllRecruitmentsRes);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/recruitments")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("유저의 채용일정 리스트가 반환되었습니다."))
                .andExpect(jsonPath("$.data.recruitments[0].recruitmentId").value(1L))
                .andExpect(jsonPath("$.data.recruitments[0].companyName").value("Test Company"))
                .andExpect(jsonPath("$.data.recruitments[0].task").value("Software Development"))
                .andExpect(jsonPath("$.data.recruitments[0].isFavorite").value(true))
                .andExpect(jsonPath("$.data.recruitments[0].isRemind").value(true))
                .andExpect(jsonPath("$.data.recruitments[0].announcementUrl").value("http://example.com"))
                .andExpect(jsonPath("$.data.recruitments[0].stageName").value("면접"))
                .andExpect(jsonPath("$.data.recruitments[0].status").value("PROGRESS"))
                .andExpect(jsonPath("$.data.recruitments[0].endDate").value("2024-09-30"))
                .andExpect(jsonPath("$.data.recruitments[0].daysUntilEnd").value(5))
                .andDo(MockMvcRestDocumentationWrapper.document("recruitment/findAllRecruitments",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("채용 일정")
                                        .description("유저의 채용일정 리스트 조회")
                                        .queryParameters(
                                                parameterWithName("userId").description("유저의 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data.recruitments[].recruitmentId").description("채용 일정 ID"),
                                                fieldWithPath("data.recruitments[].companyName").description("회사 이름"),
                                                fieldWithPath("data.recruitments[].task").description("직무"),
                                                fieldWithPath("data.recruitments[].isFavorite").description("즐겨찾기 여부"),
                                                fieldWithPath("data.recruitments[].isRemind").description("리마인드 여부"),
                                                fieldWithPath("data.recruitments[].announcementUrl").description("공고 URL"),
                                                fieldWithPath("data.recruitments[].stageName").description("채용 단계 이름"),
                                                fieldWithPath("data.recruitments[].status").description("채용 단계 상태"),
                                                fieldWithPath("data.recruitments[].endDate").description("채용 단계 종료 날짜"),
                                                fieldWithPath("data.recruitments[].daysUntilEnd").description("마감일까지 남은 일수")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("유저의 타입별 채용일정 리스트 조회")
    public void testFindRecruitmentsByType() throws Exception {
        // given
        Long userId = 1L;
        String type = "progress";

        FindAllRecruitmentsByTypeRes.RecruitmentInfo recruitmentInfo = FindAllRecruitmentsByTypeRes.RecruitmentInfo.builder()
                .recruitmentId(1L)
                .companyName("Test Company")
                .task("Software Development")
                .isFavorite(true)
                .stageName("면접")
                .status(StageStatusType.PROGRESS)
                .endDate(LocalDate.of(2024, 9, 30))
                .daysUntilEnd(5L)
                .build();

        FindAllRecruitmentsByTypeRes findAllRecruitmentsByTypeRes = FindAllRecruitmentsByTypeRes.of(List.of(recruitmentInfo));

        Mockito.when(recruitmentService.findRecruitmentsByType(type, userId)).thenReturn(findAllRecruitmentsByTypeRes);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/recruitments/status")
                .param("type", type)
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("유저 채용일정 리스트가 반환되었습니다."))
                .andExpect(jsonPath("$.data.recruitments[0].recruitmentId").value(1L))
                .andExpect(jsonPath("$.data.recruitments[0].companyName").value("Test Company"))
                .andExpect(jsonPath("$.data.recruitments[0].task").value("Software Development"))
                .andExpect(jsonPath("$.data.recruitments[0].isFavorite").value(true))
                .andExpect(jsonPath("$.data.recruitments[0].stageName").value("면접"))
                .andExpect(jsonPath("$.data.recruitments[0].status").value("PROGRESS"))
                .andExpect(jsonPath("$.data.recruitments[0].endDate").value("2024-09-30"))
                .andExpect(jsonPath("$.data.recruitments[0].daysUntilEnd").value(5))
                .andDo(MockMvcRestDocumentationWrapper.document("recruitment/findRecruitmentsByType",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("채용 일정")
                                        .description("유저의 타입별 채용일정 리스트 조회")
                                        .queryParameters(
                                                parameterWithName("type").description("조회할 채용 일정의 타입 (progress 또는 consequence)"),
                                                parameterWithName("userId").description("유저의 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data.recruitments[].recruitmentId").description("채용 일정 ID"),
                                                fieldWithPath("data.recruitments[].companyName").description("회사 이름"),
                                                fieldWithPath("data.recruitments[].task").description("직무"),
                                                fieldWithPath("data.recruitments[].isFavorite").description("즐겨찾기 여부"),
                                                fieldWithPath("data.recruitments[].stageName").description("채용 단계 이름"),
                                                fieldWithPath("data.recruitments[].status").description("채용 단계 상태"),
                                                fieldWithPath("data.recruitments[].endDate").description("채용 단계 종료 날짜"),
                                                fieldWithPath("data.recruitments[].daysUntilEnd").description("마감일까지 남은 일수")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }

}