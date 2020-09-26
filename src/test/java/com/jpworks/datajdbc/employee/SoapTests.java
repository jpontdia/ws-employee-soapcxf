package com.jpworks.datajdbc.employee;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static io.restassured.RestAssured.given;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.containsString;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SoapTests {
    @LocalServerPort
    private int port;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void wsdl(){
        given()
            .when()
                .get("http://localhost:" + port +"/soap/service/employee?wsdl")
                .then()
                .assertThat()
                .body(containsString("wsdl:definitions xmlns:xsd="));
    }

    @Test
    public void listOfServices(){
        given()
                .when()
                .get("http://localhost:" + port +"/soap")
                .then()
                .assertThat()
                .body(containsString("Available SOAP services:"));
    }

    @Test
    public void getEmployeeById(){
        Resource res = resourceLoader.getResource("classpath:getEmployeeById.xml");
        given()
            .contentType("text/xml;charset=UTF-8")
            .body(asString(res))
        .when()
            .post("http://localhost:" + port +"/soap/service/employee")
        .then()
            .assertThat()
            .body(containsString("EmployeeResponse"));
    }

    @Test
    public void getEmployeeByName(){
        Resource res = resourceLoader.getResource("classpath:getEmployeeByName.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(res))
                .when()
                .post("http://localhost:" + port +"/soap/service/employee")
                .then()
                .assertThat()
                .body(containsString("EmployeesResponse"));
    }

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
