/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.error;

import com.eviro.assessment.grad001.sydwellNgwenya.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Administrator
 */
@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionhandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountProfileNotFound.class)
    public ResponseEntity< ErrorMessage> accountProfileNotFound(AccountProfileNotFound accountProfileNotFound, WebRequest webRequest) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, accountProfileNotFound.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}