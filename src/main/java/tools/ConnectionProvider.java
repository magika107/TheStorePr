package tools;

import lombok.Getter;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionProvider {

    private static BasicDataSource basicDataSource;  // فقط تعریف، بدون مقداردهی اولیه

    @Getter
    private static final ConnectionProvider connectionProvider = new ConnectionProvider();

    private ConnectionProvider() {
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        basicDataSource.setUrl("jdbc:oracle:thin:@localhost:1521/XEPDB1");  // دقت: اینجا service name است
        basicDataSource.setUsername("java");
        basicDataSource.setPassword("java123");
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxTotal(20);
    }

    public Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }

    public int getNextId(Connection connection, String sequenceName) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select " + sequenceName + ".nextval from dual");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }
}
