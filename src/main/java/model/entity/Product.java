package model.entity;


import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString

public class Product implements Serializable {
    private int id;
    private String title;
    private String brand;
    private String model;
    private String serialNumber;
    private int buyPrice;

    public String getProductInfo() {
        return String.format("%s (%s) - %s : %s ", title, brand, model, serialNumber);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}