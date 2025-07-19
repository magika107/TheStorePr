import model.entity.Order;
import model.entity.OrderItem;
import model.entity.Product;

public class OrderItemTest {
    public static void main(String[] args) {
        Product product = Product.builder().id(101).title("Laptop").buyPrice(15000).build();
        Order order = Order.builder().id(202).build();
        OrderItem orderItem = OrderItem
                .builder()
                .id(1)
                .product(product)
                .quantity(2)
                .price(14000)
                .order(order)
                .build();

        System.out.println(orderItem);
        System.out.println("Total:" + orderItem.getItemTotal());
    }
}
