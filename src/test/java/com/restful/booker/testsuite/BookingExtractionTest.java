package com.restful.booker.testsuite;

import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BookingExtractionTest extends TestBase {

    static ValidatableResponse response;

    @Test
    public static void bookingExtraction() {
        response = given()
                .when()
                .get("/104")
                .then().statusCode(200);

        String lastName = response.extract().path("lastname");
        System.out.println("The value of lastname is  : " + lastName);

        int totalPrice = response.extract().path("totalprice");
        System.out.println("The total price is : " + totalPrice);

        boolean depositPaid = response.extract().path("depositpaid");
        System.out.println("Is deposit paid = " + depositPaid);

        HashMap<String, String> bookinDates = response.extract().path("bookingdates");
        System.out.println("The Booking Dates are : " + bookinDates);
    }
}