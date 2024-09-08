package com.letscareer.calendar;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.calendar.application.ScheduleService;
import com.letscareer.calendar.dto.response.ScheduleContentResponse;
import com.letscareer.calendar.presentation.ScheduleController;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.dto.response.ScheduleResponse;
import com.letscareer.global.domain.CommonResponse;
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
import java.util.Collections;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest extends ControllerTestConfig {

    @MockBean
    private ScheduleService scheduleService;

    @Test
    @DisplayName("월별 채용 일정 조회")
    public void testGetScheduleForMonth() throws Exception {
        // given
        ScheduleResponse scheduleResponse = new ScheduleResponse(1L, LocalDate.of(2024, 9, 1), "PERSONAL", "Naver");
        Mockito.when(scheduleService.getScheduleForMonth(anyLong(), anyInt(), anyInt()))
            .thenReturn(Collections.singletonList(scheduleResponse));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/calendars/recruitment")
            .param("userId", "1")
            .param("year", "2024")
            .param("month", "9")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document("calendar/getScheduleForMonth",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Calendar")
                        .description("월별 채용 일정 조회")
                        .queryParameters(
                            parameterWithName("userId").description("조회할 사용자의 ID"),
                            parameterWithName("year").description("조회할 년도"),
                            parameterWithName("month").description("조회할 월")
                        )
                        .responseFields(
                            fieldWithPath("code").description("응답 코드"),
                            fieldWithPath("message").description("응답 메시지"),
                            fieldWithPath("data.[].scheduleId").description("스케줄 ID"),
                            fieldWithPath("data.[].date").description("스케줄 날짜"),
                            fieldWithPath("data.[].filter").description("스케줄 필터"),
                            fieldWithPath("data.[].companyName").description("회사 이름")
                        )
                        .responseSchema(Schema.schema("ScheduleResponse"))
                        .build()
                )
            ));
    }

    @Test
    @DisplayName("특정 날짜의 채용 일정 조회")
    public void testGetScheduleForDate() throws Exception {
        // given
        ScheduleContentResponse scheduleContentResponse = new ScheduleContentResponse(1L, "Naver", "서류", "시작");
        Mockito.when(scheduleService.getScheduleForDate(anyLong(), any(LocalDate.class)))
            .thenReturn(Collections.singletonList(scheduleContentResponse));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/calendars/recruitment/date")
            .param("userId", "1")
            .param("date", "2024-09-07")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document("calendar/getScheduleForDate",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Calendar")
                        .description("특정 날짜의 채용 일정 조회")
                        .queryParameters(
                            parameterWithName("userId").description("조회할 사용자의 ID"),
                            parameterWithName("date").description("조회할 날짜 (yyyy-MM-dd)")
                        )
                        .responseFields(
                            fieldWithPath("code").description("응답 코드"),
                            fieldWithPath("message").description("응답 메시지"),
                            fieldWithPath("data.[].scheduleId").description("스케줄 ID"),
                            fieldWithPath("data.[].content").description("스케줄 내용 (회사 이름 + 전형 이름 + 추가 정보)")
                        )
                        .responseSchema(Schema.schema("ScheduleContentResponse"))
                        .build()
                )
            ));
    }


}
