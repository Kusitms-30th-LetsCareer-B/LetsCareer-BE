package com.letscareer.alert.application;

import com.letscareer.alert.domain.Alert;
import com.letscareer.alert.domain.repository.AlertRepository;
import com.letscareer.alert.dto.response.GetAlertsRes;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final RecruitmentRepository recruitmentRepository;
    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkAndCreateAlerts() {
        LocalDate threeDaysAfter = LocalDate.now().plusDays(3);
        List<Recruitment> recruitments = recruitmentRepository.findAll();

        for (Recruitment recruitment : recruitments) {
            List<Stage> stages = recruitment.getStages();

            for (Stage stage : stages) {
                if (stage.getEndDate().equals(threeDaysAfter)) {
                    createAlert(recruitment.getUser(), stage, recruitment.getCompanyName());
                }
            }
        }
    }

    private void createAlert(User user, Stage stage, String companyName) {
        // Alert 엔티티를 생성하고 저장
        Alert alert = Alert.builder()
                .user(user)
                .companyName(companyName)
                .stageName(stage.getStageName())
                .endDate(stage.getEndDate())
                .build();
        alertRepository.save(alert);
    }

    @Transactional(readOnly = true)
    public GetAlertsRes getAllAlerts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        List<Alert> alerts = alertRepository.findAllByUserOrderByIdDesc(user);
        return GetAlertsRes.builder()
                .alerts(alerts.stream().map(GetAlertsRes.AlertRes::from).toList())
                .build();
    }

}
