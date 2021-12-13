package com.depromeet.archive.api.user;

import com.depromeet.archive.domain.user.UserService;
import com.depromeet.archive.domain.user.command.PasswordRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid PasswordRegisterCommand command) {
        encryptPassword(command);
        userService.registerUser(command);
        return ResponseEntity.ok().build();
    }

    private void encryptPassword(PasswordRegisterCommand command) {
        command.setPassword(encoder.encode(command.getPassword()));
    }


}
