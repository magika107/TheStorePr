import model.entity.*;
import model.entity.enums.OrderType;
import model.entity.enums.PaymentType;

import java.time.LocalDateTime;

public class OrderTest {
    public static void main(String[] args) {
        Customer customer = Customer.builder().id(1).build();
        User user = User.builder().id(255).build();
        Product product1 = Product.builder().id(989).title("tablet").brand("samsung").buyPrice(5000).build();
        Product product2 = Product.builder().id(7878).title("mobile").brand("apple").buyPrice(11300).build();
        OrderItem item1 = OrderItem.builder().product(product1).quantity(5).price(450).build();
        OrderItem item2 = OrderItem.builder().product(product2).quantity(8).price(250).build();

        Order order = Order.builder()
                .id(1600)
                .orderSerial("59595")
                .buyer(customer)
                .user(user)
                .orderType(OrderType.Sell)
                .orderTime(LocalDateTime.now())
                .discount(10)
                .build();

        order.addItem(item1);
        order.addItem(item2);

        Payment payment = Payment.builder()
                .customer(customer)
                .user(user)
                .amount(1550)
                .order(order)
                .paymentType(PaymentType.Cash)
                .paymentTime(LocalDateTime.now())
                .build();

        order.addPayment(payment);

        System.out.println(order);
//        System.out.println(order.getOrderTotal());
//        System.out.println(order.getDiscount());
//        System.out.println(order.getPureAmount());

//        Test error
    }

}

