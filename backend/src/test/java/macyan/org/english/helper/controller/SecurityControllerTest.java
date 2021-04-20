package macyan.org.english.helper.controller;

import io.restassured.RestAssured;
import macyan.org.english.helper.EnglishHelperApplicationFunctionalTest;
import org.apache.http.HttpStatus;
import org.junit.Test;

/**
 * @author Yan Matskevich
 * @since 20.04.2021
 */
public class SecurityControllerTest extends EnglishHelperApplicationFunctionalTest {

    public static final String TRANSLATION_API_PREFIX = "api/v1/translation/";

    @Test
    public void securedApiShouldReactWithUnauthorized_getPhrases() {
        RestAssured.given()
            .when()
            .get(TRANSLATION_API_PREFIX + "phrases")
            .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void securedApiShouldReactWithUnauthorized_getWords() {
        RestAssured.given()
            .when()
            .get(TRANSLATION_API_PREFIX + "words")
            .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void securedApiShouldReactWithUnauthorized_getPhrasalVerbs() {
        RestAssured.given()
            .when()
            .get(TRANSLATION_API_PREFIX + "phrasal-verbs")
            .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void securedApiShouldGiveHttp200WhenAuthorized_getPhrases() {
        RestAssured.given()
            .auth().basic("foo", "bar")
            .when()
            .get(TRANSLATION_API_PREFIX + "phrases")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void securedApiShouldGiveHttp200WhenAuthorized_getWords() {
        RestAssured.given()
            .auth().basic("foo", "bar")
            .when()
            .get(TRANSLATION_API_PREFIX + "phrasal-verbs")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void securedApiShouldGiveHttp200WhenAuthorized_getPhrasalVerbs() {
        RestAssured.given()
            .auth().basic("foo", "bar")
            .when()
            .get(TRANSLATION_API_PREFIX + "words")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }
}