import model.entity.Customer;
import model.entity.Order;
import model.entity.Payment;
import model.entity.User;
import model.entity.enums.PaymentType;

import java.time.LocalDateTime;


public class PaymentTest {
    public static void main(String[] args) throws Exception {

        Order order = Order.builder().id(54).build();
        Customer customer = Customer.builder().id(97).build();
        User user = User.builder().id(74).build();

        Payment payment = Payment
                .builder()
                .paymentType(PaymentType.Cash)
                .order(order)
                .amount(46789)
                .customer(customer)
                .user(user)
                .paymentTime(LocalDateTime.now())
                .build();

        System.out.println(payment);
    }
}

