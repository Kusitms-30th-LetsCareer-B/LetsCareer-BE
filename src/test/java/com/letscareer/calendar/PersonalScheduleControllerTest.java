package com.letscareer.calendar;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.calendar.application.PersonalScheduleService;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.dto.request.UpdatePersonalScheduleRequest;
import com.letscareer.calendar.dto.response.PersonalScheduleResponse;
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
import java.util.Collections;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.*;
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
                    "content": "팀 회의 참석"
                }
                """;

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/calendars/personal")
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

    @Test
    @DisplayName("월별 개인 일정 조회")
    public void testGetPersonalScheduleForMonth() throws Exception {
        // given
        PersonalScheduleResponse personalScheduleResponse = new PersonalScheduleResponse(1L, LocalDate.of(2024, 9, 1), "Meeting");
        Mockito.when(personalScheduleService.getPersonalScheduleForMonth(anyLong(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(personalScheduleResponse));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/calendars/personal")
                .param("userId", "1")
                .param("year", "2024")
                .param("month", "9")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("calendar/getPersonalScheduleForMonth",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Calendar")
                                        .description("월별 개인 일정 조회")
                                        .queryParameters(
                                                parameterWithName("userId").description("조회할 사용자의 ID"),
                                                parameterWithName("year").description("조회할 년도"),
                                                parameterWithName("month").description("조회할 월")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data.[].personalScheduleId").description("개인 일정 ID"),
                                                fieldWithPath("data.[].date").description("개인 일정 날짜"),
                                                fieldWithPath("data.[].content").description("개인 일정 내용")
                                        )
                                        .responseSchema(Schema.schema("PersonalScheduleResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("특정 날짜의 개인 일정 조회")
    public void testGetPersonalScheduleForDate() throws Exception {
        // given
        PersonalScheduleResponse personalScheduleResponse = new PersonalScheduleResponse(1L, LocalDate.of(2024, 9, 7), "프로젝트 미팅");
        Mockito.when(personalScheduleService.getPersonalScheduleForDate(anyLong(), any(LocalDate.class)))
            .thenReturn(Collections.singletonList(personalScheduleResponse));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/calendars/personal/date")
            .param("userId", "1")
            .param("date", "2024-09-07")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document("calendar/getPersonalScheduleForDate",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Calendar")
                        .description("특정 날짜의 개인 일정 조회")
                        .queryParameters(
                            parameterWithName("userId").description("조회할 사용자의 ID"),
                            parameterWithName("date").description("조회할 날짜 (yyyy-MM-dd)")
                        )
                        .responseFields(
                            fieldWithPath("code").description("응답 코드"),
                            fieldWithPath("message").description("응답 메시지"),
                            fieldWithPath("data.[].personalScheduleId").description("개인 일정 ID"),
                            fieldWithPath("data.[].date").description("개인 일정 날짜"),
                            fieldWithPath("data.[].content").description("개인 일정 내용")
                        )
                        .responseSchema(Schema.schema("PersonalScheduleResponse"))
                        .build()
                )
            ));
    }


    @Test
    @DisplayName("개인 일정 수정")
    public void testUpdatePersonalSchedule() throws Exception {
        // given
        Mockito.doNothing().when(personalScheduleService).updatePersonalSchedule(anyLong(), any(UpdatePersonalScheduleRequest.class));

        String jsonRequest = """
            {
                "content": "수정된 프로젝트 계획 회의"
            }
            """;

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/calendars/personal/{personalScheduleId}", 1L)
            .content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document("calendar/updatePersonalSchedule",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Calendar")
                        .description("개인 일정 수정")
                        .pathParameters(
                            parameterWithName("personalScheduleId").description("수정할 개인 일정의 ID")
                        )
                        .requestFields(
                            fieldWithPath("content").description("수정할 일정 내용")
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
    @DisplayName("개인 일정 삭제")
    public void testDeletePersonalSchedule() throws Exception {
        // given
        Mockito.doNothing().when(personalScheduleService).deletePersonalSchedule(anyLong());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/calendars/personal/{personalScheduleId}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document("calendar/deletePersonalSchedule",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Calendar")
                        .description("개인 일정 삭제")
                        .pathParameters(
                            parameterWithName("personalScheduleId").description("삭제할 개인 일정의 ID")
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
