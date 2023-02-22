package com.scalablescripts.auth.service;

import com.scalablescripts.auth.model.user1;
import com.scalablescripts.auth.repository.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class AuthService {
    private final UserRepo userRepo;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public user1 register(String firstName, String lastName, String email, String password, String passwordConfirm) {

        if(!Objects.equals(password,passwordConfirm))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"password do not match");

        return userRepo.save(
           user1.of(firstName,lastName,email,password)
   );
    }
}
