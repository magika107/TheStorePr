package tools;

import model.entity.*;
import model.entity.enums.Gender;
import model.entity.enums.OrderType;
import model.entity.enums.PaymentType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityMapper {


    public static User userMapper(ResultSet resultSet) throws SQLException {
        return User
                .builder()
                .id(resultSet.getInt("ID"))
                .gender(Gender.valueOf(resultSet.getString("GENDER")))
                .name(resultSet.getString("NAME"))
                .family(resultSet.getString("FAMILY"))
                .birthDate(resultSet.getDate("BIRTH_DATE") == null ? null : resultSet.getDate("BIRTH_DATE").toLocalDate())
                .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                .username(resultSet.getString("USERNAME"))
                .password(resultSet.getString("PASSWORD"))
                .build();
    }

    public static Customer customerMapper(ResultSet resultSet) throws SQLException {
        return Customer
                .builder()
                .id(resultSet.getInt("ID"))
                .gender(Gender.valueOf(resultSet.getString("GENDER")))
                .name(resultSet.getString("NAME"))
                .family(resultSet.getString("FAMILY"))
                .birthDate(resultSet.getDate("BIRTH_DATE") == null ? null : resultSet.getDate("BIRTH_DATE").toLocalDate())
                .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                .build();
    }

    public static Product productMapper(ResultSet resultSet) throws SQLException {
        return Product
                .builder()
                .id(resultSet.getInt("ID"))
                .title(resultSet.getString("TITLE"))
                .brand(resultSet.getString("BRAND"))
                .model(resultSet.getString("MODEL"))
                .serialNumber(resultSet.getString("SERIALNUMBER"))
                .buyPrice(resultSet.getInt("BUY_PRICE"))
                .build();
    }

    public static Payment paymentMapper(ResultSet resultSet) throws SQLException {
        return Payment
                .builder()
                .id(resultSet.getInt("ID"))
                .paymentType(PaymentType.valueOf(resultSet.getString("PAYMENT_TYPE")))
                .order(Order.builder().id(resultSet.getInt("ORDER_ID")).build())
                .amount(resultSet.getInt("AMOUNT"))
                .customer(Customer.builder().id(resultSet.getInt("CUSTOMER_ID")).build())
                .user(User.builder().id(resultSet.getInt("USER_ID")).build())
                .paymentTime(resultSet.getTimestamp("PAYMENT_TIME").toLocalDateTime())
                .build();
    }

    public static OrderItem orderItemMapper(ResultSet resultSet) throws SQLException {
        return OrderItem
                .builder()
                .id(resultSet.getInt("ID"))
                .product(Product.builder().id(resultSet.getInt("product_id")).build())
                .quantity(resultSet.getInt("quantity"))
                .price(resultSet.getInt("price"))
                .order(Order.builder().id(resultSet.getInt("order_id")).build())
                .build();

    }

    public static Order orderMapper(ResultSet resultSet) throws SQLException {
        return Order
                .builder()
                .id(resultSet.getInt("id"))
                .orderSerial(resultSet.getString("order_serial"))
                .buyer(Customer.builder().id(resultSet.getInt("customer_id")).build())
                .user(User.builder().id(resultSet.getInt("user_id")).build())
                .orderType(OrderType.valueOf(resultSet.getString("order_type").toUpperCase()))
                .discount(resultSet.getInt("discount"))
                .pureAmount(resultSet.getInt("pure_amount"))
                .orderTime(resultSet.getTimestamp("order_time") == null ? null :
                        resultSet.getTimestamp("order_time").toLocalDateTime())
                .build();
    }

    public static Bank bankMapper(ResultSet resultSet) throws SQLException {
        return Bank.builder()
                .id(resultSet.getInt("id"))
                .amount(resultSet.getInt("amount"))
                .customer(Customer.builder()
                        .id(resultSet.getInt("customer_id"))
                        .build())
                .payment(Payment.builder()
                        .id(resultSet.getInt("payment_id"))
                        .build())
                .build();
    }

    public static Inventory inventoryMapper(ResultSet resultSet) throws SQLException {
        return Inventory.builder()
                .id(resultSet.getInt("id"))
                .quantity(resultSet.getInt("quantity"))
                .product(Product.builder()
                        .id(resultSet.getInt("product_id"))
                        .build())
                .build();
    }
}
