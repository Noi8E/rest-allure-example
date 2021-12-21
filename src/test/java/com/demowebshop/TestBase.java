package com.demowebshop;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {


    public final String USER_EMAIL = "mail@test.com";
    public final String USER_PASSWORD = "password";
    public final String HARD_COOKIE = "a578b8a0-72b1-4a15-91e8-65255bc54583";
    public static final String baseUrl = "http://demowebshop.tricentis.com";


    @BeforeAll
    static void setUp() {

        Configuration.browserSize = "600x600";


    }

}
