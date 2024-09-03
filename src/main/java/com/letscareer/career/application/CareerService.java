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
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CareerService {

    private final UserCustomRepository careerRepository;
    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final CertificateRepository certificateRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final ActivityRepository activityRepository;
    private final PortfolioRepository portfolioRepository;
    private final S3Service s3Service;

    private final String PROFILE_DIR = "profile_images";
    private final String PORTFOLIO_DIR = "portfolio_files";

    public CareerDetailResponse getCareerDetails(Long userId) {
        User user = careerRepository.findByUserId(userId);

        if (user == null) {
            throw new CustomException(ExceptionContent.NOT_FOUND_USER);
        }

        return CareerDetailResponse.from(user);
    }

    @Transactional
    public Long saveOrUpdateCareer(CareerRequest careerRequest, MultipartFile profileImage, MultipartFile portfolioFile) throws IOException {
        User user = userRepository.findById(careerRequest.id())
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));  // 기존 ID가 없으면 예외 발생

        // 기존 유저 정보 업데이트
        user.updateFromRequest(careerRequest);

        // 프로필 이미지 처리
        if (profileImage != null && !profileImage.isEmpty()) {
            String profileImageFileName = profileImage.getOriginalFilename();
            String profileImageFileKey = PROFILE_DIR + "/" + UUID.randomUUID().toString() + "_" + profileImageFileName;
            String profileImageUrl = s3Service.upload(profileImage, profileImageFileKey);
            user.updateProfileImage(profileImageFileName, profileImageUrl, profileImageFileKey);
        }

        // 포트폴리오 파일 처리
        if (portfolioFile != null && !portfolioFile.isEmpty()) {
            Portfolio portfolio = user.getPortfolio() != null ? user.getPortfolio() : Portfolio.createEmptyPortfolio(user);
            String portfolioFileName = portfolioFile.getOriginalFilename();
            String portfolioFileKey = PORTFOLIO_DIR + "/" + UUID.randomUUID().toString() + "_" + portfolioFileName;
            String portfolioFileUrl = s3Service.upload(portfolioFile, portfolioFileKey);
            portfolio.updatePortfolioFile(portfolioFileName, portfolioFileUrl, portfolioFileKey);
            portfolioRepository.save(portfolio);
        }

        saveOrUpdateEducations(user, careerRequest);
        saveOrUpdateCertificates(user, careerRequest);
        saveOrUpdateWorkExperiences(user, careerRequest);
        saveOrUpdateActivities(user, careerRequest);

        userRepository.save(user);
        return user.getId();
    }

    private void saveOrUpdateEducations(User user, CareerRequest careerRequest) {
        careerRequest.educations().forEach(educationDto -> {
            Education education = educationDto.id() != null ?
                    educationRepository.findById(educationDto.id())
                            .orElseGet(() -> Education.createEmptyEducation(user)) :
                    Education.createEmptyEducation(user);

            education.updateFromDto(educationDto);
            educationRepository.save(education);
        });
    }

    private void saveOrUpdateCertificates(User user, CareerRequest careerRequest) {
        careerRequest.certificates().forEach(certificateDto -> {
            Certificate certificate = certificateDto.id() != null ?
                    certificateRepository.findById(certificateDto.id())
                            .orElseGet(() -> Certificate.createEmptyCertificate(user)) :
                    Certificate.createEmptyCertificate(user);

            certificate.updateFromDto(certificateDto);
            certificateRepository.save(certificate);
        });
    }


    private void saveOrUpdateWorkExperiences(User user, CareerRequest careerRequest) {
        careerRequest.workExperiences().forEach(workExperienceDto -> {
            WorkExperience workExperience = workExperienceDto.id() != null ?
                    workExperienceRepository.findById(workExperienceDto.id())
                            .orElseGet(() -> WorkExperience.createEmptyWorkExperience(user)) :
                    WorkExperience.createEmptyWorkExperience(user);

            workExperience.updateFromDto(workExperienceDto);
            workExperienceRepository.save(workExperience);
        });
    }


    private void saveOrUpdateActivities(User user, CareerRequest careerRequest) {
        careerRequest.activities().forEach(activityDto -> {
            Activity activity = activityDto.id() != null ?
                    activityRepository.findById(activityDto.id())
                            .orElseGet(() -> Activity.createEmptyActivity(user)) :
                    Activity.createEmptyActivity(user);

            activity.updateFromDto(activityDto);
            activityRepository.save(activity);
        });
    }
}

