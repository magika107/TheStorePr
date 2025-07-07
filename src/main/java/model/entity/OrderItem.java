package model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder

public class OrderItem implements Serializable {
    private int id;
    private Product product;
    private int quantity;
    private int price;
    private Order order;

    public int getItemTotal(){
        return  quantity*price;
    }
}
