package com.example.api.models;
import java.util.List;

public class ApiResponse {
    private String status;
    private int code;
    private String locale;
    private Object seed;
    private int total;
    private List<Product> data;
    
    // Default constructor
    public ApiResponse() {}
    
    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    
    public String getLocale() { return locale; }
    public void setLocale(String locale) { this.locale = locale; }
    
    public Object getSeed() { return seed; }
    public void setSeed(Object seed) { this.seed = seed; }
    
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    
    public List<Product> getData() { return data; }
    public void setData(List<Product> data) { this.data = data; }
    
    @Override
    public String toString() {
        return "ApiResponse{" +
                "status='" + status + '\'' +
                ", code=" + code +
                ", total=" + total +
                ", data=" + data +
                '}';
    }
}