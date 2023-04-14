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
public class CsvFlateFileOutOfBound extends Exception {
//   CsvFlateFile 

    public CsvFlateFileOutOfBound() {
        super();
    }

    public CsvFlateFileOutOfBound(String message) {
        super(message);
    }

    public CsvFlateFileOutOfBound(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvFlateFileOutOfBound(Throwable cause) {
        super(cause);
    }

    protected CsvFlateFileOutOfBound(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
