package model.repository;

import model.entity.Bank;
import tools.ConnectionProvider;
import tools.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankRepository implements Repository<Bank> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public BankRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }
    @Override
    public void save(Bank bank) throws Exception {
        bank.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "bank_seq"));

        preparedStatement = connection.prepareStatement(
                "INSERT INTO BANKS (id, customer_id, payment_id, amount) VALUES (?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, bank.getId());
        preparedStatement.setInt(2, bank.getCustomer().getId());
        preparedStatement.setInt(3, bank.getPayment().getId());
        preparedStatement.setDouble(4, bank.getAmount());
        preparedStatement.execute();
    }

    @Override
    public void edit(Bank bank) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE banks SET customer_id = ?, payment_id = ?, amount = ? WHERE id = ?"
        );
        preparedStatement.setInt(1, bank.getCustomer().getId());
        preparedStatement.setInt(2, bank.getPayment().getId());
        preparedStatement.setDouble(3, bank.getAmount());
        preparedStatement.setInt(4, bank.getId());
        preparedStatement.execute();

    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement("DELETE FROM banks WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();

    }

    @Override
    public List<Bank> findAll() throws Exception {
        List<Bank> bankList = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM banks");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            bankList.add(EntityMapper.bankMapper(resultSet));
        }
        return bankList;
    }

    @Override
    public Bank findById(int id) throws Exception {
        Bank bank = null;
        preparedStatement = connection.prepareStatement("SELECT * FROM banks WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            bank = EntityMapper.bankMapper(resultSet);
        }
        return bank;
    }

    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();

    }
}
