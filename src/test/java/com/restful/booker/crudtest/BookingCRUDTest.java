package com.restful.booker.crudtest;

import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BookingCRUDTest extends TestBase {

    static int bookingID;

    @Test
    public void test001GetAllBookingIDs()
    {
        Response response = given()
                        .when()
                        .get();
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void test002GetSingleBooking()
    {
        Response response = given()
                        .when()
                        .get("/48");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void test003CreateBooking()
    {
        String firstname = TestUtils.getRandomValue() + "Prime";
        String lastname = TestUtils.getRandomValue() + "Testing";

        HashMap<String,String>bookingdates = new HashMap();
        bookingdates.put("checkin" , "2024-03-04");
        bookingdates.put("checkout" , "2024-04-04");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(123);
        bookingPojo.setDepositpaid(true);
        bookingPojo.setAdditionalneeds("Dinner");
        bookingPojo.setBookingdates(bookingdates);

        Response response =
                given()
                        .header("Content-Type","application/json")
                        .header("Authorization" , "Bearer c3cf9df32fa20c9" )
                        .body(bookingPojo)
                        .when()
                        .post();
        response.prettyPrint();
        response.then().statusCode(200);
        bookingID =response.then().extract().path("bookingid");
        System.out.println("ID = " + bookingID);
    }

    @Test
    public void test004UpdateBooking() {

        String firstname = TestUtils.getRandomValue() + "JAVA";
        String lastname = TestUtils.getRandomValue() + "Testing";

        HashMap<String,String>bookingdates = new HashMap();
        bookingdates.put("checkin" , "2024-03-04");
        bookingdates.put("checkout" , "2024-04-04");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(123);
        bookingPojo.setDepositpaid(true);
        bookingPojo.setAdditionalneeds("Dinner & bed");
        bookingPojo.setBookingdates(bookingdates);

        Response response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Cookie" , "token=c3cf9df32fa20c9" )
                        .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        //.auth().preemptive().basic("admin", "password123")
                        .body(bookingPojo)
                        .when()
                        .put("/" + bookingID);
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void test005PartialUpdateBooking() {
        String firstname = TestUtils.getRandomValue() + "API";
        String lastname = TestUtils.getRandomValue() + "Testing";

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);

        Response response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .body(bookingPojo)
                        .when()
                        .patch("/" + bookingID);
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void test006DeleteBooking() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .delete("/" + bookingID);
        response.then().statusCode(201);
        response.prettyPrint();
    }

    @Test
    public void test007PingCheck() {
        Response response = given().log().all()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .get("https://restful-booker.herokuapp.com/ping");
        response.prettyPrint();
        response.then().statusCode(201);
    }
}
