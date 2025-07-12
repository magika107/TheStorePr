package tools;

import model.entity.Customer;
import model.entity.Product;
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

    public static Product productMapper(ResultSet resultSet) throws SQLException {
        return Product
                .builder()
                .id(resultSet.getInt("ID"))
                .title(resultSet.getString("TITLE"))
                .brand(resultSet.getString("BRAND"))
                .model(resultSet.getString("MODEL"))
                .serialNumber(resultSet.getString("SERIALNUMBER"))
                .buyPrice(resultSet.getInt("BUY_PRICE"))
                .build();
    }
}
