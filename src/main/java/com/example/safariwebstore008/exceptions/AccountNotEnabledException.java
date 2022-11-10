package com.example.safariwebstore008.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AccountNotEnabledException extends AuthenticationException {
    public AccountNotEnabledException(String msg) {
        super(msg);
    }
}
