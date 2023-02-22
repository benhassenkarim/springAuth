package com.scalablescripts.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordNotMatch extends ResponseStatusException {
    public PasswordNotMatch() {
        super(HttpStatus.BAD_REQUEST," password do not match ");
    }
}
