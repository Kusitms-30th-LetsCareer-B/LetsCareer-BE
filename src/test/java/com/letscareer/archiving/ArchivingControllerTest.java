package com.letscareer.archiving;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.archiving.application.ArchivingService;
import com.letscareer.archiving.presentation.ArchivingController;
import com.letscareer.configuration.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArchivingController.class)
public class ArchivingControllerTest extends ControllerTestConfig {

    @MockBean
    private ArchivingService archivingService;

    @Test
    @DisplayName("아카이빙 추가")
    public void testAddArchiving() throws Exception {
        // given
        Mockito.when(archivingService.addArchiving(anyLong(), any(String.class), any(String.class), any())).thenReturn(1L);

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.multipart("/archiving")
                .file(file)
                .queryParam("recruitmentId", "1")
                .queryParam("title", "Test Archiving")
                .queryParam("content", "This is a test archiving content.")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("archiving/addArchiving",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("아카이빙")
                                        .description("아카이빙 추가")
                                        .queryParameters(
                                                parameterWithName("recruitmentId").description("채용 일정 ID"),
                                                parameterWithName("title").description("아카이빙 제목"),
                                                parameterWithName("content").description("아카이빙 내용")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data").description("생성된 아카이빙 ID")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("아카이빙 수정")
    public void testUpdateArchiving() throws Exception {
        // given
        Mockito.doNothing().when(archivingService).updateArchiving(anyLong(), any(String.class), any(String.class), any());

        MockMultipartFile file = new MockMultipartFile("file", "update.txt", "text/plain", "Updated content".getBytes());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.multipart("/archiving")
                .file(file)
                .queryParam("archivingId", "1")
                .queryParam("title", "Updated Archiving")
                .queryParam("content", "This is updated archiving content.")
                .with(request -> {
                    request.setMethod("PUT"); // multipart에서 PUT 메서드를 사용하기 위한 설정
                    return request;
                })
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("archiving/updateArchiving",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("아카이빙")
                                        .description("아카이빙 수정")
                                        .queryParameters(
                                                parameterWithName("archivingId").description("수정할 아카이빙 ID"),
                                                parameterWithName("title").description("수정할 아카이빙 제목"),
                                                parameterWithName("content").description("수정할 아카이빙 내용")
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
    @DisplayName("아카이빙 삭제")
    public void testDeleteArchiving() throws Exception {
        // given
        Mockito.doNothing().when(archivingService).deleteArchiving(anyLong());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/archiving")
                .queryParam("archivingId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("archiving/deleteArchiving",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("아카이빙")
                                        .description("아카이빙 삭제")
                                        .queryParameters(
                                                parameterWithName("archivingId").description("삭제할 아카이빙 ID")
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
