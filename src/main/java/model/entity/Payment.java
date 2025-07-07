package model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import model.entity.enums.PaymentType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder

public class Payment {
    private int id;
    private PaymentType paymentType;
    private Order order;
    private int amount;
    private Customer customer;
    private User user;
    private LocalDateTime paymentTime;
}
