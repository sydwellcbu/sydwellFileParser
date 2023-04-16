/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.service;

import com.eviro.assessment.grad001.sydwellNgwenya.entity.CsvFlateFile;
import com.eviro.assessment.grad001.sydwellNgwenya.error.AccountProfileNotFound;
import com.eviro.assessment.grad001.sydwellNgwenya.error.CsvFileStringIndexOutOfBounds;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 *
 * @author Administrator
 */
public interface FileParser {

    void parseCSV(File csvFile)  throws CsvFileStringIndexOutOfBounds,IOException;

    File convertCSVDataToImage(CsvFlateFile flateFile) throws IOException;

    URI createImagelink(File fileImage);

    String getImageHttpLink(String name, String Surname, File csvFile) throws AccountProfileNotFound,CsvFileStringIndexOutOfBounds,IOException;
}
