package com.depromeet.archive.api.user;

import com.depromeet.archive.api.resolver.annotation.RequestUser;
import com.depromeet.archive.domain.user.UserService;
import com.depromeet.archive.domain.user.command.CredentialRegisterCommand;
import com.depromeet.archive.domain.user.info.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/archive")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    //TODO - ExceptionController 에서 @Valid 가 던진 메세지 처리
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid CredentialRegisterCommand command) {
        userService.registerUser(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unregister")
    public ResponseEntity<Void> unregisterUser(@RequestUser UserInfo user) {
        userService.deleteUser(user.getUserId());
        return ResponseEntity.ok().build();
    }

}
