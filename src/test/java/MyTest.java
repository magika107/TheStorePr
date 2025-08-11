import model.entity.OrderItem;
import model.entity.*;
import model.entity.enums.OrderType;
import model.entity.enums.PaymentType;

import java.time.LocalDateTime;

public class MyTest {
    public static void main(String[] args) {
        Product product1 = Product.builder().title("mobile").build();
        Product product2 = Product.builder().title("laptop").build();

        Product product3 = Product
                .builder()
                .title("headPhone")
                .brand("apple")
                .model("tr")
                .serialNumber("909089")
                .buyPrice(900000)
                .build();
//-----------------
        User user = User.builder().name("ali").family("alipour").build();
        User user2 = User.builder().name("karim").family("karimi").build();
//-----------------
        Customer customer = Customer.builder().name("mohsen").family("akbari").build();
        Customer customer2 = Customer
                .builder()
                .name("hashem")
                .family("ghanbari")
                .build();

//-----------------------------------------
        Order order =
                Order
                        .builder()
                        .id(1)
                        .orderSerial("Ab12/17")
                        .customer(customer)
                        .user(user)
                        .orderTime(LocalDateTime.now())
                        .discount(100)
                        .orderType(OrderType.Sell)
                        .build();
// --------------------------------
        Order order1 = Order
                .builder()
                .id(3)
                .orderSerial("343t")
                .customer(customer2)
                .user(user2)
                .orderTime(LocalDateTime.now())
                .discount(5500)
                .orderType(OrderType.Sell)
                .build();
//-----------------------------
        OrderItem orderItem1 = OrderItem.builder().product(product1).quantity(2).price(100).build();
        OrderItem orderItem2 = OrderItem.builder().product(product2).quantity(3).price(800).build();
        OrderItem orderItem3 = OrderItem.builder().product(product3).quantity(4).price(900).build();
        order.addItem(orderItem1);
        order.addItem(orderItem2);

        order1.addItem(orderItem3);

        Payment payment1 = Payment.builder().paymentTime(LocalDateTime.now()).paymentType(PaymentType.Card).amount(1000).build();
        Payment payment2 = Payment.builder().paymentTime(LocalDateTime.now()).paymentType(PaymentType.Cash).amount(1600).build();
        Payment payment3=Payment.builder().paymentTime(LocalDateTime.now()).paymentType(PaymentType.Cash).amount(2000).build();

        order.addPayment(payment1);
        order.addPayment(payment2);
        order.addPayment(payment3);

        System.out.println(order.getOrderTotal());

        System.out.println(order+"___________________");
        System.out.println(order1.getOrderTotal());
        System.out.println(order1+"___________________");
    }
}
