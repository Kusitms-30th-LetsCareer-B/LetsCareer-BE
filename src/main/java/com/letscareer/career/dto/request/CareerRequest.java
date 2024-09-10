package com.letscareer.career.dto.request;

import java.time.LocalDate;
import java.util.List;

public record CareerRequest(
    Long userId,
    String name,
    String gender,
    LocalDate birthDate,
    String portfolioOrganizationUrl,
    List<EducationRequest> educations,
    List<CertificateRequest> certificates,
    List<WorkExperienceRequest> workExperiences,
    List<ActivityRequest> activities
) {
    public record EducationRequest(
        Long educationId,
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
    }

    public record CertificateRequest(
        Long certificateId,
        String certificateName,
        String certificateIssuer,
        LocalDate acquiredDate
    ) {
    }

    public record WorkExperienceRequest(
        Long workExperienceId,
        String employmentType,
        String companyName,
        String departmentName,
        LocalDate startDate,
        LocalDate endDate,
        String jobTitle,
        String jobDescription
    ) {
    }

    public record ActivityRequest(
        Long activityId,
        String activeType,
        String organization,
        LocalDate startDate,
        LocalDate endDate,
        Boolean isActive,
        String activeUrl
    ) {
    }
}
