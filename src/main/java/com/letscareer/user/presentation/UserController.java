package com.letscareer.user.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.user.application.UserService;
import com.letscareer.user.domain.User;
import com.letscareer.user.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonResponse<?>> createUser(@RequestBody UserRequest userRequest) {
        userService.create(userRequest);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("유저 생성에 성공하였습니다.", null));
    }

    @DeleteMapping("")
    public ResponseEntity<CommonResponse<?>> deleteUser(@RequestParam(name = "userId") Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("유저 삭제에 성공하였습니다.", null));
    }

}