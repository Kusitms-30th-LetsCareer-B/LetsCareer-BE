package com.letscareer.alert.dto.response;

import com.letscareer.alert.domain.Alert;
import com.letscareer.introduce.domain.Introduce;
import com.letscareer.introduce.dto.response.GetIntroduceRes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAlertsRes {

    private List<AlertRes> alerts;

    public static GetAlertsRes from(List<AlertRes> alerts) {
        return GetAlertsRes.builder()
                .alerts(alerts)
                .build();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class AlertRes {
        private String companyName;
        private String stageName;
        private LocalDate endDate;

        public static AlertRes from(Alert alert) {
            return AlertRes.builder()
                    .companyName(alert.getCompanyName())
                    .stageName(alert.getStageName())
                    .endDate(alert.getEndDate())
                    .build();
        }
    }
}
