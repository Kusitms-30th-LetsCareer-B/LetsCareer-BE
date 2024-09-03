package com.letscareer.archiving;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.archiving.application.ArchivingService;
import com.letscareer.archiving.dto.response.ArchivingDetailResponse;
import com.letscareer.archiving.dto.response.ArchivingFileResponse;
import com.letscareer.archiving.dto.response.ArchivingResponse;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArchivingController.class)
public class ArchivingControllerTest extends ControllerTestConfig {

    @MockBean
    private ArchivingService archivingService;

    @Test
    @DisplayName("아카이빙 추가")
    public void testAddArchiving() throws Exception {
        // given
        Mockito.when(archivingService.addArchiving(anyLong(), any(String.class), any(String.class), any(MultipartFile.class)))
                .thenReturn(1L);

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());
        MockMultipartFile archivingRequest = new MockMultipartFile("archivingRequest", "", "application/json", """
                {
                    "title": "Test Archiving",
                    "content": "This is a test archiving content."
                }
                """.getBytes());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.multipart("/archivings")
                .file(file)
                .file(archivingRequest)
                .queryParam("recruitmentId", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("archiving/addArchiving",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(partWithName("file").description("업로드할 파일").optional(),
                                partWithName("archivingRequest").description("아카이빙 요청 데이터")
                        ),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("아카이빙")
                                        .description("아카이빙 추가")
                                        .queryParameters(
                                                parameterWithName("recruitmentId").description("채용 일정 ID")
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
    @DisplayName("아카이빙 목록 조회")
    public void testGetArchivingsByRecruitmentId() throws Exception {
        // given
        Mockito.when(archivingService.getArchivingsByRecruitmentId(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(new ArchivingResponse(1L, "First Archiving"),
                        new ArchivingResponse(2L, "Second Archiving")));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/archivings/recruitment")
                .queryParam("recruitmentId", "1")
                .queryParam("page", "0")
                .queryParam("size", "5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("archiving/getArchivingsByRecruitmentId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("아카이빙")
                                        .description("특정 채용 일정에 해당하는 아카이빙 목록 조회(페이징 적용)")
                                        .queryParameters(
                                                parameterWithName("recruitmentId").description("채용 일정 ID"),
                                                parameterWithName("page").description("페이지 번호"),
                                                parameterWithName("size").description("페이지 크기")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data.[].id").description("아카이빙 ID"),
                                                fieldWithPath("data.[].title").description("아카이빙 제목")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("아카이빙 상세 조회")
    public void testGetArchivingDetail() throws Exception {
        // given
        Mockito.when(archivingService.getArchivingDetail(anyLong()))
                .thenReturn(new ArchivingDetailResponse("Archiving Title", "Archiving Content", "test.txt"));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/archivings/detail")
                .queryParam("archivingId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("archiving/getArchivingDetail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("아카이빙")
                                        .description("특정 아카이빙의 상세 정보 조회(파일 제외)")
                                        .queryParameters(
                                                parameterWithName("archivingId").description("아카이빙 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("title").description("아카이빙 제목"),
                                                fieldWithPath("content").description("아카이빙 내용"),
                                                fieldWithPath("fileName").description("파일 이름")
                                        )
                                        .responseSchema(Schema.schema("ArchivingDetailResponse"))
                                        .build()
                        )
                ));
    }



    @Test
    @DisplayName("아카이빙 파일 다운로드")
    public void testDownloadArchivingFile() throws Exception {
        // given
        Mockito.when(archivingService.getArchivingFile(anyLong()))
                .thenReturn(new ArchivingFileResponse("test.txt", "Test content".getBytes()));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/archivings/download")
                .queryParam("archivingId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_OCTET_STREAM));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("archiving/downloadArchivingFile",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("아카이빙")
                                        .description("특정 아카이빙의 파일만 조회")
                                        .queryParameters(
                                                parameterWithName("archivingId").description("아카이빙 ID")
                                        )
                                        .responseHeaders(
                                                headerWithName("Content-Disposition").description("파일 첨부 헤더"),
                                                headerWithName("Content-Type").description("파일의 MIME 타입")
                                        )
                                        .responseSchema(Schema.schema("file"))
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
        MockMultipartFile archivingRequest = new MockMultipartFile("archivingRequest", "", "application/json", """
            {
                "title": "Updated Archiving",
                "content": "This is updated archiving content."
            }
            """.getBytes());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.multipart("/archivings")
                .file(file)
                .file(archivingRequest)
                .queryParam("archivingId", "1")
                .with(request -> {
                    request.setMethod("PATCH"); // multipart에서 PATCH 메서드를 사용하기 위한 설정
                    return request;
                })
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("archiving/updateArchiving",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(partWithName("file").description("업로드할 파일").optional(),
                                partWithName("archivingRequest").description("아카이빙 요청 데이터")
                        ),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("아카이빙")
                                        .description("특정 아카이빙 수정(글, 내용, 파일 전부)")
                                        .queryParameters(
                                                parameterWithName("archivingId").description("수정할 아카이빙 ID")
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
    @DisplayName("아카이빙 삭제")
    public void testDeleteArchiving() throws Exception {
        // given
        Mockito.doNothing().when(archivingService).deleteArchiving(anyLong());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/archivings")
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
