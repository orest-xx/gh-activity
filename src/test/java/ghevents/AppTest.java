package ghevents;

import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppTest {

    @Test
    public void givenNoAuthentication_whenAccessHome_thenOK() {
        int statusCode = RestAssured.get("/").statusCode();
        assertEquals(HttpStatus.OK.value(), statusCode);
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        get("/rate_limit").then().statusCode(HttpStatus.OK.value()).assertThat()
                .body("resources.core.limit", equalTo(60));
    }

    @Test
    public void whenLogResponse_thenOK() {

        when().get("/rate_limit")
                .then().log().body().statusCode(HttpStatus.OK.value());
        when().get("/events?username=user")
                .then().log().body().statusCode(HttpStatus.OK.value());
        when().get("/received_events?username=user")
                .then().log().body().statusCode(HttpStatus.OK.value());
        when().get("/users")
                .then().log().body().statusCode(HttpStatus.NOT_FOUND.value());
        when().get("/events?username=asdfasdf23")
                .then().log().body().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void givenUrl_whenJsonResponseConformsToSchema_thenCorrect() {
        get("/rate_limit").then().assertThat()
                .body(matchesJsonSchemaInClasspath("rate_0.json"));

        get("/received_events?username=user").then().assertThat()
                .body(matchesJsonSchemaInClasspath("events_0.json"));

        get("/events?username=user").then().assertThat()
                .body(matchesJsonSchemaInClasspath("events_0.json"));

    }

}
