package com.letscareer.user.application;

import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import com.letscareer.user.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long create(UserRequest userRequest) {
        User user = User.from(userRequest);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public void delete(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(userRepository::delete);
    }
}
