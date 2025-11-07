package com.example.api.testdata;
import com.example.api.models.ApiResponse;
import com.example.api.models.Image;
import com.example.api.models.Product;
import java.util.Arrays;
import java.util.List;

public class TestDataBuilder {
    
    public static ApiResponse createExpectedApiResponse() {
        ApiResponse response = new ApiResponse();
        response.setStatus("OK");
        response.setCode(200);
        response.setTotal(1);
        response.setData(Arrays.asList(createSampleProduct()));
        return response;
    }
    
    public static Product createSampleProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Esse fuga quo aut id.");
        product.setDescription("Placeat suscipit iusto quis corporis et aut. Sit eaque consequuntur nemo et eveniet. Deleniti architecto error eos ratione qui. Dignissimos non dicta non in sed.");
        product.setEan("4988843021351");
        product.setUpc("361235060105");
        product.setImage("http://placeimg.com/640/480/tech");
        product.setNetPrice(4433649.85); // Fixed: using setNetPrice instead of setNet_price
        product.setTaxes(12);
        product.setPrice(4965687.83);
        product.setImages(createSampleImages());
        product.setCategories(createSampleCategories());
        product.setTags(createSampleTags());
        return product;
    }
    
    private static List<Image> createSampleImages() {
        return Arrays.asList(
            new Image("Et et voluptates vel.", 
                     "Quos dolores saepe magnam impedit. Facilis harum inventore omnis temporibus.", 
                     "https://picsum.photos/640/480"),
            new Image("Deserunt rem impedit impedit.", 
                     "Reprehenderit autem quia molestias consequatur iure harum. Placeat tempore incidunt corrupti modi. Cum doloremque vero itaque.", 
                     "https://picsum.photos/640/480"),
            new Image("Quo officiis quia nisi.", 
                     "Rerum error ipsam quasi qui aperiam. Quia delectus sit quasi consequatur ratione. Tenetur eius ad dolorem ex harum sunt vel.", 
                     "https://picsum.photos/640/480")
        );
    }
    
    private static List<String> createSampleCategories() {
        return Arrays.asList(
            "2d895a57-187a-375c-a942-846e02911aec",
            "a66e1bda-04b3-340b-b61c-ec3319b0eb0e",
            "bb5aba4b-efa3-3cd3-bfc4-30ee6e90de93"
        );
    }
    
    private static List<String> createSampleTags() {
        return Arrays.asList(
            "laudantium", 
            "consequatur", 
            "repudiandae", 
            "deserunt", 
            "perferendis"
        );
    }
}