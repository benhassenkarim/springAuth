package com.scalablescripts.auth.service;

import com.scalablescripts.auth.error.*;
import com.scalablescripts.auth.model.PasswordRecovery;
import com.scalablescripts.auth.model.Token;
import com.scalablescripts.auth.model.user1;
import com.scalablescripts.auth.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final String accesTokenSecret;
    private final String refreshTokenSecret;
    private final MailService mailService;

    public AuthService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder,
                       @Value("${application.security.access-token-secret}") String accesTokenSecret,
                       @Value("${application.security.refresh-token-secret}") String refreshTokenSecret,
                       MailService mailService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.accesTokenSecret = accesTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
        this.mailService = mailService;
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

        var login=Login.of(user.getId(),accesTokenSecret,refreshTokenSecret);
        var refreshJwt=login.getRefreshToken();
        user.addToken(new Token(refreshJwt.getToken(),refreshJwt.getIssuedAt(),refreshJwt.getExpiration() ));
        userRepo.save(user);
        return login;
    }

    public user1 getUserFromToken(String token) {

        var userId= Jwt.from(token,accesTokenSecret).getUserId();
        return userRepo.findById(userId)
                .orElseThrow(UserNotFoundError::new);
    }

    public Login refreshAccess(String refreshToken) {
        var refreshJwt= Jwt.from(refreshToken,refreshTokenSecret);

        var user=userRepo.findByIdAndTokenRefreshTokenAndTokenExpiredAtGreaterThan(refreshJwt.getUserId(),refreshJwt.getToken(),refreshJwt.getExpiration())
                .orElseThrow(UnauthenticatiodError::new);
        return Login.of(refreshJwt.getUserId(),accesTokenSecret, refreshJwt);
    }
    public Boolean logout(String refreshToken){
        var refreshJwt=Jwt.from(refreshToken,refreshTokenSecret);
        var user=userRepo.findById(refreshJwt.getUserId())
                .orElseThrow(UnknownError::new);
        var tokenIsRemoved=user.removeTokenIf(token ->Objects.equals(token.refreshToken(),refreshToken) );
        if (tokenIsRemoved)
            userRepo.save(user);
        return tokenIsRemoved;
    }

    public void forgot(String email, String originUrl) {
        var token= UUID.randomUUID().toString().replace("-","");
        var user=userRepo.findByEmail(email)
                .orElseThrow(UserNotFoundError::new);
        user.addPasswordRecovery(new PasswordRecovery(token));
        mailService.sendForgotMessage(email,token,originUrl);
        userRepo.save(user);
    }
}
