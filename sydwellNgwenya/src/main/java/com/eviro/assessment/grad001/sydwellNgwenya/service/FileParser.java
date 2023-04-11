/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.service;

import com.eviro.assessment.grad001.sydwellNgwenya.entity.AccountProfile;
import com.eviro.assessment.grad001.sydwellNgwenya.entity.CsvFlateFile;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author Administrator
 */
public interface FileParser {

    void parseCSV(File csvFile);

    File convertCSVDataToImage(String base64ImageString) throws IOException;

    URI createImagelink(File fileImage);

    String getImageHttpLink(String name, String Surname,File csvFile);
}
