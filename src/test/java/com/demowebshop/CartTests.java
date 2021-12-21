package com.demowebshop;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.registerParser;
import static org.hamcrest.Matchers.is;

public class CartTests extends TestBase {

    @Test
    @DisplayName("Можем добавить товар в корзину без куки в запросе")
    void canAddItemToCartWOCookie() {

        step("Added item to cart WO cookie", () ->
                given()
                        .filter(customLogFilter().withCustomTemplates())
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .body("addtocart_14.EnteredQuantity=1")
                        .when()
                        .log().body()
                        .log().uri()
                        .post(baseUrl + "/addproducttocart/details/14/1")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("success", is(true))
                        .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                        .body("updatetopcartsectionhtml", is("(1)"))
        );
    }

    @Test
    @DisplayName("Можем добавить товар в корзину с сохранением сессии")
    void canAddItemToCartWithCookie() {
        step("Added item to cart With cookie", () -> {
            given()
                    .filter(customLogFilter().withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .body("addtocart_14.EnteredQuantity=1")
                    .cookie(HARD_COOKIE)
                    .when()
                    .log().body()
                    .log().uri()
                    .post(baseUrl + "/addproducttocart/details/14/1")
                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("success", is(true))
                    .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                    .body("updatetopcartsectionhtml", is("(1)"));

            step("open logo for create browser-session", () ->
                    open(baseUrl + "/Themes/DefaultClean/Content/images/logo.png")
            );


            step("Inject cookie", () ->
                    getWebDriver().manage().addCookie(new Cookie("Nop.customer", HARD_COOKIE))
            );


        });

        step("Some UI Action", () -> {
        });
    }
}
