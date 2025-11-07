package com.example.api.tests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public class BaseApiTest {
    
    protected static final String BASE_URL = "https://fakerapi.it/api/v2";
    protected static final String PRODUCTS_ENDPOINT = "/products?_quantity=1&_taxes=12&_categories_type=uuid";
    
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        
        // Configure RestAssured to use Gson instead of Jackson
        RestAssured.config = RestAssuredConfig.config()
                .objectMapperConfig(objectMapperConfig()
                    .defaultObjectMapperType(ObjectMapperType.GSON));
        
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    
    protected RequestSpecification givenRequest() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
    }
    
    protected Response getProducts() {
        return givenRequest()
                .when()
                .get(PRODUCTS_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }
}