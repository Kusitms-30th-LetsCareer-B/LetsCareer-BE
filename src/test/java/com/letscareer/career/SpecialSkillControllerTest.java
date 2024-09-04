package com.letscareer.career;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.career.application.SpecialSkillService;
import com.letscareer.career.dto.request.SpecialSkillRequest;
import com.letscareer.career.dto.response.SpecialSkillResponse;
import com.letscareer.career.presentation.SpecialSkillController;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpecialSkillController.class)
public class SpecialSkillControllerTest extends ControllerTestConfig {

    @MockBean
    private SpecialSkillService specialSkillService;

    @Test
    @DisplayName("필살기 경험 목록 조회")
    public void testGetSpecialSkills() throws Exception {
        // given
        List<SpecialSkillResponse.Skill> success = List.of(
                new SpecialSkillResponse.Skill(1L, "프로젝트 성공 경험", "프로젝트를 성공적으로 완수한 경험입니다.")
        );
        List<SpecialSkillResponse.Skill> job = List.of(
                new SpecialSkillResponse.Skill(2L, "직무 경험", "직무와 관련된 실무 경험입니다.")
        );
        List<SpecialSkillResponse.Skill> collaboration = List.of(
                new SpecialSkillResponse.Skill(3L, "팀 협업 경험", "다양한 팀과의 협업 경험입니다.")
        );

        SpecialSkillResponse specialSkillResponse = new SpecialSkillResponse(success, job, collaboration);

        Mockito.when(specialSkillService.getSpecialSkillsByUserId(anyLong())).thenReturn(specialSkillResponse);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/careers/special-skills")
                .queryParam("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("specialSkills/getSpecialSkills",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("커리어 관리")
                                        .description("특정 사용자의 필살기 경험 목록 조회")
                                        .queryParameters(
                                                parameterWithName("userId").description("사용자 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data.success").description("성공 경험 목록"),
                                                fieldWithPath("data.success[].id").description("필살기 ID"),
                                                fieldWithPath("data.success[].title").description("필살기 제목"),
                                                fieldWithPath("data.success[].content").description("필살기 내용"),
                                                fieldWithPath("data.job").description("직무 경험 목록"),
                                                fieldWithPath("data.job[].id").description("필살기 ID"),
                                                fieldWithPath("data.job[].title").description("필살기 제목"),
                                                fieldWithPath("data.job[].content").description("필살기 내용"),
                                                fieldWithPath("data.collaboration").description("협업 경험 목록"),
                                                fieldWithPath("data.collaboration[].id").description("필살기 ID"),
                                                fieldWithPath("data.collaboration[].title").description("필살기 제목"),
                                                fieldWithPath("data.collaboration[].content").description("필살기 내용")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }


    @Test
    @DisplayName("필살기 생성")
    public void testAddSpecialSkill() throws Exception {
        // given
        Mockito.when(specialSkillService.addSpecialSkill(any(SpecialSkillRequest.class), anyLong()))
                .thenReturn(1L);

        String requestBody = """
                {
                    "experienceType": "성공",
                    "title": "프로젝트 성공 경험",
                    "content": "프로젝트를 성공적으로 완수한 경험입니다."
                }
                """;

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/careers/special-skills")
                .queryParam("userId", "1")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("specialSkills/addSpecialSkill",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("커리어 관리")
                                        .description("필살기 경험 추가")
                                        .queryParameters(
                                                parameterWithName("userId").description("사용자 ID")
                                        )
                                        .requestFields(
                                                fieldWithPath("experienceType").description("필살기 경험 유형 (성공, 직무, 협업)"),
                                                fieldWithPath("title").description("필살기 제목"),
                                                fieldWithPath("content").description("필살기 내용")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data").description("생성된 필살기 ID")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("필살기 수정")
    public void testUpdateSpecialSkill() throws Exception {
        // given
        Mockito.doNothing().when(specialSkillService).updateSpecialSkill(any(SpecialSkillRequest.class), anyLong());

        String requestBody = """
                {
                    "experienceType": "성공",
                    "title": "프로젝트 성공 경험",
                    "content": "프로젝트를 성공적으로 완수한 경험입니다."
                }
                """;

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/careers/special-skills")
                .queryParam("specialSkillId", "1")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("specialSkills/updateSpecialSkill",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("커리어 관리")
                                        .description("필살기 경험 수정")
                                        .queryParameters(
                                                parameterWithName("specialSkillId").description("수정할 필살기 ID")
                                        )
                                        .requestFields(
                                                fieldWithPath("experienceType").description("필살기 경험 유형 (성공, 직무, 협업)"),
                                                fieldWithPath("title").description("필살기 제목"),
                                                fieldWithPath("content").description("필살기 내용")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data").description("응답 데이터 (없음, null)")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("필살기 삭제")
    public void testDeleteSpecialSkill() throws Exception {
        // given
        Mockito.doNothing().when(specialSkillService).deleteSpecialSkill(anyLong());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/careers/special-skills")
                .queryParam("skillId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("specialSkills/deleteSpecialSkill",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("커리어 관리")
                                        .description("필살기 경험 삭제")
                                        .queryParameters(
                                                parameterWithName("skillId").description("삭제할 필살기 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data").description("응답 데이터 (없음, null)")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }
}
