package model.entity;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

public class User extends Person implements Serializable {
    private int id;
    private String username;
    private String password;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}


