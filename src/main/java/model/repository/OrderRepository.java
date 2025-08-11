package model.repository;

import model.entity.Order;
import tools.ConnectionProvider;
import tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements Repository<Order> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public OrderRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public void save(Order order) throws Exception {
        order.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "order_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO ORDERS (id, order_serial, customer_id, user_id, order_type, discount, pure_amount, order_time) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        );

        preparedStatement.setInt(1, order.getId());
        preparedStatement.setString(2, order.getOrderSerial());
        preparedStatement.setInt(3, order.getCustomer().getId());
        preparedStatement.setInt(4, order.getUser().getId());
        String orderType = order.getOrderType().name();
        String formattedOrderType = orderType.substring(0, 1).toUpperCase() + orderType.substring(1).toLowerCase();
        preparedStatement.setString(5, formattedOrderType);


        //        preparedStatement.setString(5, order.getOrderType().toString());



        preparedStatement.setInt(6, order.getDiscount());
        preparedStatement.setInt(7, order.getPureAmount());
        preparedStatement.setTimestamp(8, Timestamp.valueOf(order.getOrderTime()));
        preparedStatement.executeUpdate();

    }

    @Override
    public void edit(Order order) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE ORDERS SET order_serial = ?, customer_id = ?, user_id = ?, order_type = ?, discount = ?, pure_amount = ?, order_time = ? WHERE id = ?"
        );
        preparedStatement.setString(1, order.getOrderSerial());
        preparedStatement.setInt(2, order.getCustomer().getId());
        preparedStatement.setInt(3, order.getUser().getId());
        preparedStatement.setString(4, order.getOrderType().name());
        preparedStatement.setInt(5, order.getDiscount());
        preparedStatement.setInt(6, order.getPureAmount());
        preparedStatement.setTimestamp(7, Timestamp.valueOf(order.getOrderTime()));
        preparedStatement.setInt(8, order.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement("DELETE FROM ORDERS WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Order> findAll() throws Exception {
        List<Order> orderlist = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM ORDERS");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orderlist.add(EntityMapper.orderMapper(resultSet));
        }
        return orderlist;
    }

    @Override
    public Order findById(int id) throws Exception {
        Order order = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM ORDERS WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            order = EntityMapper.orderMapper(resultSet);
        }
        return order;
    }

    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }

}

