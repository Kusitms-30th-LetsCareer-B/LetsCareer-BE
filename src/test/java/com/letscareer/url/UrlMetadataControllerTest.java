package com.letscareer.url;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.configuration.ControllerTestConfig;
import com.letscareer.url.application.UrlMetadataService;
import com.letscareer.url.dto.UrlMetadata;
import com.letscareer.url.presentation.UrlMetadataController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlMetadataController.class)
public class UrlMetadataControllerTest extends ControllerTestConfig {

    @MockBean
    private UrlMetadataService urlMetadataService;

    @Test
    @DisplayName("URL 메타데이터 조회")
    public void testGetUrlMetadata() throws Exception {
        // given
        UrlMetadata metadata = new UrlMetadata("Test Title", "Test Description", "https://example.com/favicon.ico", "https://example.com");
        Mockito.when(urlMetadataService.getUrlMetadata(anyString())).thenReturn(metadata);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/url/metadata")
                .param("url", "https://example.com")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document("url/getMetadata",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("URL")
                        .description("URL 메타데이터 조회")
                        .queryParameters( // requestParameters는 삭제 이걸로 써야함
                                parameterWithName("url").description("조회할 대상의 URL")
                        )
                        .responseFields(
                            fieldWithPath("code").description("응답 코드"),
                            fieldWithPath("message").description("응답 메시지"),
                            fieldWithPath("data.title").description("메타데이터의 제목"),
                            fieldWithPath("data.description").description("메타데이터의 설명"),
                            fieldWithPath("data.iconUrl").description("Icon  URL"),
                            fieldWithPath("data.url").description("원본 URL")
                        )
                        .responseSchema(Schema.schema("UrlMetadataResponse"))  // 여기에 추가
                        .build()
                )
            ));
    }
}
