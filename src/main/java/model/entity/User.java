package model.entity;

import lombok.*;
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


    public String toString(){
        return String.valueOf(id);
    }
}


