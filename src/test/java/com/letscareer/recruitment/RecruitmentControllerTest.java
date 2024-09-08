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
		Mockito.doNothing().when(recruitmentService).enrollRecruitment(anyLong(), any(EnrollRecruitmentReq.class));

		String jsonRequest = """
			{
			    "companyName": "네이버",
			    "isFavorite": true,
			    "task": "백엔드 개발",
			    "isRemind": true,
			    "announcementUrl": "http://naver.com",
			    "stageStartDate": "2024-09-01",
			    "stageEndDate": "2024-09-30"
			}
			""";

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/recruitments")
			.param("userId", "1")
			.content(jsonRequest)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("recruitment/enrollRecruitment",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("채용 일정")
						.description("채용 일정 등록")
						.queryParameters(parameterWithName("userId").description("채용 일정을 등록할 유저의 ID"))
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
			.companyName("카카오")
			.isFavorite(true)
			.task("프론트엔드 개발")
			.announcementUrl("http://kakao.com")
			.stageName(null)
			.status(null)
			.daysUntilEnd(0)
			.stages(List.of(stageRes))
			.build();

		Mockito.when(recruitmentService.findRecruitment(recruitmentId)).thenReturn(recruitmentRes);

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/recruitments/{recruitmentId}", recruitmentId)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("채용 일정을 조회하였습니다."))
			.andExpect(jsonPath("$.data.recruitmentId").value(recruitmentId))
			.andExpect(jsonPath("$.data.companyName").value("카카오"))
			.andExpect(jsonPath("$.data.isFavorite").value(true))
			.andExpect(jsonPath("$.data.task").value("프론트엔드 개발"))
			.andExpect(jsonPath("$.data.announcementUrl").value("http://kakao.com"))
			.andExpect(jsonPath("$.data.stageName").isEmpty())
			.andExpect(jsonPath("$.data.status").isEmpty())
			.andExpect(jsonPath("$.data.daysUntilEnd").value(0))
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
							fieldWithPath("data.stageName").description("현재 단계 이름").optional(),
							fieldWithPath("data.status").description("현재 단계 상태").optional(),
							fieldWithPath("data.daysUntilEnd").description("마감일까지 남은 일수"),
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
		Mockito.doNothing().when(recruitmentService).deleteRecruitment(anyLong());

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/recruitments")
			.queryParam("recruitmentId", "1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

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
		Long userId = 1L;

		GetRecruitmentsStatusRes statusRes = GetRecruitmentsStatusRes.builder()
			.total(10)
			.document(4)
			.interview(3)
			.other(3)
			.build();

		Mockito.when(recruitmentService.getRecruitmentsStatus(userId)).thenReturn(statusRes);

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/statuses")
			.param("userId", userId.toString())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("유저의 총 채용일정 상태 개수가 반환되었습니다."))
			.andExpect(jsonPath("$.data.total").value(10))
			.andExpect(jsonPath("$.data.document").value(4))
			.andExpect(jsonPath("$.data.interview").value(3))
			.andExpect(jsonPath("$.data.other").value(3))
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
							fieldWithPath("data.document").description("일정의 현재 전형의 상태:서류"),
							fieldWithPath("data.interview").description("일정의 현재 전형의 상태:면접"),
							fieldWithPath("data.other").description("일정의 현재 전형의 상태:기타")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("유저의 채용일정 리스트 조회")
	public void testFindAllRecruitments() throws Exception {
		Long userId = 1L;
		Long page = 6L;
		FindAllRecruitmentsRes.RecruitmentInfo recruitmentInfo = FindAllRecruitmentsRes.RecruitmentInfo.builder()
			.recruitmentId(1L)
			.companyName("네이버")
			.task("백엔드 개발")
			.isFavorite(true)
			.isRemind(true)
			.announcementUrl("http://naver.com")
			.stageName("면접")
			.status(StageStatusType.PROGRESS)
			.endDate(LocalDate.of(2024, 9, 30))
			.daysUntilEnd(5)
			.build();

		FindAllRecruitmentsRes findAllRecruitmentsRes = FindAllRecruitmentsRes.of(1L, 1L, 1L, List.of(recruitmentInfo));

		Mockito.when(recruitmentService.findAllRecruitments(userId, page)).thenReturn(findAllRecruitmentsRes);

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/recruitments")
			.param("userId", userId.toString())
			.param("page", page.toString())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("유저의 채용일정 리스트가 반환되었습니다."))
			.andExpect(jsonPath("$.data.recruitments[0].recruitmentId").value(1L))
			.andExpect(jsonPath("$.data.recruitments[0].companyName").value("네이버"))
			.andExpect(jsonPath("$.data.recruitments[0].task").value("백엔드 개발"))
			.andExpect(jsonPath("$.data.recruitments[0].isFavorite").value(true))
			.andExpect(jsonPath("$.data.recruitments[0].isRemind").value(true))
			.andExpect(jsonPath("$.data.recruitments[0].announcementUrl").value("http://naver.com"))
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
							parameterWithName("userId").description("유저의 ID"),
							parameterWithName("page").description("페이지 번호")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data.totalPages").description("총 페이지 수"),
							fieldWithPath("data.currentPage").description("현재 페이지 번호"),
							fieldWithPath("data.totalElementsCount").description("총 채용 일정 수"),
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
	@DisplayName("특정 기업의 관심 여부 변경")
	public void testModifyRecruitmentFavorite() throws Exception {
		Mockito.doNothing().when(recruitmentService).modifyRecruitmentFavorite(anyLong());

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/recruitments/{recruitmentId}/favorite", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("recruitment/modifyRecruitmentFavorite",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("채용 일정")
						.description("특정 기업의 관심 여부 변경")
						.pathParameters(
							parameterWithName("recruitmentId").description("관심 여부를 변경할 채용 일정의 ID")
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
	@DisplayName("유저의 타입별 채용일정 리스트 조회")
	public void testFindRecruitmentsByType() throws Exception {
		Long userId = 1L;
		String type = "progress";
		Long page = 1L;

		FindAllRecruitmentsByTypeRes.RecruitmentInfo recruitmentInfo = FindAllRecruitmentsByTypeRes.RecruitmentInfo.builder()
			.recruitmentId(1L)
			.companyName("네이버")
			.task("백엔드 개발")
			.isFavorite(true)
			.stageName("면접")
			.status(StageStatusType.PROGRESS)
			.endDate(LocalDate.of(2024, 9, 30))
			.daysUntilEnd(5L)
			.isFinal(true)
			.build();

		FindAllRecruitmentsByTypeRes findAllRecruitmentsByTypeRes = FindAllRecruitmentsByTypeRes.of(1L, 1L, 1L, List.of(recruitmentInfo));

		Mockito.when(recruitmentService.findRecruitmentsByType(type, userId, page)).thenReturn(findAllRecruitmentsByTypeRes);

		// when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/recruitments/status")
			.param("type", type)
			.param("userId", userId.toString())
			.param("page", page.toString())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("유저 채용일정 리스트가 반환되었습니다."))
			.andExpect(jsonPath("$.data.recruitments[0].recruitmentId").value(1L))
			.andExpect(jsonPath("$.data.recruitments[0].companyName").value("네이버"))
			.andExpect(jsonPath("$.data.recruitments[0].task").value("백엔드 개발"))
			.andExpect(jsonPath("$.data.recruitments[0].isFavorite").value(true))
			.andExpect(jsonPath("$.data.recruitments[0].stageName").value("면접"))
			.andExpect(jsonPath("$.data.recruitments[0].status").value("PROGRESS"))
			.andExpect(jsonPath("$.data.recruitments[0].endDate").value("2024-09-30"))
			.andExpect(jsonPath("$.data.recruitments[0].daysUntilEnd").value(5))
			.andExpect(jsonPath("$.data.recruitments[0].isFinal").value(true))
			.andDo(MockMvcRestDocumentationWrapper.document("recruitment/findRecruitmentsByType",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("채용 일정")
						.description("유저의 타입별 채용일정 리스트 조회")
						.queryParameters(
							parameterWithName("type").description("조회할 채용 일정의 타입 (progress 또는 consequence)"),
							parameterWithName("userId").description("유저의 ID"),
							parameterWithName("page").description("페이지 번호")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data.totalPages").description("총 페이지 수"),
							fieldWithPath("data.currentPage").description("현재 페이지 번호"),
							fieldWithPath("data.totalElementsCount").description("총 채용 일정 수"),
							fieldWithPath("data.recruitments[].recruitmentId").description("채용 일정 ID"),
							fieldWithPath("data.recruitments[].companyName").description("회사 이름"),
							fieldWithPath("data.recruitments[].task").description("직무"),
							fieldWithPath("data.recruitments[].isFavorite").description("즐겨찾기 여부"),
							fieldWithPath("data.recruitments[].stageName").description("채용 단계 이름"),
							fieldWithPath("data.recruitments[].status").description("채용 단계 상태"),
							fieldWithPath("data.recruitments[].endDate").description("채용 단계 종료 날짜"),
							fieldWithPath("data.recruitments[].daysUntilEnd").description("마감일까지 남은 일수"),
							fieldWithPath("data.recruitments[].isFinal").description("현재전형이 최종인지 유무")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}
}
