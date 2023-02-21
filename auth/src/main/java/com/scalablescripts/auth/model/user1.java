package com.scalablescripts.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

@Getter
@Setter
@ToString
public class user1 {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
public static user1 of(String firstName,String lastName,String email,String password){
    return new user1(null,firstName,lastName,email,password);
}
    @PersistenceCreator
    private user1(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
