package model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import model.entity.enums.PaymentType;
import model.entity.enums.TransactionType;

@Data
@NoArgsConstructor
@SuperBuilder

public class Bank {
    private int id;
    private Customer customer;
    private Payment payment;
    private int amount;
}
