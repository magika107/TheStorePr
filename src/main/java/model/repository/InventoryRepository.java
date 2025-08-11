package model.repository;


import model.entity.Inventory;
import tools.ConnectionProvider;
import tools.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository implements Repository<Inventory> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public InventoryRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public void save(Inventory inventory) throws Exception {
        inventory.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "inventory_seq"));

        preparedStatement = connection.prepareStatement(
                "INSERT INTO inventories (id, product_id, quantity) VALUES (?, ?, ?)"
        );
        preparedStatement.setInt(1, inventory.getId());
        preparedStatement.setInt(2, inventory.getProduct().getId());
        preparedStatement.setInt(3, inventory.getQuantity());
        preparedStatement.execute();

    }

    @Override
    public void edit(Inventory inventory) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE inventories SET product_id = ?, quantity = ? WHERE id = ?"
        );
        preparedStatement.setInt(1, inventory.getProduct().getId());
        preparedStatement.setInt(2, inventory.getQuantity());
        preparedStatement.setInt(3, inventory.getId());
        preparedStatement.execute();

    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM inventories WHERE id = ?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();

    }

    @Override
    public List<Inventory> findAll() throws Exception {
        List<Inventory> inventoryList = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM inventories");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            inventoryList.add(EntityMapper.inventoryMapper(resultSet));
        }
        return inventoryList;
    }

    @Override
    public Inventory findById(int id) throws Exception {
        Inventory inventory = null;
        preparedStatement = connection.prepareStatement("SELECT * FROM inventories WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            inventory = EntityMapper.inventoryMapper(resultSet);
        }
        return inventory;
    }

    public Inventory findByProductId(int productId) throws Exception {
        Inventory inventory = null;
        preparedStatement = connection.prepareStatement("SELECT * FROM inventories WHERE product_id = ?");
        preparedStatement.setInt(1, productId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            inventory = EntityMapper.inventoryMapper(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return inventory;
    }


    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }
}
