package com.letscareer.presentation;

import com.letscareer.url.application.UrlMetadataService;
import com.letscareer.url.dto.UrlMetadata;
import com.letscareer.url.presentation.UrlMetadataController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlMetadataController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class UrlMetadataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlMetadataService urlMetadataService;

    @Test
    public void testGetUrlMetadata() throws Exception {
        // given
        UrlMetadata metadata = new UrlMetadata("Test Title", "Test Description", "https://example.com/favicon.ico", "https://example.com");
        Mockito.when(urlMetadataService.getUrlMetadata(anyString())).thenReturn(metadata);

        // when & then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/url-metadata")
                        .param("url", "https://example.com"))
                .andExpect(status().isOk())
                .andDo(document("get-url-metadata",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}
