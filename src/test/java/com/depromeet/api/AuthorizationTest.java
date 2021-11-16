package com.depromeet.api;

import com.depromeet.archive.ArchiveApplication;
import com.depromeet.archive.common.exception.ResourceNotFoundException;
import com.depromeet.archive.domain.user.command.CredentialRegisterCommand;
import com.depromeet.archive.domain.user.command.LoginCommand;
import com.depromeet.archive.domain.user.entity.User;
import com.depromeet.archive.infra.user.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;

@SpringBootTest(classes = ArchiveApplication.class, webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthorizationTest {

    @Value("${server.port}")
    private int PORT;
    private final static String HOST = "http://localhost";
    private final static String BASE_URI = "/api/v1/archive";
    private final static String REGISTER_URI = "/register";
    private final static String LOGIN_URI = "/login";
    private final static String UNREGISTER_URI = "/unregister";
    private final static String AUTH_HEADER_KEY = "Authorization";

    private static CredentialRegisterCommand testRegisterInfo;
    private static LoginCommand loginCommand;

    @Autowired
    private UserRepository repository;

    @BeforeAll
    public static void initDTO() {
        testRegisterInfo = new CredentialRegisterCommand("testMail@naver.com", "abcABC123");
        loginCommand = new LoginCommand(testRegisterInfo.getMailAddress(), testRegisterInfo.getCredential());
    }

    @BeforeEach
    public void removeUser() {
        try {
            User user = repository.findUserByMail(testRegisterInfo.getMailAddress());
            repository.removeUser(user);
        } catch (ResourceNotFoundException ignored) { }
    }

    @Test
    public void registerUser() {
        Assertions.assertEquals(HttpStatus.OK.value(), tryRegister());
    }
    
    @Test
    public void registerAndLogin() {
        Assertions.assertEquals(HttpStatus.OK.value(), tryRegister());
        String token = tryLoginAndGetToken();
        Assertions.assertNotNull(token);
        assertBearerToken(token);
    }

    @Test
    public void unregister() {
        tryRegister();
        String authToken = tryLoginAndGetToken();
        Assertions.assertEquals(HttpStatus.OK.value(), tryUnregister(authToken));
    }

    private int tryRegister() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(testRegisterInfo)
                .post(getRegisterUrl())
                .getStatusCode();
    }

    private String tryLoginAndGetToken() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(loginCommand)
                .post(getLoginUrl())
                .getHeader(AUTH_HEADER_KEY);
    }

    private int tryUnregister(String authToken) {
        return RestAssured
                .given()
                .header(AUTH_HEADER_KEY, authToken)
                .delete(getUnregisterUri())
                .statusCode();
    }

    private void assertBearerToken(String token) {
        String tokenType = token.split(" ")[0];
        Assertions.assertEquals("BEARER", tokenType);
    }

    private String getRegisterUrl() {
        return HOST + ":" + PORT + BASE_URI + REGISTER_URI;
    }

    private String getLoginUrl() {
        return HOST + ":" + PORT + BASE_URI + LOGIN_URI;
    }

    private String getUnregisterUri() {return HOST + ":" + PORT + BASE_URI + UNREGISTER_URI;}
}
