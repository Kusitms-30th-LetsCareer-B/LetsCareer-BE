package com.letscareer.recruitment;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.configuration.ControllerTestConfig;
import com.letscareer.recruitment.application.RecruitmentService;
import com.letscareer.recruitment.application.StageService;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.StageStatusType;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.recruitment.domain.repository.StageRepository;
import com.letscareer.recruitment.dto.request.CreateStageReq;
import com.letscareer.recruitment.dto.request.ModifyStageReq;
import com.letscareer.recruitment.dto.response.FindStageRes;
import com.letscareer.recruitment.presentation.StageController;
import com.letscareer.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Optional;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StageController.class)
public class StageControllerTest extends ControllerTestConfig {
	@MockBean
	private StageService stageService;

	@MockBean
	private StageRepository stageRepository;

	@MockBean
	private RecruitmentRepository recruitmentRepository;

	// 필요한 RecruitmentService를 Mocking
	@MockBean
	private RecruitmentService recruitmentService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("채용 전형 추가")
	public void testCreateStage() throws Exception {
		Long recruitmentId = 1L;

		String jsonRequest = """
			{
			    "stageName": "면접",
			    "endDate": "2024-09-30",
			    "isFinal": true
			}
			""";

		// Mock the service call to do nothing when createStage is called
		Mockito.doNothing().when(stageService).createStage(anyLong(), any(CreateStageReq.class));

		// when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/stages")
			.param("recruitmentId", recruitmentId.toString())
			.content(jsonRequest)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("채용전형을 추가하였습니다."))
			.andDo(MockMvcRestDocumentationWrapper.document("stage/createStage",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("채용 전형")
						.description("채용 전형 추가")
						.queryParameters(
							parameterWithName("recruitmentId").description("채용 일정의 ID")
						)
						.requestFields(
							fieldWithPath("stageName").description("전형 이름"),
							fieldWithPath("endDate").description("전형 종료 날짜"),
							fieldWithPath("isFinal").description("최종 전형 여부")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("null로 반환되는 데이터")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("특정 채용 전형 단일 조회")
	public void testFindStage() throws Exception {
		Long stageId = 1L;

		FindStageRes stageRes = FindStageRes.builder()
			.stageName("면접")
			.startDate(LocalDate.of(2024, 9, 1))
			.endDate(LocalDate.of(2024, 9, 30))
			.status(StageStatusType.PROGRESS)
			.isFinal(true)
			.build();

		Mockito.when(stageService.findStage(stageId)).thenReturn(stageRes);

		// when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/stages")
			.param("stageId", stageId.toString())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("해당 채용전형을 조회하였습니다."))
			.andExpect(jsonPath("$.data.stageName").value("면접"))
			.andExpect(jsonPath("$.data.startDate").value("2024-09-01"))
			.andExpect(jsonPath("$.data.endDate").value("2024-09-30"))
			.andExpect(jsonPath("$.data.status").value("PROGRESS"))
			.andExpect(jsonPath("$.data.isFinal").value(true))
			.andDo(MockMvcRestDocumentationWrapper.document("stage/findStage",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("채용 전형")
						.description("특정 채용 전형 단일 조회")
						.queryParameters(
							parameterWithName("stageId").description("채용 전형의 ID")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data.stageName").description("전형 이름"),
							fieldWithPath("data.startDate").description("전형 시작 날짜"),
							fieldWithPath("data.endDate").description("전형 종료 날짜"),
							fieldWithPath("data.status").description("전형 상태"),
							fieldWithPath("data.isFinal").description("최종 전형 여부")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}


	@Test
	@DisplayName("채용 전형 수정")
	public void testModifyStage() throws Exception {
		Long stageId = 1L;

		String jsonRequest = """
			{
			    "stageName": "면접",
			    "endDate": "2024-09-30",
			    "status": "PROGRESS",
			    "isFinal": "true"
			}
			""";

		Mockito.doNothing().when(stageService).modifyStage(anyLong(), any(ModifyStageReq.class));

		// when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/stages")
			.param("stageId", stageId.toString())
			.content(jsonRequest)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("stage/modifyStage",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("채용 전형")
						.description("채용 전형 수정")
						.queryParameters(
							parameterWithName("stageId").description("채용 전형의 ID")
						)
						.requestFields(
							fieldWithPath("stageName").description("전형 이름"),
							fieldWithPath("endDate").description("전형 종료 날짜"),
							fieldWithPath("status").description("채용 전형 상태"),
							fieldWithPath("isFinal").description("최종 전형 유무")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("null로 반환되는 데이터")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("채용 전형 삭제")
	public void testDeleteStage() throws Exception {

		// given
		Mockito.doNothing().when(recruitmentService).deleteRecruitment(anyLong());

		// when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/stages")
			.queryParam("stageId", "1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

//        Long stageId = 1L;
//        Long recruitmentId = 1L;
//        Long userId = 1L;
//
//        // Mock User entity
//        User user = User.builder()
//                .id(userId)
//                .name("Test User")
//                .email("testuser@example.com")
//                .build();
//
//        // Mock Recruitment entity
//        Recruitment recruitment = Recruitment.builder()
//                .id(recruitmentId)
//                .user(user)
//                .companyName("Test Company")
//                .task("Software Development")
//                .isFavorite(false)
//                .isRemind(true)
//                .build();
//
//        // Mock Stage entity
//        Stage stage = Stage.builder()
//                .id(stageId)
//                .recruitment(recruitment)
//                .stageName("면접")
//                .endDate(LocalDate.of(2024, 9, 30))
//                .status(StageStatusType.PROGRESS)
//                .isFinal(true)
//                .build();
//
//        // Mock the repository call to return the recruitment and stage when findById is called
//        Mockito.when(recruitmentRepository.findById(recruitmentId)).thenReturn(Optional.of(recruitment));
//        Mockito.when(stageRepository.findById(stageId)).thenReturn(Optional.of(stage));
//
//        // Mock the service call to do nothing when deleteStage is called
//        Mockito.doNothing().when(stageService).deleteStage(stageId);
//
//        // when
//        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/stages")
//                .param("stageId", stageId.toString())
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("stage/delete",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("채용 전형")
						.description("채용 전형 삭제")
						.queryParameters(
							parameterWithName("stageId").description("채용 전형의 ID")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("null로 반환되는 데이터")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

}

