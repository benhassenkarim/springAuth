package com.scalablescripts.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;


@ToString
public class user1 {
    @Getter

    @Id
    private Long id;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;
    @MappedCollection
    private final Set<Token> tokens=new HashSet<>();

    @MappedCollection
    private final Set<PasswordRecovery> passwordRecoveries=new HashSet<>();


    public static user1 of(String firstName,String lastName,String email,String password){
    return new user1(null,firstName,lastName,email,password, Collections.emptyList(),Collections.emptyList());
}
    @PersistenceCreator
    private user1(Long id, String firstName, String lastName, String email, String password, Collection<Token> tokens,Collection<PasswordRecovery> passwordRecoveries) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.tokens.addAll(tokens);
        this.passwordRecoveries.addAll(passwordRecoveries);
    }
    public void addToken(Token token){
    this.tokens.add(token);
    }

    public  Boolean removeToken(Token token){
    return this.tokens.remove(token);
    }

    public Boolean removeTokenIf(Predicate<? super Token> predicate){
    return this.tokens.removeIf(predicate);
    }

    public void addPasswordRecovery(PasswordRecovery passwordRecovery){this.passwordRecoveries.add(passwordRecovery);}

    public Boolean removePasswordRecovery(PasswordRecovery passwordRecovery){ return this.passwordRecoveries.remove(passwordRecovery);}

    public Boolean removePasswordRecoveryif(Predicate<? super PasswordRecovery> predicate){
       return this.passwordRecoveries.removeIf(predicate);}
}
