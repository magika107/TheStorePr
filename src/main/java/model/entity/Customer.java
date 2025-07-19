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

public class Customer extends Person implements Serializable {
    private int id;
}
