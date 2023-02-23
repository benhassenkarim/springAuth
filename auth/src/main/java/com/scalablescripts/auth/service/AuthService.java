package com.scalablescripts.auth.service;

import com.scalablescripts.auth.error.EmailAlreadyExist;
import com.scalablescripts.auth.error.InvalidCreditialsError;
import com.scalablescripts.auth.error.PasswordNotMatch;
import com.scalablescripts.auth.error.UserNotFoundError;
import com.scalablescripts.auth.model.user1;
import com.scalablescripts.auth.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final String accesTokenSecret;
    private final String refreshTokenSecret;

    public AuthService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder,
                      @Value("${application.security.access-token-secret}") String accesTokenSecret,
                       @Value("${application.security.refresh-token-secret}")  String refreshTokenSecret) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.accesTokenSecret = accesTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
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

    public Login login(String email, String password) {
        var user=userRepo.findByEmail(email)
                .orElseThrow(InvalidCreditialsError::new);
        if(!passwordEncoder.matches(password,user.getPassword()))
            throw new InvalidCreditialsError();

        return Login.of(user.getId(),accesTokenSecret,refreshTokenSecret);
    }

    public user1 getUserFromToken(String token) {

        var userId=Token.from(token,accesTokenSecret);
        return userRepo.findById(userId)
                .orElseThrow(UserNotFoundError::new);
    }
}
