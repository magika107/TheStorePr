import model.entity.*;
import model.entity.enums.Gender;
import model.entity.enums.OrderType;
import model.entity.enums.PaymentType;
import model.entity.enums.TransactionType;
import model.service.OrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderTest {
    public static void main(String[] args) {

        Customer customer = Customer.builder().id(21).build();
        User user = User.builder().id(25).build();

//        Product product1 = Product.builder().id(989).title("tablet").brand("samsung").model("iui").buyPrice(5000).build();
//        Product product2 = Product.builder().id(7878).title("mobile").brand("apple").model("ere").buyPrice(11300).build();
//        OrderItem item1 = OrderItem.builder().id(65).product(product1).quantity(5).price(450).build();
//        OrderItem item2 = OrderItem.builder().product(product2).quantity(8).price(250).build();


        Order order = Order.builder()
                .id(1600)
                .orderSerial("59595")
                .customer(customer)
                .user(user)
                .orderType(OrderType.Sell)
                .discount(10)
                .pureAmount(2)
                .orderTime(LocalDateTime.now())
                .build();

        System.out.println(order);

        try {
            OrderService.getService().save(order);

        }catch (Exception e){
            e.printStackTrace();
        }



//        System.out.println(order);


//        System.out.println(order.getDiscount());
//        System.out.println(order.getPureAmount());

//      todo  Test error

    }


}

