package model.entity;

import com.google.gson.Gson;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
//@ToString

public class User extends Person implements Serializable {
    private int id;
    private String username;
    private String password;
}


