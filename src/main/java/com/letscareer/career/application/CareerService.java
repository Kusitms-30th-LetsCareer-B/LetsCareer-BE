package com.letscareer.career.application;

import com.letscareer.career.dto.response.CareerDetailResponse;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CareerService {

    private final UserCustomRepository careerRepository;

    public CareerDetailResponse getCareerDetails(Long userId) {
        User user = careerRepository.findByUserId(userId);

        if (user == null) {
            throw new CustomException(ExceptionContent.NOT_FOUND_USER);
        }

        return CareerDetailResponse.from(user);
    }
}
