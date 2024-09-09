package com.letscareer.alert.presentation;

import com.letscareer.alert.application.AlertService;
import com.letscareer.alert.domain.Alert;
import com.letscareer.alert.dto.response.GetAlertsRes;
import com.letscareer.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    /**
     * 유저의 알림창 반환
     * @param userId
     * @return
     */
    @GetMapping("/alerts")
    public ResponseEntity<CommonResponse<GetAlertsRes>> getAllAlerts(@RequestParam Long userId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("유저의 알림목록을 반환하였습니다.", alertService.getAllAlerts(userId)));
    }
}
