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
public class CsvFileStringIndexOutOfBounds extends Exception {
//   CsvFlateFile 

    public CsvFileStringIndexOutOfBounds() {
        super();
    }

    public CsvFileStringIndexOutOfBounds(String message) {
        super(message);
    }

    public CsvFileStringIndexOutOfBounds(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvFileStringIndexOutOfBounds(Throwable cause) {
        super(cause);
    }

    protected CsvFileStringIndexOutOfBounds(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
