package com.synapsis.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "BAD REQUEST")
public class BadRequestException extends Exception {
    private static final long serialVersionUID = 1L;
}