package model.repository;

import model.entity.OrderItem;
import tools.ConnectionProvider;
import tools.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderItemRepository implements Repository<OrderItem> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public OrderItemRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public void save(OrderItem orderItem) throws Exception {
        orderItem.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "order_item_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO ORDER_ITEMS (ID, PRODUCT_ID, QUANTITY, PRICE, ORDER_ID) VALUES (?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, orderItem.getId());
        preparedStatement.setInt(2, orderItem.getProduct().getId());
        preparedStatement.setInt(3, orderItem.getQuantity());
        preparedStatement.setInt(4, orderItem.getPrice());
        preparedStatement.setInt(5, orderItem.getOrder().getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void edit(OrderItem orderItem) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE ORDER_ITEMS SET PRODUCT_ID = ?, QUANTITY = ?, PRICE = ?, ORDER_ID = ? WHERE ID = ?"
        );
        preparedStatement.setInt(1, orderItem.getProduct().getId());
        preparedStatement.setInt(2, orderItem.getQuantity());
        preparedStatement.setInt(3, orderItem.getPrice());
        preparedStatement.setInt(4, orderItem.getOrder().getId());
        preparedStatement.setInt(5, orderItem.getId());

        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement("DELETE FROM ORDER_ITEMS WHERE ID = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<OrderItem> findAll() throws Exception {
        List<OrderItem> list = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM ORDER_ITEMS");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(EntityMapper.orderItemMapper(resultSet));
        }
        return list;

    }

    @Override
    public OrderItem findById(int id) throws Exception {

        preparedStatement = connection.prepareStatement("SELECT * FROM ORDER_ITEMS WHERE ID = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return EntityMapper.orderItemMapper(resultSet);
        }
        return null;
    }

    public List<OrderItem> findByOrderId(int orderId) throws Exception {
        List<OrderItem> list = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM ORDER_ITEMS WHERE ORDER_ID = ?");
        preparedStatement.setInt(1, orderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(EntityMapper.orderItemMapper(resultSet));
        }
        return list;
    }

    @Override
    public void close() throws Exception {

    }
}
