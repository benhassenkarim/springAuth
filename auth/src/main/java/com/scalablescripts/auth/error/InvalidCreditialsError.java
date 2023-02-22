package com.scalablescripts.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCreditialsError extends ResponseStatusException {

    public InvalidCreditialsError() {
        super(HttpStatus.BAD_REQUEST," invalid credentials ");
    }
}
