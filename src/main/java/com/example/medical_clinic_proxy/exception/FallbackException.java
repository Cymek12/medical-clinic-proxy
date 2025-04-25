package com.example.medical_clinic_proxy.exception;

import org.springframework.http.HttpStatus;

public class FallbackException extends WebException{
    public FallbackException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
