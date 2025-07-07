import model.entity.OrderItem;
import model.entity.*;
import model.entity.enums.OrderType;
import model.entity.enums.PaymentType;

import java.time.LocalDateTime;

public class MyTest {
    public static void main(String[] args) {
        Product product1 = Product.builder().title("mobile").build();
        Product product2 = Product.builder().title("laptop").build();

        User user = User.builder().name("ali").family("alipour").build();
        Customer customer = Customer.builder().name("mohsen").family("akbari").build();

        Order order =
                Order
                        .builder()
                        .id(1)
                        .orderSerial("Ab12/17")
                        .buyer(customer)
                        .user(user)
                        .orderTime(LocalDateTime.now())
                        .discount(100)
                        .orderType(OrderType.Sell)
                        .build();

        OrderItem orderItem1 = OrderItem.builder().product(product1).quantity(2).price(100).build();
        OrderItem orderItem2 = OrderItem.builder().product(product2).quantity(3).price(800).build();
        order.addItem(orderItem1);
        order.addItem(orderItem2);

        Payment payment1 = Payment.builder().paymentTime(LocalDateTime.now()).paymentType(PaymentType.Card).amount(1000).build();
        Payment payment2 = Payment.builder().paymentTime(LocalDateTime.now()).paymentType(PaymentType.Cash).amount(1600).build();
        order.addPayment(payment1);
        order.addPayment(payment2);

        System.out.println(order.getOrderTotal());

        System.out.println(order);
    }
}
