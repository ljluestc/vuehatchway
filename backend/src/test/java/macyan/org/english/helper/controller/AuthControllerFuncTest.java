package macyan.org.english.helper.controller;

import io.restassured.RestAssured;
import lombok.SneakyThrows;
import macyan.org.english.helper.EnglishHelperApplicationFunctionalTest;
import net.minidev.json.JSONObject;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yan Matskevich
 * @since 20.04.2021
 */
public class AuthControllerFuncTest extends EnglishHelperApplicationFunctionalTest {

    private static final String AUTH_API_PREFIX = AuthController.class.getAnnotation(RequestMapping.class).value()[0];
    private static final String USERNAME_PARAM = "username";
    private static final String EMAIL_PARAM = "email";
    private static final String PASS_PARAM = "password";
    private static final String COOKIE_REFRESH_TOKEN_NAME = "refreshToken";
    private static final String BODY_ACCESS_TOKEN_NAME = "accessToken";

    protected static final String NEW_USER_NAME = "newUser";
    protected static final String NEW_USER_EMAIL = "new@test.com";
    protected static final String NEW_USER_PASS = "new12345";

    @Test
    public void authApiSigninAccessible() {
        JSONObject requestParams = new JSONObject();
        requestParams.put(USERNAME_PARAM, USER_NAME);
        requestParams.put(PASS_PARAM, USER_PASS);

        RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(requestParams.toJSONString())
            .post(AUTH_API_PREFIX + "/signin")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();
    }

    @Test
    public void authApiSigninNotExistingUser() {
        JSONObject requestParams = new JSONObject();
        requestParams.put(USERNAME_PARAM, "notExisting");
        requestParams.put(PASS_PARAM, USER_PASS);

        RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(requestParams.toJSONString())
            .post(AUTH_API_PREFIX + "/signin")
        .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED)
            .extract().response();
    }

    @SneakyThrows
    @Test
    public void authApiRefreshRefreshCall() {
        JSONObject requestParams = new JSONObject();
        requestParams.put(USERNAME_PARAM, USER_NAME);
        requestParams.put(PASS_PARAM, USER_PASS);

        var response = RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(requestParams.toJSONString())
            .post(AUTH_API_PREFIX + "/signin")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();

        var token = response.getCookies().get(COOKIE_REFRESH_TOKEN_NAME);
        Thread.sleep(properties.getSecurity().getJwtExpirationMs());

        var refreshResponse = RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .cookie(COOKIE_REFRESH_TOKEN_NAME, token)
            .post(AUTH_API_PREFIX + "/refresh")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();

        var refreshResponseJson = refreshResponse.getBody().jsonPath();
        assertEquals(USER_NAME, refreshResponseJson.get("username"));
        assertEquals(USER_EMAIL, refreshResponseJson.get("email"));
        assertNotNull(refreshResponseJson.get("accessToken"));
    }

    @SneakyThrows
    @Test
    public void authApiRefreshRefreshCallExpired() {
        JSONObject requestParams = new JSONObject();
        requestParams.put(USERNAME_PARAM, USER_NAME);
        requestParams.put(PASS_PARAM, USER_PASS);

        var response = RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(requestParams.toJSONString())
            .post(AUTH_API_PREFIX + "/signin")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();

        var token = response.getCookies().get(COOKIE_REFRESH_TOKEN_NAME);
        Thread.sleep(properties.getSecurity().getJwtRefreshExpirationMs());

        RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .cookie(COOKIE_REFRESH_TOKEN_NAME, token)
            .post(AUTH_API_PREFIX + "/refresh")
        .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void authApiSignupAccessible() {
        JSONObject signinRequest = new JSONObject();
        signinRequest.put(USERNAME_PARAM, ADMIN_NAME);
        signinRequest.put(PASS_PARAM, ADMIN_PASS);

        var response = RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(signinRequest.toJSONString())
            .post(AUTH_API_PREFIX + "/signin")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();

        var token = response.body().jsonPath().get(BODY_ACCESS_TOKEN_NAME);
        JSONObject signupRequest = new JSONObject();
        signupRequest.put(USERNAME_PARAM, NEW_USER_NAME);
        signupRequest.put(EMAIL_PARAM, NEW_USER_EMAIL);
        signupRequest.put(PASS_PARAM, NEW_USER_PASS);

        RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(signupRequest.toJSONString())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .post(AUTH_API_PREFIX + "/signup")
        .then()
            .statusCode(HttpStatus.SC_OK);

        JSONObject signinNewUserRequest = new JSONObject();
        signinNewUserRequest.put(USERNAME_PARAM, NEW_USER_NAME);
        signinNewUserRequest.put(PASS_PARAM, NEW_USER_PASS);

        RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(signinNewUserRequest.toJSONString())
            .post(AUTH_API_PREFIX + "/signin")
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void authApiSignupNotAvailableForNotAdmin() {
        JSONObject signinRequest = new JSONObject();
        signinRequest.put(USERNAME_PARAM, USER_NAME);
        signinRequest.put(PASS_PARAM, USER_PASS);

        var response = RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(signinRequest.toJSONString())
            .post(AUTH_API_PREFIX + "/signin")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();

        var token = response.body().jsonPath().get(BODY_ACCESS_TOKEN_NAME);
        JSONObject signupRequest = new JSONObject();
        signupRequest.put(USERNAME_PARAM, NEW_USER_NAME);
        signupRequest.put(EMAIL_PARAM, NEW_USER_EMAIL);
        signupRequest.put(PASS_PARAM, NEW_USER_PASS);

        RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(signupRequest.toJSONString())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .post(AUTH_API_PREFIX + "/signup")
        .then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void authApiSignupDuplicateCredentials() {
        JSONObject signinRequest = new JSONObject();
        signinRequest.put(USERNAME_PARAM, ADMIN_NAME);
        signinRequest.put(PASS_PARAM, ADMIN_PASS);

        var response = RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(signinRequest.toJSONString())
            .post(AUTH_API_PREFIX + "/signin")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().response();

        var token = response.body().jsonPath().get(BODY_ACCESS_TOKEN_NAME);
        JSONObject signupRequestWithDuplicateName = new JSONObject();
        signupRequestWithDuplicateName.put(USERNAME_PARAM, USER_NAME);
        signupRequestWithDuplicateName.put(EMAIL_PARAM, NEW_USER_EMAIL);
        signupRequestWithDuplicateName.put(PASS_PARAM, NEW_USER_PASS);

        RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(signupRequestWithDuplicateName.toJSONString())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .post(AUTH_API_PREFIX + "/signup")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);

        JSONObject signupRequestWithDuplicateEmail = new JSONObject();
        signupRequestWithDuplicateEmail.put(USERNAME_PARAM, NEW_USER_NAME);
        signupRequestWithDuplicateEmail.put(EMAIL_PARAM, USER_EMAIL);
        signupRequestWithDuplicateEmail.put(PASS_PARAM, NEW_USER_PASS);

        RestAssured.given()
            .contentType("application/json")
            .log().all()
        .when()
            .body(signupRequestWithDuplicateEmail.toJSONString())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .post(AUTH_API_PREFIX + "/signup")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

}
