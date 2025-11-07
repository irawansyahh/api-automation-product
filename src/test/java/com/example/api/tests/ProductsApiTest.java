package com.example.api.tests;
import com.example.api.models.ApiResponse;
import com.example.api.models.Image;
import com.example.api.models.Product;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductsApiTest extends BaseApiTest {

    @Test
    public void testGetProducts_ShouldReturnValidResponseStructure() {
        Response response = getProducts();
        
        // Verify HTTP status code
        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        
        // Parse response to POJO
        ApiResponse apiResponse = response.as(ApiResponse.class);
        
        // Verify response structure
        assertAll("API Response Structure",
            () -> assertEquals("OK", apiResponse.getStatus(), "Status should be OK"),
            () -> assertEquals(200, apiResponse.getCode(), "Code should be 200"),
            () -> assertNotNull(apiResponse.getData(), "Data should not be null"),
            () -> assertEquals(1, apiResponse.getData().size(), "Should have exactly 1 product")
        );
    }
    
    @Test
    public void testGetProducts_ProductDataValidation() {
        Response response = getProducts();
        ApiResponse apiResponse = response.as(ApiResponse.class);
        Product product = apiResponse.getData().get(0);
        
        // Verify product basic information
        assertAll("Product Basic Information",
            () -> assertTrue(product.getId() > 0, "Product ID should be positive"),
            () -> assertNotNull(product.getName(), "Product name should not be null"),
            () -> assertFalse(product.getName().isEmpty(), "Product name should not be empty"),
            () -> assertNotNull(product.getDescription(), "Description should not be null"),
            () -> assertNotNull(product.getEan(), "EAN should not be null"),
            () -> assertNotNull(product.getUpc(), "UPC should not be null"),
            () -> assertNotNull(product.getImage(), "Main image URL should not be null")
        );
        
        // Verify pricing information - handle potential nulls
        assertAll("Product Pricing",
            () -> assertNotNull(product.getNetPrice(), "Net price should not be null"),
            () -> assertTrue(product.getNetPrice() >= 0, "Net price should be non-negative"),
            () -> assertNotNull(product.getTaxes(), "Taxes should not be null"),
            () -> assertEquals(12, product.getTaxes(), "Taxes should be 12 as specified in query param"),
            () -> assertNotNull(product.getPrice(), "Price should not be null"),
            () -> assertTrue(product.getPrice() >= 0, "Price should be non-negative")
        );
    }
    
    @Test
    public void testGetProducts_ImagesValidation() {
        Response response = getProducts();
        ApiResponse apiResponse = response.as(ApiResponse.class);
        Product product = apiResponse.getData().get(0);
        
        // Verify images array
        assertNotNull(product.getImages(), "Images should not be null");
        assertFalse(product.getImages().isEmpty(), "Images should not be empty");
        
        // Verify all images have required fields
        for (int i = 0; i < product.getImages().size(); i++) {
            Image image = product.getImages().get(i);
            assertAll("Image " + i + " Validation",
                () -> assertNotNull(image.getTitle(), "Image title should not be null"),
                () -> assertNotNull(image.getDescription(), "Image description should not be null"),
                () -> assertNotNull(image.getUrl(), "Image URL should not be null"),
                () -> assertTrue(image.getUrl().startsWith("http"), "Image URL should be valid")
            );
        }
    }
    
    @Test
    public void testGetProducts_CategoriesAndTagsValidation() {
        Response response = getProducts();
        ApiResponse apiResponse = response.as(ApiResponse.class);
        Product product = apiResponse.getData().get(0);
        
        // Verify categories (UUID format as specified in query param)
        assertNotNull(product.getCategories(), "Categories should not be null");
        assertFalse(product.getCategories().isEmpty(), "Categories should not be empty");
        
        // Verify UUID format for categories
        for (String category : product.getCategories()) {
            assertTrue(category.matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"),
                    "Category should be in UUID format: " + category);
        }
        
        // Verify tags
        assertNotNull(product.getTags(), "Tags should not be null");
        assertFalse(product.getTags().isEmpty(), "Tags should not be empty");
        
        // Verify tags are strings
        for (String tag : product.getTags()) {
            assertNotNull(tag, "Tag should not be null");
            assertFalse(tag.isEmpty(), "Tag should not be empty");
        }
    }
    
    @Test
    public void testGetProducts_WithRestAssuredDirectValidation() {
        getProducts()
            .then()
            .assertThat()
            .statusCode(200)
            .contentType("application/json")
            .body("status", equalTo("OK"))
            .body("code", equalTo(200))
            .body("data", hasSize(1))
            .body("data[0].id", greaterThan(0))
            .body("data[0].name", not(emptyOrNullString()))
            .body("data[0].description", not(emptyOrNullString()))
            .body("data[0].ean", not(emptyOrNullString()))
            .body("data[0].upc", not(emptyOrNullString()))
            .body("data[0].image", not(emptyOrNullString()))
            .body("data[0].net_price", notNullValue())  // Use JSON field name: net_price
            .body("data[0].net_price", greaterThanOrEqualTo(0f))
            .body("data[0].taxes", equalTo(12))
            .body("data[0].price", notNullValue())
            .body("data[0].price", greaterThanOrEqualTo(0f))
            .body("data[0].images", hasSize(greaterThan(0)))
            .body("data[0].categories", hasSize(greaterThan(0)))
            .body("data[0].tags", hasSize(greaterThan(0)));
    }
    
    @Test
    public void testPriceCalculation() {
        Response response = getProducts();
        ApiResponse apiResponse = response.as(ApiResponse.class);
        Product product = apiResponse.getData().get(0);
        
        // Verify price calculation: price = netPrice * (1 + taxes/100)
        double expectedPrice = product.getNetPrice() * (1 + product.getTaxes() / 100.0);
        double actualPrice = product.getPrice();
        double tolerance = 0.01;
        
        assertEquals(expectedPrice, actualPrice, tolerance,
                String.format("Price calculation mismatch. Expected: %.2f, Actual: %.2f", 
                         expectedPrice, actualPrice));
    }
    
    @Test
    public void testResponseTime() {
        long startTime = System.currentTimeMillis();
        Response response = getProducts();
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        
        assertTrue(responseTime < 10000, "Response time should be less than 10 seconds. Actual: " + responseTime + "ms");
        assertEquals(200, response.getStatusCode(), "Status code should be 200 even when checking response time");
    }
    
    @Test
    public void testProductQuantity() {
        Response response = getProducts();
        ApiResponse apiResponse = response.as(ApiResponse.class);
        
        // Should have exactly 1 product as specified in query param
        assertEquals(1, apiResponse.getData().size(), "Should return exactly 1 product as specified in _quantity=1");
    }
}