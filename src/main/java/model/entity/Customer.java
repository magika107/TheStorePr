package model.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

public class Customer extends Person implements Serializable {
    private int id;

    public String toString(){
        return String.valueOf(id);
    }
}
