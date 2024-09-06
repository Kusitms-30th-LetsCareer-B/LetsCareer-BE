package com.letscareer.todo;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.configuration.ControllerTestConfig;
import com.letscareer.todo.application.TodoService;
import com.letscareer.todo.dto.request.CreateTodoReq;
import com.letscareer.todo.dto.request.ModifyTodoReq;
import com.letscareer.todo.dto.response.CompanyTodosRes;
import com.letscareer.todo.dto.response.GroupedByCompanyRes;
import com.letscareer.todo.dto.response.TodoRes;
import com.letscareer.todo.presentation.TodoController;
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

@WebMvcTest(TodoController.class)
public class TodoControllerTest extends ControllerTestConfig {

	@MockBean
	private TodoService todoService;

	@Test
	@DisplayName("투두 추가")
	public void testCreateTodo() throws Exception {
		Mockito.doNothing().when(todoService).createTodo(anyLong(), anyLong(), any(CreateTodoReq.class));

		String jsonRequest = """
            {
                "date": "2024-09-01",
                "content": "서류 제출 준비"
            }
            """;

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/todos")
			.param("userId", "1")
			.param("recruitmentId", "1")
			.content(jsonRequest)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("todo/createTodo",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("투두 추가")
						.queryParameters(
							parameterWithName("userId").description("유저 ID"),
							parameterWithName("recruitmentId").description("채용 일정 ID")
						)
						.requestFields(
							fieldWithPath("date").description("투두 날짜"),
							fieldWithPath("content").description("투두 내용")
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
	@DisplayName("전체 기업의 해당 날짜의 투두 리스트 반환")
	public void testGetTodosGroupedByCompanyName() throws Exception {
		GroupedByCompanyRes groupedByCompanyRes = GroupedByCompanyRes.builder()
			.companyName("카카오")
			.todos(List.of(TodoRes.builder()
				.todoId(1L)
				.content("서류 제출 준비")
				.isCompleted(false)
				.date(LocalDate.of(2024, 9, 1))
				.recruitmentId(1L)
				.build()))
			.build();

		Mockito.when(todoService.getTodosGroupedByCompanyName(anyLong(), any(LocalDate.class)))
			.thenReturn(List.of(groupedByCompanyRes));

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/todos/groupedByCompany")
			.param("userId", "1")
			.param("date", "2024-09-01")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("전체 투두 목록을 조회하였습니다."))
			.andExpect(jsonPath("$.data[0].companyName").value("카카오"))
			.andExpect(jsonPath("$.data[0].todos[0].todoId").value(1L))
			.andExpect(jsonPath("$.data[0].todos[0].content").value("서류 제출 준비"))
			.andExpect(jsonPath("$.data[0].todos[0].isCompleted").value(false))
			.andDo(MockMvcRestDocumentationWrapper.document("todo/getTodosGroupedByCompanyName",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("전체 기업의 해당 날짜의 투두 리스트 반환")
						.queryParameters(
							parameterWithName("userId").description("유저 ID"),
							parameterWithName("date").description("투두 날짜")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data[].companyName").description("회사 이름"),
							fieldWithPath("data[].todos[].todoId").description("투두 ID"),
							fieldWithPath("data[].todos[].content").description("투두 내용"),
							fieldWithPath("data[].todos[].isCompleted").description("투두 완료 여부"),
							fieldWithPath("data[].todos[].date").description("투두 날짜"),
							fieldWithPath("data[].todos[].recruitmentId").description("채용 일정 ID")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("특정 기업의 해당 날짜의 투두 리스트 조회")
	public void testGetTodosByCompanyName() throws Exception {
		CompanyTodosRes companyTodosRes = CompanyTodosRes.builder()
			.todos(List.of(TodoRes.builder()
				.todoId(1L)
				.content("서류 제출 준비")
				.isCompleted(false)
				.date(LocalDate.of(2024, 9, 1))
				.recruitmentId(1L)
				.build()))
			.build();

		Mockito.when(todoService.getTodosByCompanyName(anyLong(), any(LocalDate.class)))
			.thenReturn(companyTodosRes);

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/todos")
			.param("recruitmentId", "1")
			.param("date", "2024-09-01")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("전체 투두 목록을 조회하였습니다."))
			.andExpect(jsonPath("$.data.todos[0].todoId").value(1L))
			.andExpect(jsonPath("$.data.todos[0].content").value("서류 제출 준비"))
			.andExpect(jsonPath("$.data.todos[0].isCompleted").value(false))
			.andDo(MockMvcRestDocumentationWrapper.document("todo/getTodosByCompanyName",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("특정 기업의 해당 날짜의 투두 리스트 조회")
						.queryParameters(
							parameterWithName("recruitmentId").description("채용 일정 ID"),
							parameterWithName("date").description("투두 날짜")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data.todos[].todoId").description("투두 ID"),
							fieldWithPath("data.todos[].content").description("투두 내용"),
							fieldWithPath("data.todos[].isCompleted").description("투두 완료 여부"),
							fieldWithPath("data.todos[].date").description("투두 날짜"),
							fieldWithPath("data.todos[].recruitmentId").description("채용 일정 ID")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("투두 수정")
	public void testModifyTodo() throws Exception {
		Mockito.doNothing().when(todoService).modifyTodo(anyLong(), any(ModifyTodoReq.class));

		String jsonRequest = """
            {
                "content": "서류 제출 완료",
                "date": "2024-09-02"
            }
            """;

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/todos/{todoId}", 1L)
			.content(jsonRequest)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("todo/modifyTodo",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("투두 수정")
						.pathParameters(parameterWithName("todoId").description("수정할 투두의 ID"))
						.requestFields(
							fieldWithPath("content").description("수정할 투두 내용"),
							fieldWithPath("date").description("수정할 투두 날짜")
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
	@DisplayName("투두 완료 여부 수정")
	public void testModifyTodoIsCompleted() throws Exception {
		Mockito.doNothing().when(todoService).modifyTodoIsCompleted(anyLong());

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/todos/{todoId}/check", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("todo/modifyTodoIsCompleted",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("투두 완료 여부 수정")
						.pathParameters(parameterWithName("todoId").description("수정할 투두의 ID"))
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
	@DisplayName("투두 삭제")
	public void testDeleteTodo() throws Exception {
		Mockito.doNothing().when(todoService).deleteTodo(anyLong());

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/todos/{todoId}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("todo/deleteTodo",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("투두 삭제")
						.pathParameters(parameterWithName("todoId").description("삭제할 투두의 ID"))
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
	@DisplayName("투두 하루 미루기")
	public void testDelayTodo() throws Exception {
		Mockito.doNothing().when(todoService).delayTodo(anyLong());

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/todos/{todoId}/delay", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("todo/delayTodo",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("투두")
						.description("투두 하루 미루기")
						.pathParameters(parameterWithName("todoId").description("일정을 미룰 투두의 ID"))
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
