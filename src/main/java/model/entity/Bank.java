package model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import model.entity.enums.TransactionType;

@Data
@NoArgsConstructor
@SuperBuilder

public class Bank {
    private Customer customer;
    private int amount;
    private TransactionType transactionType;
}
