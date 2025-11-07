package com.example.api.models;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private String description;
    private String ean;
    private String upc;
    private String image;
    private List<Image> images;
    
    @SerializedName("net_price")
    private Double netPrice;
    
    private Integer taxes;
    private Double price;
    private List<String> categories;
    private List<String> tags;
    
    // Default constructor
    public Product() {}
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getEan() { return ean; }
    public void setEan(String ean) { this.ean = ean; }
    
    public String getUpc() { return upc; }
    public void setUpc(String upc) { this.upc = upc; }
    
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    
    public List<Image> getImages() { return images; }
    public void setImages(List<Image> images) { this.images = images; }
    
    public Double getNetPrice() { return netPrice; }
    public void setNetPrice(Double netPrice) { this.netPrice = netPrice; }
    
    public Integer getTaxes() { return taxes; }
    public void setTaxes(Integer taxes) { this.taxes = taxes; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", netPrice=" + netPrice +
                ", price=" + price +
                '}';
    }
}