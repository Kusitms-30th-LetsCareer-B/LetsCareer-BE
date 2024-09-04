package com.letscareer.career.dto.response;

import com.letscareer.career.domain.SpecialSkill;
import com.letscareer.career.domain.enums.ExperienceType;

import java.util.List;

public record SpecialSkillResponse(
        List<Skill> success,
        List<Skill> job,
        List<Skill> collaboration
) {
    public static SpecialSkillResponse from(List<SpecialSkill> specialSkills) {
        List<Skill> successSkills = specialSkills.stream()
                .filter(skill -> skill.getExperienceType() == ExperienceType.SUCCESS)
                .map(Skill::from)
                .toList();

        List<Skill> taskSkills = specialSkills.stream()
                .filter(skill -> skill.getExperienceType() == ExperienceType.JOB)
                .map(Skill::from)
                .toList();

        List<Skill> collaborationSkills = specialSkills.stream()
                .filter(skill -> skill.getExperienceType() == ExperienceType.COLLABORATION)
                .map(Skill::from)
                .toList();

        return new SpecialSkillResponse(successSkills, taskSkills, collaborationSkills);
    }

    public static record Skill(Long id, String title, String content) {
        public static Skill from(SpecialSkill specialSkill) {
            return new Skill(
                    specialSkill.getId(),
                    specialSkill.getTitle(),
                    specialSkill.getContent()
            );
        }
    }
}
