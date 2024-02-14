package com.synapsis.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "UNAUTHORIZED")
public class UnauthorizeException extends Exception {
    private static final long serialVersionUID = 1L;
}
