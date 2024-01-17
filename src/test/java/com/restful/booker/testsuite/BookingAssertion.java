package com.restful.booker.testsuite;

import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class BookingAssertion extends TestBase {
    static ValidatableResponse response;

   @Test
    public static void bookingAssertion() {
        response = given()
                .when()
                .get("/104")
                .then().statusCode(200)
                //Verify checkin is on 2018-01-01
                .body("bookingdates.checkin", equalTo("2018-01-01"))
                //Verify totalprice is greater than  100
                .body("totalprice", greaterThan(100))
                //Verify that the lastname is "Doe"
                .body("lastname", equalTo("Doe"))
                //Verify that the additionalneeds is Extra pillows please
                .body("additionalneeds", equalTo("Extra pillows please"));
    }
}
