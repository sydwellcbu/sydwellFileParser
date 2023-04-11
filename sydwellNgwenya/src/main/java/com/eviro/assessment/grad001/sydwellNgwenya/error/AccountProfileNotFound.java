/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.error;

/**
 *
 * @author Administrator
 */
public class AccountProfileNotFound extends Exception {

    public AccountProfileNotFound() {
        super();
    }

    public AccountProfileNotFound(String message) {
        super(message);
    }

    public AccountProfileNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountProfileNotFound(Throwable cause) {
        super(cause);
    }

    protected AccountProfileNotFound(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
