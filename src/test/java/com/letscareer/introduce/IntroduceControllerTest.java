package com.letscareer.introduce;

import com.letscareer.configuration.ControllerTestConfig;
import com.letscareer.introduce.application.IntroduceService;
import com.letscareer.introduce.domain.Introduce;
import com.letscareer.introduce.dto.request.ReactionReq;
import com.letscareer.introduce.dto.response.GetIntroduceRes;
import com.letscareer.introduce.presentation.IntroduceController;
import com.letscareer.review.presentation.ReviewController;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IntroduceController.class)
public class IntroduceControllerTest extends ControllerTestConfig {
	@MockBean
	private IntroduceService introduceService;

	@Test
	@DisplayName("자기소개서 생성 및 수정")
	public void testCreateOrModifyIntroduce() throws Exception {
		// given
		Mockito.doNothing().when(introduceService).createOrModifyIntroduce(anyLong(), anyList());

		String introduceRequestJson = """
			    [
			        {
			            "order": 1,
			            "question": "지원동기는 무엇인가요?",
			            "answer": "저는 이 회사에서 성장할 기회를 보고 지원했습니다."
			        },
			        {
			            "order": 2,
			            "question": "자신의 강점은 무엇인가요?",
			            "answer": "논리적인 사고와 문제 해결 능력입니다."
			        }
			    ]
			""";

		// when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/introduces")
			.queryParam("recruitmentId", "1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(introduceRequestJson)
			.accept(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("introduce/createOrModifyIntroduce",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("자기소개서")
						.description("자기소개서 생성 및 수정")
						.queryParameters(
							parameterWithName("recruitmentId").description("채용 일정 ID")
						)
						.requestFields(
							fieldWithPath("[].order").description("질문 순서"),
							fieldWithPath("[].question").description("자기소개서 질문"),
							fieldWithPath("[].answer").description("자기소개서 답변")
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
	@DisplayName("자기소개서 조회")
	public void testGetIntroduces() throws Exception {
		// given
		List<GetIntroduceRes> response = List.of(
			GetIntroduceRes.of(   // 'of()' method 사용
				Introduce.of(   // 'Introduce.of()' static method 사용
					null, 1, "지원동기는 무엇인가요?", "저는 이 회사에서 성장할 기회를 보고 지원했습니다."
				)
			),
			GetIntroduceRes.of(
				Introduce.of(
					null, 2, "자신의 강점은 무엇인가요?", "논리적인 사고와 문제 해결 능력입니다."
				)
			)
		);

		Mockito.when(introduceService.getIntroduces(anyLong())).thenReturn(response);

		// when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/introduces")
			.queryParam("recruitmentId", "1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("introduce/getIntroduces",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("자기소개서")
						.description("자기소개서 조회")
						.queryParameters(
							parameterWithName("recruitmentId").description("채용 일정 ID")
						)
						.responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data.[].introduceId").description("자기소개서 ID"),
							fieldWithPath("data.[].order").description("질문 순서"),
							fieldWithPath("data.[].question").description("자기소개서 질문"),
							fieldWithPath("data.[].answer").description("자기소개서 답변"),
							fieldWithPath("data.[].type").description("자기소개서 상태 (잘했어요, 아쉬워요)")
						)
						.responseSchema(Schema.schema("CommonResponse"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("자기소개서 삭제")
	public void testDeleteIntroduce() throws Exception {
		// given
		Mockito.doNothing().when(introduceService).deleteIntroduce(anyLong(), anyLong());

		// when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/introduces/{introduceId}", 1L)
			.queryParam("recruitmentId", "1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("introduce/deleteIntroduce",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("자기소개서")
						.description("자기소개서 질문 삭제")
						.queryParameters(
							parameterWithName("recruitmentId").description("채용 일정 ID")
						)
						.pathParameters(
							parameterWithName("introduceId").description("삭제할 자기소개서 ID")
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
	@DisplayName("자기소개서 질문에 대한 반응 수정")
	public void testModifyReaction() throws Exception {
		// given
		Mockito.doNothing().when(introduceService).modifyReaction(anyLong(), any(ReactionReq.class));

		String reactionRequestJson = """
			    {
			        "reaction": "잘했어요"
			    }
			""";

		// when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/introduces/{introduceId}/reaction", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(reactionRequestJson)
			.accept(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document("introduce/modifyReaction",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("자기소개서")
						.description("자기소개서 질문에 대한 반응 수정(잘했어요, 아쉬워요)")
						.pathParameters(
							parameterWithName("introduceId").description("자기소개서 ID")
						)
						.requestFields(
							fieldWithPath("reaction").description("자기소개서 질문에 대한 반응 (잘했어요, 아쉬워요)")
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

}
