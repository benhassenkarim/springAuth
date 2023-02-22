package com.scalablescripts.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExist extends ResponseStatusException {
    public EmailAlreadyExist() {
        super(HttpStatus.BAD_REQUEST," email already exits ");
    }
}
