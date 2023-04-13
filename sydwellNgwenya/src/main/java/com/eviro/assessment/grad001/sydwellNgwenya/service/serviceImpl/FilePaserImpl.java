/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.service.serviceImpl;

import com.eviro.assessment.grad001.sydwellNgwenya.entity.AccountProfile;
import com.eviro.assessment.grad001.sydwellNgwenya.entity.CsvFlateFile;
import com.eviro.assessment.grad001.sydwellNgwenya.error.AccountProfileNotFound;
import com.eviro.assessment.grad001.sydwellNgwenya.repository.AccountProfileRepository;
import com.eviro.assessment.grad001.sydwellNgwenya.service.FileParser;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.eviro.assessment.grad001.sydwellNgwenya.controller.ImageController.logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author Administrator
 */
@Service
public class FilePaserImpl implements FileParser {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private AccountProfileRepository accountProfileRepository;
    private CsvFlateFile csvFlateFile;
    public static final Path resourceDirectory = Paths.get("src", "resources", "images");
    @Override
    public void parseCSV(File csvFile) {
        boolean skip = false;

        try {
            //getting csv file path
            Path csvFilePath = Paths.get(csvFile.getPath());

            //passing the file content to a list of a string
            List<String> csvContent = Files.readAllLines(csvFilePath);

            //reading line by line of the string list
            for (String line : csvContent) {
                if (skip == true) {
                    logger.info("parseCSV file in FilePaserImpl ");
                    String[] fields = line.split(",");
                    if (fields.length == 4) {
                        //passing the csv content to a new csvFlateFile
                        csvFlateFile = new CsvFlateFile(fields[0].trim().replaceAll("\\s", ""), fields[1].trim().replaceAll("\\s", ""), fields[2].substring(6), fields[3]);
                        logger.log(Level.INFO, "convertCSVDataToImage in FilePaserImpl size ={0}", fields.length);
                        //calling the convertCSVDataToImage to create a physical image file
                        File imagePhysicalFile = convertCSVDataToImage(csvFlateFile.getImageData());
                        logger.info("createImagelink in FilePaserImpl");
                        //creating the fille image uri
                        URI imageUri = createImagelink(imagePhysicalFile);
                        logger.info("createHttpImageLink in FilePaserImpl");
                        //creating http image link
                        String httpImageLink = createHttpImageLink(imageUri);
                        //Saving  Account Profile
                        AccountProfile dbAccountProfile = new AccountProfile(csvFlateFile.getName(), csvFlateFile.getSurName(), httpImageLink);
                        logger.info("Saving CSV File in FilePaserImpl");
                        accountProfileRepository.save(dbAccountProfile);

                    } else {
                        logger.warning("CsvFile must have 4 feilds");
                    }
                }

                skip = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(FilePaserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public File convertCSVDataToImage(String base64ImageString) throws IOException {

        logger.info("inside convertCSVDataToImage in FilePaserImpl");
        /*            Decode base64 image data and save as physical file */
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
        String imageName = csvFlateFile.getName()+csvFlateFile.getSurName() + "." + csvFlateFile.getImageFormat();
        Path filePath = null;
        try {

            filePath = resourceDirectory.resolve(imageName);
            Files.createDirectories(resourceDirectory);
            FileCopyUtils.copy(imageBytes, filePath.toFile());
            Files.createDirectories(resourceDirectory);

        } catch (IOException ex) {
            Logger.getLogger(FilePaserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filePath.toFile();
    }

    @Override
    public URI createImagelink(File fileImage) {
        logger.info("inside createImagelink in FilePaserImpl");
        return fileImage.toURI();
    }

    @Override
    public String getImageHttpLink(String name, String Surname, File csvFile) throws AccountProfileNotFound{

        logger.info("inside getImageHttpLink in FilePaserImpl");

        parseCSV(csvFile);

        
            return getHttpLinkFromDb(name, Surname);
   
    }

    private String createHttpImageLink(URI imageUri) {

        logger.info("inside createHttpImageLink in FilePaserImpl");

        String http = "http://localhost:" + serverPort;
        String stringUri = imageUri.toString();
        int lastIndex = stringUri.lastIndexOf("/");
        String lastPart = stringUri.substring(lastIndex);
        String imageUrl = http+lastPart;
        

        return imageUrl;
    }

    private String getHttpLinkFromDb(String name, String surname) throws AccountProfileNotFound {

        List<AccountProfile> accountProfile = accountProfileRepository.findByNameAndSurname(name, surname);

        if (!accountProfile.isEmpty()) {
            AccountProfile accountProfile1 = accountProfile.get(0);
            return accountProfile1.getHttpImageLink();
        } else {
            throw new AccountProfileNotFound("Account Profile is not found");
        }

    }
}
