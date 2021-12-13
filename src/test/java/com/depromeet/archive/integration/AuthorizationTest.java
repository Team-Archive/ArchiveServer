package com.depromeet.archive.integration;

import com.depromeet.archive.ArchiveApplication;
import com.depromeet.archive.domain.user.command.CredentialRegisterCommand;
import com.depromeet.archive.domain.user.command.LoginCommand;
import com.depromeet.archive.infra.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Slf4j
@SpringBootTest(classes = {ArchiveApplication.class, IntegrationContext.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthorizationTest {

    private CredentialRegisterCommand testRegisterInfo;
    private LoginCommand loginCommand;

    @Autowired
    private ApiHelper helper;

    @BeforeEach
    public void initDTO() {
        var testEmail = UUID.randomUUID() + "@naver.com";
        var testPassword = "abcABC123!@#";
        testRegisterInfo = new CredentialRegisterCommand(testEmail, testPassword);
        loginCommand = new LoginCommand(testRegisterInfo.getEmail(), testRegisterInfo.getPassword());
    }


    @Test
    void registerWithValidPassword() {
        Assertions.assertEquals(HttpStatus.OK.value(), helper.tryRegister(testRegisterInfo));
    }

    @Test
    void registerWithNoSpecialCharacter() {
        testRegisterInfo.setPassword("abcABC123");
        Assertions.assertEquals(HttpStatus.OK.value(), helper.tryRegister(testRegisterInfo));
    }

    @Test
    void registerWithNoNumber() {
        testRegisterInfo.setPassword("abcABC!@#");
        Assertions.assertNotEquals(HttpStatus.OK.value(), helper.tryRegister(testRegisterInfo));
    }

    @Test
    void registerWithNoAlphabet() {
        testRegisterInfo.setPassword("123456!@#");
        Assertions.assertNotEquals(HttpStatus.OK.value(), helper.tryRegister(testRegisterInfo));
    }

    @Test
    void registerWithLongPassword() {
        testRegisterInfo.setPassword("TooLongPassword1234!@#");
        Assertions.assertNotEquals(HttpStatus.OK.value(), helper.tryRegister(testRegisterInfo));
    }

    @Test
    void registerWithShortPassword() {
        testRegisterInfo.setPassword("ab12!@");
        Assertions.assertNotEquals(HttpStatus.OK.value(), helper.tryRegister(testRegisterInfo));
    }

    @Test
    void registerAndLogin() {
        Assertions.assertEquals(HttpStatus.OK.value(), helper.tryRegister(testRegisterInfo));
        String token = helper.tryLoginAndGetToken(loginCommand);
        log.debug("토큰 스트링 {}", token);
        Assertions.assertNotNull(token);
        assertBearerToken(token);
    }


    @Test
    void unregister() {
        helper.tryRegister(testRegisterInfo);
        String authToken = helper.tryLoginAndGetToken(loginCommand);
        log.debug("토큰 스트링 {}", authToken);
        Assertions.assertEquals(HttpStatus.OK.value(), helper.tryUnregister(authToken));
    }

    private void assertBearerToken(String token) {
        String tokenType = token.split(" ")[0];
        Assertions.assertEquals("BEARER", tokenType);
    }

}
