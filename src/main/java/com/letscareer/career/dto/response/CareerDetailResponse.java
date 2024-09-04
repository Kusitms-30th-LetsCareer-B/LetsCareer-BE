package com.letscareer.career.dto.response;

import com.letscareer.career.domain.*;
import com.letscareer.user.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record CareerDetailResponse(
        Long userId,
        String name,
        String gender,
        LocalDate birthDate,
        String imageFileUrl,
        List<EducationResponse> educations,
        List<CertificateResponse> certificates,
        List<WorkExperienceResponse> workExperiences,
        List<ActivityResponse> activities,
        Long portfolioId,
        String portfolioOrganizationUrl,
        String portfolioFileUrl,
        String portfolioFileName,
        String portfolioFileKey
) {
    public static CareerDetailResponse from(User user) {
        return new CareerDetailResponse(
                user.getId(),
                user.getName(),
                user.getGender() != null ? user.getGender().name() : null,
                user.getBirthDate(),
                user.getFileUrl(),
                user.getEducations().stream().map(EducationResponse::from).collect(Collectors.toList()),
                user.getCertificates().stream().map(CertificateResponse::from).collect(Collectors.toList()),
                user.getWorkExperiences().stream().map(WorkExperienceResponse::from).collect(Collectors.toList()),
                user.getActivities().stream().map(ActivityResponse::from).collect(Collectors.toList()),
                user.getPortfolio() != null ? user.getPortfolio().getId() : null,
                user.getPortfolio() != null ? user.getPortfolio().getOrganizationUrl() : null,
                user.getPortfolio() != null ? user.getPortfolio().getFileUrl() : null,
                user.getPortfolio() != null ? user.getPortfolio().getFileName() : null,
                user.getPortfolio() != null ? user.getPortfolio().getFileKey() : null
        );
    }

    public record EducationResponse(
            Long id,
            String educationType,
            String schoolName,
            String schoolLocation,
            LocalDate enrollmentDate,
            LocalDate graduationDate,
            String graduationStatus,
            String majorType,
            String majorName,
            String subMajorType,
            String subMajorName
    ) {
        public static EducationResponse from(Education education) {
            return new EducationResponse(
                    education.getId(),
                    education.getEducationType().getName(),
                    education.getSchoolName(),
                    education.getSchoolLocation().getName(),
                    education.getEnrollmentDate(),
                    education.getGraduationDate(),
                    education.getGraduationStatus().getName(),
                    education.getMajorType().getName(),
                    education.getMajorName(),
                    education.getSubMajorType() != null ? education.getSubMajorType().getName() : null,
                    education.getSubMajorName()
            );
        }
    }

    public record CertificateResponse(
            Long id,
            String certificateName,
            String certificateIssuer,
            LocalDate acquiredDate
    ) {
        public static CertificateResponse from(Certificate certificate) {
            return new CertificateResponse(
                    certificate.getId(),
                    certificate.getCertificateName(),
                    certificate.getCertificateIssuer(),
                    certificate.getAcquiredDate()
            );
        }
    }

    public record WorkExperienceResponse(
            Long id,
            String employmentType,
            String companyName,
            String departmentName,
            LocalDate startDate,
            LocalDate endDate,
            String jobTitle,
            String jobDescription
    ) {
        public static WorkExperienceResponse from(WorkExperience workExperience) {
            return new WorkExperienceResponse(
                    workExperience.getId(),
                    workExperience.getEmploymentType().getName(),
                    workExperience.getCompanyName(),
                    workExperience.getDepartmentName(),
                    workExperience.getStartDate(),
                    workExperience.getEndDate(),
                    workExperience.getJobTitle(),
                    workExperience.getJobDescription()
            );
        }
    }

    public record ActivityResponse(
            Long id,
            String activeType,
            String organization,
            LocalDate startDate,
            LocalDate endDate,
            Boolean isActive,
            String activeUrl
    ) {
        public static ActivityResponse from(Activity activity) {
            return new ActivityResponse(
                    activity.getId(),
                    activity.getActiveType().getName(),
                    activity.getOrganization(),
                    activity.getStartDate(),
                    activity.getEndDate(),
                    activity.getIsActive(),
                    activity.getActiveUrl()
            );
        }
    }
}
