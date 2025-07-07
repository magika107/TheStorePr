package model.repository;

import lombok.extern.log4j.Log4j2;
import model.entity.Customer;
import tools.ConnectionProvider;
import tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2

public class CustomerRepository implements  Repository<Customer> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public CustomerRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    public void save(Customer customer) throws SQLException {
        customer.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "customer_seq"));

        preparedStatement = connection.prepareStatement(
                "insert into CUSTOMERS values (?, ?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.setString(2, customer.getGender().name());
        preparedStatement.setString(3, customer.getName());
        preparedStatement.setString(4, customer.getFamily());
        preparedStatement.setDate(5, (customer.getBirthDate() != null) ? Date.valueOf(customer.getBirthDate()) : null);
        preparedStatement.setString(6, customer.getPhoneNumber());
        preparedStatement.execute();
    }

    public void edit(Customer customer) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "update CUSTOMERS set gender=?, name=?, family=?, birth_date=?, phone_number=? where id=?"
        );
        preparedStatement.setString(1, customer.getGender().name());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setString(3, customer.getFamily());
        preparedStatement.setDate(4, (customer.getBirthDate() != null) ? Date.valueOf(customer.getBirthDate()) : null);
        preparedStatement.setString(5, customer.getPhoneNumber());
        preparedStatement.setInt(6, customer.getId());
        preparedStatement.execute();
    }


    public void delete(int id) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "delete from CUSTOMERS where id=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public List<Customer> findAll() throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from CUSTOMERS order by family");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            customerList.add(EntityMapper.customerMapper(resultSet));
        }
        return customerList;
    }

    public Customer findById(int id) throws SQLException {
        Customer customer = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from CUSTOMERS where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            customer = EntityMapper.customerMapper(resultSet);
        }
        return customer;
    }

    public List<Customer> findByNameAndFamily(String name, String family) throws Exception {
        List<Customer> customerList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM CUSTOMERS WHERE name LIKE ? AND family LIKE ?"
        );
        preparedStatement.setString(1, name + "%");
        preparedStatement.setString(2, family + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Customer customer = EntityMapper.customerMapper(resultSet);
            customerList.add(customer);
        }

        resultSet.close();
        preparedStatement.close();
        return customerList;
    }

    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }
}
