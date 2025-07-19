package model.repository;

import model.entity.Product;
import tools.ConnectionProvider;
import tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository implements Repository<Product> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public ProductRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public void save(Product product) throws Exception {
        product.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "product_seq"));
        preparedStatement = connection.prepareStatement("insert into PRODUCTS values(?,?,?,?,?,?)");
        preparedStatement.setInt(1, product.getId());
        preparedStatement.setString(2, product.getTitle());
        preparedStatement.setString(3, product.getBrand());
        preparedStatement.setString(4, product.getModel());
        preparedStatement.setString(5, product.getSerialNumber());
        preparedStatement.setInt(6, product.getBuyPrice());
        preparedStatement.execute();
    }

    @Override
    public void edit(Product product) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE PRODUCTS SET TITLE=?,brand=?,model=?,SERIALNUMBER=?,BUY_PRICE=? WHERE id=?"
        );
        preparedStatement.setString(1, product.getTitle());
        preparedStatement.setString(2, product.getBrand());
        preparedStatement.setString(3, product.getModel());
        preparedStatement.setString(4, product.getSerialNumber());
        preparedStatement.setInt(5, product.getBuyPrice());
        preparedStatement.setInt(6, product.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement("DELETE FROM PRODUCTS WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> productList = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCTS ORDER BY TITLE");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            productList.add(EntityMapper.productMapper(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return productList;
    }

    @Override
    public Product findById(int id) throws Exception {
        Product product = null;
        preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCTS WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            product = EntityMapper.productMapper(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return product;
    }

    public Product findBySerialNumber(String serialNumber) throws Exception {
        Product product = null;
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM PRODUCTS WHERE SERIALNUMBER = ?"
        );
        preparedStatement.setString(1, serialNumber);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            product = EntityMapper.productMapper(resultSet);
        }

        resultSet.close();
        preparedStatement.close();
        return product;
    }

    public List<Product> findByBrandAndModel(String brand, String model) throws Exception {
        List<Product> productList = new ArrayList<>();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM PRODUCTS WHERE BRAND LIKE ? AND MODEL LIKE ?"
        );
        preparedStatement.setString(1, brand + "%");
        preparedStatement.setString(2, model + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            productList.add(EntityMapper.productMapper(resultSet));
        }

        resultSet.close();
        preparedStatement.close();
        return productList;
    }

    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();

    }
}
