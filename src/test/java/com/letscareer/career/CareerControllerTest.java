package com.letscareer.career;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.letscareer.career.application.CareerService;
import com.letscareer.career.dto.request.CareerRequest;
import com.letscareer.career.dto.response.CareerDetailResponse;
import com.letscareer.career.presentation.CareerController;
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

import java.time.LocalDate;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CareerController.class)
public class CareerControllerTest extends ControllerTestConfig {

    @MockBean
    private CareerService careerService;

    @Test
    @DisplayName("사용자의 커리어 정보 조회")
    public void testGetCareerDetails() throws Exception {
        // given
        CareerDetailResponse careerDetailResponse = new CareerDetailResponse(
                1L,
                "John Doe",
                "MALE",
                LocalDate.of(1990, 1, 1),
                "https://example.com/profile.png",
                List.of(
                        new CareerDetailResponse.EducationResponse(
                                1L, "COLLEGE_4YR", "Harvard University", "USA",
                                LocalDate.of(2008, 9, 1), LocalDate.of(2012, 6, 30),
                                "GRADUATED", "MAJOR", "Computer Science", null, null
                        )
                ),
                List.of(
                        new CareerDetailResponse.CertificateResponse(
                                1L, "Oracle Certified Professional", "Oracle",
                                LocalDate.of(2015, 5, 15)
                        )
                ),
                List.of(
                        new CareerDetailResponse.WorkExperienceResponse(
                                1L, "FULL_TIME", "Google", "Engineering",
                                LocalDate.of(2012, 7, 1), LocalDate.of(2018, 12, 31),
                                "Software Engineer", "Developed scalable cloud solutions."
                        )
                ),
                List.of(
                        new CareerDetailResponse.ActivityResponse(
                                1L, "VOLUNTEER", "Red Cross",
                                LocalDate.of(2015, 6, 1), LocalDate.of(2015, 8, 31),
                                true, "https://redcross.org/volunteer"
                        )
                ),
                1L,
                "https://portfolio.example.com",
                "https://example.com/portfolio.pdf",
                "portfolio.pdf",
                "portfolio_file_key"
        );

        Mockito.when(careerService.getCareerDetails(anyLong())).thenReturn(careerDetailResponse);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/careers/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("careers/getCareerDetails",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("커리어 관리")
                                        .description("사용자의 기본 이력서 조회")
                                        .pathParameters(
                                                parameterWithName("userId").description("사용자 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data.userId").description("사용자 ID"),
                                                fieldWithPath("data.name").description("사용자 이름"),
                                                fieldWithPath("data.gender").description("사용자 성별"),
                                                fieldWithPath("data.birthDate").description("사용자 생년월일").optional(),
                                                fieldWithPath("data.fileUrl").description("프로필 이미지 URL").optional(),
                                                fieldWithPath("data.educations").description("교육 정보 리스트").optional(),
                                                fieldWithPath("data.educations[].id").description("교육 ID"),
                                                fieldWithPath("data.educations[].educationType").description("교육 유형"),
                                                fieldWithPath("data.educations[].schoolName").description("학교 이름"),
                                                fieldWithPath("data.educations[].schoolLocation").description("학교 위치"),
                                                fieldWithPath("data.educations[].enrollmentDate").description("입학 날짜"),
                                                fieldWithPath("data.educations[].graduationDate").description("졸업 날짜"),
                                                fieldWithPath("data.educations[].graduationStatus").description("졸업 상태"),
                                                fieldWithPath("data.educations[].majorType").description("전공 유형"),
                                                fieldWithPath("data.educations[].majorName").description("전공 이름"),
                                                fieldWithPath("data.educations[].subMajorType").description("부전공 유형").optional(),
                                                fieldWithPath("data.educations[].subMajorName").description("부전공 이름").optional(),
                                                fieldWithPath("data.certificates").description("자격증 정보 리스트").optional(),
                                                fieldWithPath("data.certificates[].id").description("자격증 ID"),
                                                fieldWithPath("data.certificates[].certificateName").description("자격증 이름"),
                                                fieldWithPath("data.certificates[].certificateIssuer").description("자격증 발행 기관"),
                                                fieldWithPath("data.certificates[].acquiredDate").description("자격증 취득 날짜"),
                                                fieldWithPath("data.workExperiences").description("경력 정보 리스트").optional(),
                                                fieldWithPath("data.workExperiences[].id").description("경력 ID"),
                                                fieldWithPath("data.workExperiences[].employmentType").description("고용 유형"),
                                                fieldWithPath("data.workExperiences[].companyName").description("회사 이름"),
                                                fieldWithPath("data.workExperiences[].departmentName").description("부서 이름"),
                                                fieldWithPath("data.workExperiences[].startDate").description("근무 시작 날짜"),
                                                fieldWithPath("data.workExperiences[].endDate").description("근무 종료 날짜"),
                                                fieldWithPath("data.workExperiences[].jobTitle").description("직무 제목"),
                                                fieldWithPath("data.workExperiences[].jobDescription").description("직무 설명"),
                                                fieldWithPath("data.activities").description("활동 정보 리스트").optional(),
                                                fieldWithPath("data.activities[].id").description("활동 ID"),
                                                fieldWithPath("data.activities[].activeType").description("활동 유형"),
                                                fieldWithPath("data.activities[].organization").description("조직 이름"),
                                                fieldWithPath("data.activities[].startDate").description("활동 시작 날짜"),
                                                fieldWithPath("data.activities[].endDate").description("활동 종료 날짜"),
                                                fieldWithPath("data.activities[].isActive").description("활동 여부"),
                                                fieldWithPath("data.activities[].activeUrl").description("활동 URL"),
                                                fieldWithPath("data.portfolioId").description("포트폴리오 ID").optional(),
                                                fieldWithPath("data.portfolioOrganizationUrl").description("포트폴리오 기관 URL").optional(),
                                                fieldWithPath("data.portfolioFileUrl").description("포트폴리오 파일 URL").optional(),
                                                fieldWithPath("data.portfolioFileName").description("포트폴리오 파일 이름").optional(),
                                                fieldWithPath("data.portfolioFileKey").description("포트폴리오 파일 키").optional()
                                        )
                                        .responseSchema(Schema.schema("CareerDetailResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("사용자의 커리어 정보 저장 또는 수정")
    public void testSaveOrUpdateCareer() throws Exception {
        // given
        Mockito.when(careerService.saveOrUpdateCareer(any(CareerRequest.class), any(MultipartFile.class), any(MultipartFile.class)))
                .thenReturn(1L);

        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "profile.png", "image/png", "Profile Image Content".getBytes());
        MockMultipartFile portfolioFile = new MockMultipartFile("portfolioFile", "portfolio.pdf", "application/pdf", "Portfolio File Content".getBytes());
        MockMultipartFile careerRequest = new MockMultipartFile("careerRequest", "", "application/json", """
                {
                    "id": 1,
                    "name": "John Doe",
                    "gender": "MALE",
                    "birthDate": "1990-01-01",
                    "educations": [
                        {
                            "id": 1,
                            "educationType": "COLLEGE_4YR",
                            "schoolName": "Harvard University",
                            "schoolLocation": "USA",
                            "enrollmentDate": "2008-09-01",
                            "graduationDate": "2012-06-30",
                            "graduationStatus": "GRADUATED",
                            "majorType": "MAJOR",
                            "majorName": "Computer Science",
                            "subMajorType": null,
                            "subMajorName": null
                        }
                    ],
                    "certificates": [
                        {
                            "id": 1,
                            "certificateName": "Oracle Certified Professional",
                            "certificateIssuer": "Oracle",
                            "acquiredDate": "2015-05-15"
                        }
                    ],
                    "workExperiences": [
                        {
                            "id": 1,
                            "employmentType": "FULL_TIME",
                            "companyName": "Google",
                            "departmentName": "Engineering",
                            "startDate": "2012-07-01",
                            "endDate": "2018-12-31",
                            "jobTitle": "Software Engineer",
                            "jobDescription": "Developed scalable cloud solutions."
                        }
                    ],
                    "activities": [
                        {
                            "id": 1,
                            "activeType": "VOLUNTEER",
                            "organization": "Red Cross",
                            "startDate": "2015-06-01",
                            "endDate": "2015-08-31",
                            "isActive": true,
                            "activeUrl": "https://redcross.org/volunteer"
                        }
                    ]
                }
                """.getBytes());

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.multipart("/careers/save")
                .file(profileImage)
                .file(portfolioFile)
                .file(careerRequest)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("careers/saveOrUpdateCareer",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(
                                partWithName("profileImage").description("프로필 이미지 파일").optional(),
                                partWithName("portfolioFile").description("포트폴리오 파일").optional(),
                                partWithName("careerRequest").description("커리어 요청 데이터")
                        ),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("커리어 관리")
                                        .description("사용자의 기본 이력서 정보 저장 또는 수정(저장하기 버튼)")
                                        .responseFields(
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("data").description("저장된 사용자 ID")
                                        )
                                        .responseSchema(Schema.schema("CommonResponse"))
                                        .build()
                        )
                ));
    }
}

