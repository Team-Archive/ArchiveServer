package com.depromeet.archive.integration;

import com.depromeet.archive.domain.user.command.PasswordRegisterCommand;
import com.depromeet.archive.domain.user.command.LoginCommand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Value;

public class ApiHelper {

    @Value("${server.port}")
    private int PORT;
    private final static String HOST = "http://localhost";
    private final static String BASE_URI = "/api/v1/auth";
    private final static String REGISTER_URI = "/register";
    private final static String LOGIN_URI = "/login";
    private final static String UNREGISTER_URI = "/unregister";
    private final static String AUTH_HEADER_KEY = "Authorization";
    public int tryRegister(PasswordRegisterCommand command) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(command)
                .post(getRegisterUrl())
                .getStatusCode();
    }

    public String tryLoginAndGetToken(LoginCommand command) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(command)
                .post(getLoginUrl())
                .getHeader(AUTH_HEADER_KEY);
    }

    public int tryUnregister(String authToken) {
        return RestAssured
                .given()
                .header(AUTH_HEADER_KEY, authToken)
                .delete(getUnregisterUri())
                .statusCode();
    }


    private String getRegisterUrl() {
        return HOST + ":" + PORT + BASE_URI + REGISTER_URI;
    }

    private String getLoginUrl() {
        return HOST + ":" + PORT + BASE_URI + LOGIN_URI;
    }

    private String getUnregisterUri() {return HOST + ":" + PORT + BASE_URI + UNREGISTER_URI;}

}
