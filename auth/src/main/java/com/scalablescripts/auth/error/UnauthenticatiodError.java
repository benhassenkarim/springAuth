package com.scalablescripts.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthenticatiodError extends ResponseStatusException {
    public UnauthenticatiodError() {
        super(HttpStatus.UNAUTHORIZED,"unauthenticated");
    }
}
