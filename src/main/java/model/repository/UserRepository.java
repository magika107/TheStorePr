package model.repository;

import lombok.extern.log4j.Log4j2;
import model.entity.User;
import tools.ConnectionProvider;
import tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2

public class UserRepository implements Repository<User> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public UserRepository() throws Exception {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    public void save(User user) throws Exception {
        user.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "user_seq"));

        preparedStatement = connection.prepareStatement(
                "insert into USERS values (?, ?, ?, ?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, user.getGender().name());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getFamily());
        preparedStatement.setDate(5, (user.getBirthDate() != null) ? Date.valueOf(user.getBirthDate()) : null);
        preparedStatement.setString(6, user.getPhoneNumber());
        preparedStatement.setString(7, user.getUsername());
        preparedStatement.setString(8, user.getPassword());
        preparedStatement.execute();
    }

    public void edit(User user) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update USERS set GENDER=?, NAME=?, FAMILY=?, BIRTH_DATE=?, PHONE_NUMBER=?, USERNAME=?, PASSWORD=? where ID=?"
        );
        preparedStatement.setString(1, user.getGender().name());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getFamily());
        preparedStatement.setDate(4, (user.getBirthDate() != null) ? Date.valueOf(user.getBirthDate()) : null);
        preparedStatement.setString(5, user.getPhoneNumber());
        preparedStatement.setString(6, user.getUsername());
        preparedStatement.setString(7, user.getPassword());
        preparedStatement.setInt(8, user.getId());
        preparedStatement.execute();
    }

    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from USERS where ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public List<User> findAll() throws Exception {
        List<User> userList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from USERS order by family");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            userList.add(EntityMapper.userMapper(resultSet));
        }
        return userList;
    }

    public User findById(int id) throws Exception {
        User user = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from USERS where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            user = EntityMapper.userMapper(resultSet);
        }
        return user;
    }

    public List<User> findByNameAndFamily(String name, String family) throws Exception {
        List<User> userList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM USERS WHERE name LIKE ? AND family LIKE ?"
        );
        preparedStatement.setString(1, name + "%");
        preparedStatement.setString(2, family + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = EntityMapper.userMapper(resultSet);
            userList.add(user);
        }

        resultSet.close();
        preparedStatement.close();
        return userList;
    }

    public User findByUsername(String username) throws Exception {
        User user = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE USERNAME=?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            user = EntityMapper.userMapper(resultSet);
        }
        return user;
    }

    public User findByUsernameAndPassword(String username, String password) throws Exception {
        User user = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD=?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            user = EntityMapper.userMapper(resultSet);
        }
        return user;
    }

    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }
}
