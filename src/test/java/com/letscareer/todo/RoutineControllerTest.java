package com.letscareer.todo;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.configuration.ControllerTestConfig;
import com.letscareer.todo.application.RoutineService;
import com.letscareer.todo.dto.request.RoutineReq;
import com.letscareer.todo.dto.response.RoutineRes;
import com.letscareer.todo.presentation.RoutineController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoutineController.class)
public class RoutineControllerTest extends ControllerTestConfig {

	@MockBean
	private RoutineService routineService;

	@Test
	@DisplayName("루틴 생성")
	public void testCreateRoutine() throws Exception {
		Mockito.doNothing().when(routineService).createRoutine(anyLong(), anyLong(), any(RoutineReq.class));

		String jsonRequest = """
            {
                "content": "서류 작성 루틴",
                "startDate": "2024-09-01",
                "endDate": "2024-09-07"
            }
            """;

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/routines")
			.param("userId", "1")
			.param("recruitmentId", "1")
			.content(jsonRequest)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("routine/createRoutine",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("루틴 생성")
						.queryParameters(
							parameterWithName("userId").description("유저 ID"),
							parameterWithName("recruitmentId").description("채용 일정 ID")
						)
						.requestFields(
							fieldWithPath("content").description("루틴 내용"),
							fieldWithPath("startDate").description("루틴 시작 날짜"),
							fieldWithPath("endDate").description("루틴 종료 날짜")
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
	@DisplayName("루틴 조회")
	public void testGetRoutine() throws Exception {
		RoutineRes routineRes = RoutineRes.builder()
			.content("서류 작성 루틴")
			.startDate(LocalDate.of(2024, 9, 1))
			.endDate(LocalDate.of(2024, 9, 7))
			.build();

		Mockito.when(routineService.getRoutine(anyLong())).thenReturn(routineRes);

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/routines/{routineId}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("해당 루틴을 조회하였습니다."))
			.andExpect(jsonPath("$.data.content").value("서류 작성 루틴"))
			.andExpect(jsonPath("$.data.startDate").value("2024-09-01"))
			.andExpect(jsonPath("$.data.endDate").value("2024-09-07"))
			.andDo(MockMvcRestDocumentationWrapper.document("routine/getRoutine",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("루틴 조회")
						.pathParameters(parameterWithName("routineId").description("조회할 루틴 ID"))
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data.content").description("루틴 내용"),
							fieldWithPath("data.startDate").description("루틴 시작 날짜"),
							fieldWithPath("data.endDate").description("루틴 종료 날짜")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("루틴 삭제")
	public void testDeleteRoutine() throws Exception {
		Mockito.doNothing().when(routineService).deleteRoutine(anyLong());

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/routines/{routineId}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("routine/deleteRoutine",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("루틴 삭제")
						.pathParameters(parameterWithName("routineId").description("삭제할 루틴 ID"))
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
	@DisplayName("루틴 수정")
	public void testModifyRoutine() throws Exception {
		Mockito.doNothing().when(routineService).modifyRoutine(anyLong(), any(RoutineReq.class));

		String jsonRequest = """
            {
                "content": "서류 검토 루틴",
                "startDate": "2024-09-02",
                "endDate": "2024-09-08"
            }
            """;

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/routines/{routineId}", 1L)
			.content(jsonRequest)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("routine/modifyRoutine",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("루틴 수정")
						.pathParameters(parameterWithName("routineId").description("수정할 루틴 ID"))
						.requestFields(
							fieldWithPath("content").description("수정할 루틴 내용"),
							fieldWithPath("startDate").description("수정할 루틴 시작 날짜"),
							fieldWithPath("endDate").description("수정할 루틴 종료 날짜")
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
