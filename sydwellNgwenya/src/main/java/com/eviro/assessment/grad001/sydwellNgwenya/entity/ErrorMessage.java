/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Administrator
 */
 @AllArgsConstructor
 @NoArgsConstructor
 @Data
public class ErrorMessage {
    
    
    private HttpStatus httpStatus;
    private String errorMessage;
}
