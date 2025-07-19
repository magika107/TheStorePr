package model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder

public class Inventory {
    private int id;
    private Product product;
    private int quantity;
}
