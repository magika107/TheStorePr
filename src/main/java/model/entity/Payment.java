package model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import model.entity.enums.PaymentType;
import model.entity.enums.TransactionType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString

public class Payment {
    private int id;
//
    private TransactionType transactionType;
//
    private PaymentType paymentType;
    private Order order;
    private int amount;
    private Customer customer;
    private User user;
    private LocalDateTime paymentTime;
}
