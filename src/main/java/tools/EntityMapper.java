package tools;

import model.entity.Customer;
import model.entity.User;
import model.entity.enums.Gender;

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
}
