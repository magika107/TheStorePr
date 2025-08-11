package model.repository;

import model.entity.Payment;
import tools.ConnectionProvider;
import tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class PaymentRepository implements Repository<Payment> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public PaymentRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public void save(Payment payment) throws Exception {
        payment.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "payment_seq"));
        preparedStatement = connection.prepareStatement(
                "insert into PAYMENTS values (?,?,?,?,?,?,?,?)"
        );

        preparedStatement.setInt(1, payment.getId());
        preparedStatement.setString(2, payment.getTransactionType().name());
        preparedStatement.setString(3, payment.getPaymentType().name());
        preparedStatement.setInt(4, payment.getOrder().getId());
        preparedStatement.setInt(5, payment.getAmount());
        preparedStatement.setInt(6, payment.getCustomer().getId());
        preparedStatement.setInt(7, payment.getUser().getId());
        preparedStatement.setTimestamp(8, Timestamp.valueOf(payment.getPaymentDateTime()));
        preparedStatement.executeUpdate();
    }

    @Override
    public void edit(Payment payment) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update PAYMENTS SET  TRANSACTION_TYPE = ?, PAYMENT_TYPE = ?, ORDER_ID = ?, AMOUNT = ?,CUSTOMER_ID = ?, USER_ID = ?, PAYMENT_TIME = ? " + " WHERE ID = ?"
        );

        preparedStatement.setString(1, payment.getTransactionType().name());
        preparedStatement.setString(2, payment.getPaymentType().name());
        preparedStatement.setInt(3, payment.getOrder().getId());
        preparedStatement.setInt(4, payment.getAmount());
        preparedStatement.setInt(5, payment.getCustomer().getId());
        preparedStatement.setInt(6, payment.getUser().getId());
        preparedStatement.setTimestamp(7, Timestamp.valueOf(payment.getPaymentDateTime()));
        preparedStatement.setInt(8, payment.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement("DELETE FROM PAYMENTS WHERE ID = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Payment> findAll() throws Exception {
        List<Payment> paymentList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM PAYMENTS");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            paymentList.add(EntityMapper.paymentMapper(resultSet));
        }
        return paymentList;
    }

    @Override
    public Payment findById(int id) throws Exception {
        Payment payment = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM PAYMENTS WHERE ID = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            payment = EntityMapper.paymentMapper(resultSet);
        }
        return payment;
    }

    public Payment findByPaymentTime(String paymentTime) throws Exception {
        Payment payment = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM PAYMENTS WHERE PAYMENT_TIME = ?");
        preparedStatement.setTimestamp(1, Timestamp.valueOf(paymentTime));
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            payment = EntityMapper.paymentMapper(resultSet);

        }
        resultSet.close();
        preparedStatement.close();
        return payment;
    }

    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }
}
