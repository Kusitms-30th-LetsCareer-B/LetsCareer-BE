package com.letscareer.career.application;

import com.letscareer.career.domain.enums.ExperienceType;
import com.letscareer.career.dto.request.SpecialSkillRequest;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.career.domain.SpecialSkill;
import com.letscareer.career.domain.repository.SpecialSkillRepository;
import com.letscareer.career.dto.response.SpecialSkillResponse;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class SpecialSkillService {

    private final SpecialSkillRepository specialSkillRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public SpecialSkillResponse getSpecialSkillsByUserId(Long userId) {
        List<SpecialSkill> skills = specialSkillRepository.findByUserId(userId);
        return SpecialSkillResponse.from(skills);
    }

    public Long addSpecialSkill(SpecialSkillRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));

        // 한글로 받은 경험 타입을 Enum으로 변환
        ExperienceType experienceType = ExperienceType.of(request.experienceType());

        SpecialSkill specialSkill = SpecialSkill.create(user, request.title(), request.content(), experienceType);
        specialSkillRepository.save(specialSkill);
        return specialSkill.getId();
    }

    public void updateSpecialSkill(SpecialSkillRequest request, Long specialSkillId) {
        SpecialSkill specialSkill = specialSkillRepository.findById(specialSkillId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_SPECIAL_SKILL));

        // 한글로 받은 경험 타입을 Enum으로 변환
        ExperienceType experienceType = ExperienceType.of(request.experienceType());

        specialSkill.updateSpecialSkill(request.title(), request.content(), experienceType);
    }

    public void deleteSpecialSkill(Long skillId) {
        SpecialSkill specialSkill = specialSkillRepository.findById(skillId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_SPECIAL_SKILL));
        specialSkillRepository.delete(specialSkill);
    }
}
