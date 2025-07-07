package model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import model.entity.enums.OrderType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder

public class Order implements Serializable {
    private int id;
    private String orderSerial;
    private Customer buyer;
    private User user;
    private OrderType orderType;

    List<OrderItem> orderItemList;

    private int discount;
    private int pureAmount;

    List<Payment> paymentList;

    private LocalDateTime orderTime;

    public int getOrderTotal(){
        int total=0;
        for (OrderItem orderItem : orderItemList) {
            total += orderItem.getItemTotal();
        }

        pureAmount = total - discount;
        return  total;
    }

    public void addItem(OrderItem orderItem){
        if(orderItemList == null){
            orderItemList = new ArrayList<>();
        }
        orderItemList.add(orderItem);
    }

    public void addPayment(Payment payment){
        if(paymentList == null){
            paymentList = new ArrayList<>();
        }
        paymentList.add(payment);
    }
}