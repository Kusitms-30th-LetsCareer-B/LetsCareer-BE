package com.letscareer.review;

import com.letscareer.configuration.ControllerTestConfig;
import com.letscareer.review.application.InterviewService;
import com.letscareer.review.dto.request.ReactionReq;
import com.letscareer.review.dto.response.GetAdditionalInterviewRes;
import com.letscareer.review.dto.response.GetInterviewRes;
import com.letscareer.review.presentation.InterviewController;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
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

@WebMvcTest(InterviewController.class)
public class InterviewControllerTest extends ControllerTestConfig {

	@MockBean
	private InterviewService interviewService;

	@Test
	@DisplayName("특정 기업의 면접 질문 작성 및 수정")
	public void testCreateOrModifyInterview() throws Exception {
		Mockito.doNothing().when(interviewService).createOrModifyInterview(anyLong(), anyList());

		String interviewRequestJson = """
            [
                {
                    "order": 1,
                    "question": "최근 해결한 기술적 어려움은 무엇인가요?",
                    "answer": "비동기 처리와 관련된 문제를 해결했습니다."
                },
                {
                    "order": 2,
                    "question": "팀에서 협업한 경험을 설명해 주세요.",
                    "answer": "팀원들과 코드 리뷰를 통해 최종 결과물을 도출했습니다."
                }
            ]
        """;

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/interviews")
			.queryParam("recruitmentId", "1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(interviewRequestJson)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("interview/createOrModifyInterview",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("복기 노트")
						.description("특정 기업의 면접 질문 작성 및 수정")
						.queryParameters(parameterWithName("recruitmentId").description("채용 일정 ID"))
						.requestFields(
							fieldWithPath("[].order").description("질문 순서"),
							fieldWithPath("[].question").description("면접 질문"),
							fieldWithPath("[].answer").description("면접 답변")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("응답 데이터, null")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("특정 기업의 면접 질문 리스트 조회")
	public void testGetInterviews() throws Exception {
		List<GetInterviewRes> response = List.of(
			GetInterviewRes.builder()
				.interviewId(1L)
				.order(1)
				.question("최근 해결한 기술적 어려움은 무엇인가요?")
				.answer("비동기 처리와 관련된 문제를 해결했습니다.")
				.type(null) // 이 부분을 포함
				.build(),
			GetInterviewRes.builder()
				.interviewId(2L)
				.order(2)
				.question("팀에서 협업한 경험을 설명해 주세요.")
				.answer("팀원들과 코드 리뷰를 통해 최종 결과물을 도출했습니다.")
				.type(null) // 이 부분을 포함
				.build()
		);

		Mockito.when(interviewService.getInterviews(anyLong())).thenReturn(response);

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/interviews")
			.queryParam("recruitmentId", "1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("interview/getInterviews",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("복기 노트")
						.description("특정 기업의 면접 질문 리스트 조회")
						.queryParameters(parameterWithName("recruitmentId").description("채용 일정 ID"))
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data.[].interviewId").description("면접 질문 ID"),
							fieldWithPath("data.[].order").description("질문 순서"),
							fieldWithPath("data.[].question").description("면접 질문"),
							fieldWithPath("data.[].answer").description("면접 답변"),
							fieldWithPath("data.[].type").description("면접 질문에 대한 반응 상태 (잘했어요, 아쉬워요)").optional() // 추가
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}


	@Test
	@DisplayName("특정 면접 질문 삭제")
	public void testDeleteInterview() throws Exception {
		Mockito.doNothing().when(interviewService).deleteInterview(anyLong(), anyLong());

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/interviews/{interviewId}", 1L)
			.queryParam("recruitmentId", "1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("interview/deleteInterview",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("복기 노트")
						.description("특정 면접 질문 삭제")
						.queryParameters(parameterWithName("recruitmentId").description("채용 일정 ID"))
						.pathParameters(parameterWithName("interviewId").description("삭제할 면접 질문 ID"))
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("응답 데이터, null")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("면접 질문에 대한 반응 수정")
	public void testModifyReaction() throws Exception {
		String reactionRequestJson = """
            {
                "reaction": "아쉬워요"
            }
        """;

		Mockito.doNothing().when(interviewService).modifyReaction(anyLong(), any(ReactionReq.class));

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/interviews/{interviewId}/reaction", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(reactionRequestJson)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("interview/modifyReaction",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("복기 노트")
						.description("면접 질문에 대한 반응 수정")
						.pathParameters(parameterWithName("interviewId").description("면접 질문 ID"))
						.requestFields(fieldWithPath("reaction").description("면접 질문에 대한 반응 (잘했어요, 아쉬워요)"))
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("응답 데이터, null")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("한번 더 보면 좋을 면접 질문 조회")
	public void testGetAdditionalInterviews() throws Exception {
		List<GetAdditionalInterviewRes> response = List.of(
			GetAdditionalInterviewRes.builder()
				.interviewId(1L)
				.question("최근 해결한 기술적 어려움은 무엇인가요?")
				.build()
		);

		Mockito.when(interviewService.getAdditionalInterviews(anyLong())).thenReturn(response);

		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/interviews/additional")
			.queryParam("recruitmentId", "1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("interview/getAdditionalInterviews",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("복기 노트")
						.description("한번 더 보면 좋을 면접 질문 조회")
						.queryParameters(parameterWithName("recruitmentId").description("채용 일정 ID"))
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data.[].interviewId").description("면접 질문 ID"),
							fieldWithPath("data.[].question").description("면접 질문")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}
}
