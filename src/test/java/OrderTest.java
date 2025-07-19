import model.entity.*;
import model.entity.enums.Gender;
import model.entity.enums.OrderType;
import model.entity.enums.PaymentType;
import model.entity.enums.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderTest {
    public static void main(String[] args) {

        Customer customer = Customer.builder().id(1).gender(Gender.Male).name("karim").family("ghanbari").birthDate(LocalDate.now()).phoneNumber("564646").build();
        User user = User.builder().id(255).gender(Gender.Male).name("hasan").family("hasni").birthDate(LocalDate.now()).phoneNumber("0251511").build();
        Product product1 = Product.builder().id(989).title("tablet").brand("samsung").model("iui").buyPrice(5000).build();
        Product product2 = Product.builder().id(7878).title("mobile").brand("apple").model("ere").buyPrice(11300).build();
        OrderItem item1 = OrderItem.builder().id(65).product(product1).quantity(5).price(450).build();
        OrderItem item2 = OrderItem.builder().product(product2).quantity(8).price(250).build();

        Order order = Order.builder()
                .id(1600)
                .orderSerial("59595")
                .buyer(customer)
                .user(user)
                .orderType(OrderType.Sell)
                .discount(10)
                .pureAmount(2)
                .orderTime(LocalDateTime.now())
                .build();

//        System.out.println(order);
//        is Ok
        order.addItem(item1);
        order.addItem(item2);
        System.out.println(item1);
        System.out.println(item2);
//        is Ok


        Payment payment = Payment
                .builder()
                .id(1600)
                .transactionType(TransactionType.Payment)
                .paymentType(PaymentType.Cash)
                .order(order)
                .amount(1550)
                .customer(customer)
                .user(user)
                .paymentTime(LocalDateTime.now())
                .build();


//        System.out.println(payment);
//        is Ok

      order.addPayment(payment);

//


//        System.out.println(order);
//        System.out.println(order);
//        System.out.println(order.getOrderTotal());


//        System.out.println(order.getDiscount());
//        System.out.println(order.getPureAmount());

//      todo  Test error
    }


}

