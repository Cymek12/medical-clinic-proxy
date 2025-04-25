package com.example.medical_clinic_proxy.handler;

import com.example.medical_clinic_proxy.exception.WebException;
import com.example.medical_clinic_proxy.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class MedicalClinicProxyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(exception = WebException.class)
    public ResponseEntity<Object> handleWebException(WebException ex, WebRequest request) {
        ErrorMessage body = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), new Date());
        return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getHttpStatus(), request);
    }
}