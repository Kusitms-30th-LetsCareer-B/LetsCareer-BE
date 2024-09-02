package com.letscareer.calendar;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.calendar.application.PersonalScheduleService;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.presentation.PersonalScheduleController;
import com.letscareer.configuration.ControllerTestConfig;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonalScheduleController.class)
public class PersonalScheduleControllerTest extends ControllerTestConfig {

    @MockBean
    private PersonalScheduleService personalScheduleService;

    @Test
    @DisplayName("사용자의 개인 일정 추가")
    public void testAddPersonalSchedule() throws Exception {
        // given
        Mockito.doNothing().when(personalScheduleService).addPersonalSchedule(anyLong(), any(PersonalScheduleRequest.class));

        String jsonRequest = """
                {
                    "date": "2024-09-01",
                    "content": "Meeting with the team"
                }
                """;

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/calendar/personal-schedule")
                .param("userId", "1")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("calendar/addPersonalSchedule",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Calendar")
                                        .description("사용자의 개인 일정 추가")
                                        .queryParameters(
                                                parameterWithName("userId").description("일정을 추가할 사용자의 ID")
                                        )
                                        .requestFields(
                                                fieldWithPath("date").description("일정 날짜"),
                                                fieldWithPath("content").description("일정 내용")
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
