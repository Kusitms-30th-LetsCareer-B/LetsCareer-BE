package com.letscareer.career.application;

import com.letscareer.career.domain.*;
import com.letscareer.career.domain.repository.*;
import com.letscareer.career.dto.request.CareerRequest;
import com.letscareer.career.dto.response.CareerDetailResponse;
import com.letscareer.global.application.S3Service;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserCustomRepository;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CareerService {

    private final UserCustomRepository userCustomRepository;
    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final CertificateRepository certificateRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final ActivityRepository activityRepository;
    private final PortfolioRepository portfolioRepository;
    private final S3Service s3Service;

    private final String PROFILE_DIR = "profile_images";
    private final String PORTFOLIO_DIR = "portfolio_files";

    @Transactional(readOnly = true)
    public CareerDetailResponse getCareerDetails(Long userId) {
        User user = userCustomRepository.findByUserId(userId);

        if (user == null) {
            throw new CustomException(ExceptionContent.NOT_FOUND_USER);
        }

        return CareerDetailResponse.from(user);
    }

    public Long saveOrUpdateCareer(CareerRequest careerRequest, MultipartFile profileImage, MultipartFile portfolioFile) throws IOException {
        User user = userRepository.findById(careerRequest.userId())
            .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));

        // 기존 유저 정보 업데이트
        user.updateFromRequest(careerRequest);

        // 프로필 이미지 처리
        if (profileImage != null && !profileImage.isEmpty()) {
            // 기존 프로필 이미지가 있으면 삭제하고 새로운 이미지 업로드
            if (user.getFileKey() != null) {
                s3Service.deleteFile(user.getFileKey());
            }
            String profileImageFileName = profileImage.getOriginalFilename();
            String profileImageFileKey = PROFILE_DIR + "/" + UUID.randomUUID().toString() + "_" + profileImageFileName;
            String profileImageUrl = s3Service.upload(profileImage, profileImageFileKey);
            user.updateProfileImage(profileImageFileName, profileImageUrl, profileImageFileKey);
        }

        // 포트폴리오 파일 처리
        if (portfolioFile != null && !portfolioFile.isEmpty()) {
            Portfolio portfolio = user.getPortfolio() != null ? user.getPortfolio() : Portfolio.createEmptyPortfolio(user);
            if (portfolio.getFileKey() != null) {
                s3Service.deleteFile(portfolio.getFileKey());
            }
            String portfolioFileName = portfolioFile.getOriginalFilename();
            String portfolioFileKey = PORTFOLIO_DIR + "/" + UUID.randomUUID().toString() + "_" + portfolioFileName;
            String portfolioFileUrl = s3Service.upload(portfolioFile, portfolioFileKey);
            String requestOrganizationUrl = careerRequest.portfolioOrganizationUrl();
            portfolio.updatePortfolioFile(portfolioFileName, portfolioFileUrl, portfolioFileKey, requestOrganizationUrl);
            portfolioRepository.save(portfolio);
        }

        // 교육 정보 처리
        saveOrUpdateEducations(user, careerRequest.educations());

        // 자격증 정보 처리
        saveOrUpdateCertificates(user, careerRequest.certificates());

        // 경력 정보 처리
        saveOrUpdateWorkExperiences(user, careerRequest.workExperiences());

        // 활동 정보 처리
        saveOrUpdateActivities(user, careerRequest.activities());

        userRepository.save(user);
        return user.getId();
    }

    private void saveOrUpdateEducations(User user, List<CareerRequest.EducationRequest> educationRequests) {
        List<Long> requestedIds = educationRequests.stream().map(CareerRequest.EducationRequest::educationId).toList();

        // 삭제 처리: 요청에 없는 ID는 삭제
        educationRepository.findByUser(user).stream()
            .filter(education -> !requestedIds.contains(education.getId()))
            .forEach(educationRepository::delete);

        educationRequests.forEach(educationDto -> {
            Education education = educationDto.educationId() != null ?
                educationRepository.findById(educationDto.educationId())
                    .orElseGet(() -> Education.createEmptyEducation(user)) :
                Education.createEmptyEducation(user);

            education.updateFromDto(educationDto);
            educationRepository.save(education);
        });
    }

    private void saveOrUpdateCertificates(User user, List<CareerRequest.CertificateRequest> certificateRequests) {
        List<Long> requestedIds = certificateRequests.stream().map(CareerRequest.CertificateRequest::certificateId).toList();

        certificateRepository.findByUser(user).stream()
            .filter(certificate -> !requestedIds.contains(certificate.getId()))
            .forEach(certificateRepository::delete);

        certificateRequests.forEach(certificateDto -> {
            Certificate certificate = certificateDto.certificateId() != null ?
                certificateRepository.findById(certificateDto.certificateId())
                    .orElseGet(() -> Certificate.createEmptyCertificate(user)) :
                Certificate.createEmptyCertificate(user);

            certificate.updateFromDto(certificateDto);
            certificateRepository.save(certificate);
        });
    }

    private void saveOrUpdateWorkExperiences(User user, List<CareerRequest.WorkExperienceRequest> workExperienceRequests) {
        List<Long> requestedIds = workExperienceRequests.stream().map(CareerRequest.WorkExperienceRequest::workExperienceId).toList();

        workExperienceRepository.findByUser(user).stream()
            .filter(workExperience -> !requestedIds.contains(workExperience.getId()))
            .forEach(workExperienceRepository::delete);

        workExperienceRequests.forEach(workExperienceDto -> {
            WorkExperience workExperience = workExperienceDto.workExperienceId() != null ?
                workExperienceRepository.findById(workExperienceDto.workExperienceId())
                    .orElseGet(() -> WorkExperience.createEmptyWorkExperience(user)) :
                WorkExperience.createEmptyWorkExperience(user);

            workExperience.updateFromDto(workExperienceDto);
            workExperienceRepository.save(workExperience);
        });
    }

    private void saveOrUpdateActivities(User user, List<CareerRequest.ActivityRequest> activityRequests) {
        List<Long> requestedIds = activityRequests.stream().map(CareerRequest.ActivityRequest::activityId).toList();

        activityRepository.findByUser(user).stream()
            .filter(activity -> !requestedIds.contains(activity.getId()))
            .forEach(activityRepository::delete);

        activityRequests.forEach(activityDto -> {
            Activity activity = activityDto.activityId() != null ?
                activityRepository.findById(activityDto.activityId())
                    .orElseGet(() -> Activity.createEmptyActivity(user)) :
                Activity.createEmptyActivity(user);

            activity.updateFromDto(activityDto);
            activityRepository.save(activity);
        });
    }
}