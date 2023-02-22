package com.scalablescripts.auth.service;

import com.scalablescripts.auth.error.EmailAlreadyExist;
import com.scalablescripts.auth.error.InvalidCreditialsError;
import com.scalablescripts.auth.error.PasswordNotMatch;
import com.scalablescripts.auth.model.user1;
import com.scalablescripts.auth.repository.UserRepo;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public user1 register(String firstName, String lastName, String email, String password, String passwordConfirm) {

        if(!Objects.equals(password,passwordConfirm))
            throw new PasswordNotMatch();
user1 user;
        try{
            user=userRepo.save(
                    user1.of(firstName,lastName,email,passwordEncoder.encode(password))
            );
        }
        catch (DbActionExecutionException exception){
            throw new EmailAlreadyExist();
        }
        return user;
    }

    public user1 login(String email, String password) {
        var user=userRepo.findByEmail(email)
                .orElseThrow(InvalidCreditialsError::new);
        if(!passwordEncoder.matches(password,user.getPassword()))
            throw new InvalidCreditialsError();

        return user;
    }
}
