package model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import model.entity.enums.Gender;
import tools.DateConvertor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder

public abstract class Person implements Serializable {
    private Gender gender;
    private String name;
    private String family;
    private LocalDate birthDate;
    private String phoneNumber;

    public String getFullName(){
        return name + " " + family;
    }

    public String getFaBirthDate(){
        return DateConvertor.miladiToShamsi(birthDate).toString();
    }

    public void setFaBirthDate(String faBirthDate){
        birthDate = DateConvertor.shamsiToMiladi(faBirthDate);
    }
}