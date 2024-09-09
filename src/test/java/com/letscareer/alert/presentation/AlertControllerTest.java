package com.letscareer.alert.presentation;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.alert.application.AlertService;
import com.letscareer.alert.domain.Alert;
import com.letscareer.alert.dto.response.GetAlertsRes;
import com.letscareer.configuration.ControllerTestConfig;
import com.letscareer.todo.dto.response.GroupedByCompanyRes;
import com.letscareer.todo.dto.response.TodoRes;
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
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlertController.class)
public class AlertControllerTest extends ControllerTestConfig {

    @MockBean
    private AlertService alertService;

    @Test
    @DisplayName("유저의 알림 목록 반환")
    public void testGetAllAlerts() throws Exception {
        GetAlertsRes getAlertsRes = GetAlertsRes.builder()
                .alerts(List.of(GetAlertsRes.AlertRes.builder()
                                .companyName("카카오")
                                .stageName("서류")
                                .endDate(LocalDate.of(2024, 9, 12))
                                .build()))
                .build();

        Mockito.when(alertService.getAllAlerts(anyLong()))
                .thenReturn(getAlertsRes);
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/alerts")
                .param("userId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("유저의 알림목록을 반환하였습니다."))
                .andExpect(jsonPath("$.data.alerts[0].companyName").value("카카오"))
                .andExpect(jsonPath("$.data.alerts[0].stageName").value("서류"))
                .andExpect(jsonPath("$.data.alerts[0].endDate").value("2024-09-12"))
                .andDo(MockMvcRestDocumentationWrapper.document("todo/getAllAlerts",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("알림")
                                        .description("해당 유저의 알림 리스트 조회")
                                        .queryParameters(
                                                parameterWithName("userId").description("유저 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data.alerts[].companyName").description("기업명"),
                                                fieldWithPath("data.alerts[].stageName").description("전형명"),
                                                fieldWithPath("data.alerts[].endDate").description("마감일")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));

    }
}