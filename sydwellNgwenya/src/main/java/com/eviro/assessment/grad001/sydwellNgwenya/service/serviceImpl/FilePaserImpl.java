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
import com.eviro.assessment.grad001.sydwellNgwenya.error.CsvFileStringIndexOutOfBounds;
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

    private static final Path RESOURCEDIRETORY = Paths.get("src", "main", "resources", "images");

    @Override
    public void parseCSV(File csvFile) throws CsvFileStringIndexOutOfBounds, IOException {

        //getting csv file path
        Path csvFilePath = Paths.get(csvFile.getPath());

        //passing the file content to a list of a string
        List<String> csvContent = Files.readAllLines(csvFilePath);
        csvContent.remove(csvContent.get(0));
        //reading line by line of the string list
        for (String line : csvContent) {
            logger.info("parseCSV file in FilePaserImpl ");
            String[] fields = line.split(",");
            if (fields.length == 4) {
                //passing the csv content to a new csvFlateFile
                CsvFlateFile csvFlateFile = new CsvFlateFile(fields[0].trim().replaceAll("\\s", ""), fields[1].trim().replaceAll("\\s", ""), fields[2].substring(6), fields[3]);
                logger.log(Level.INFO, "convertCSVDataToImage in FilePaserImpl");
                //calling the convertCSVDataToImage to create a physical image file
                File imagePhysicalFile = convertCSVDataToImage(csvFlateFile);
                logger.info("createImagelink in FilePaserImpl");
                //creating the fille image uri
                URI imageUri = createImagelink(imagePhysicalFile);
                logger.info("createHttpImageLink in FilePaserImpl");
                //creating http image link
                String httpImageLink = createHttpImageLink(imageUri);
                //Saving  Account Profile
                logger.info("Account does not exist, creating a new account");
                AccountProfile AccountProfile = new AccountProfile(csvFlateFile.getName(), csvFlateFile.getSurName(), httpImageLink);
                try {
                    getHttpLinkFromDb(csvFlateFile.getName(), csvFlateFile.getSurName());
                } catch (AccountProfileNotFound ex) {
                    accountProfileRepository.save(AccountProfile);
                }

            } else {
                throw new CsvFileStringIndexOutOfBounds("Csv Flate File Does Not Have 4 Fields");
            }
        }

    }

    @Override
    public File convertCSVDataToImage(CsvFlateFile flateFile) throws IOException {

        logger.info("inside convertCSVDataToImage in FilePaserImpl");
        /*            Decode base64 image data and save as physical file */
        byte[] imageBytes = Base64.getDecoder().decode(flateFile.getImageData());
        String imageName = flateFile.getName() + flateFile.getSurName() + "." + flateFile.getImageFormat();
        Path filePath = null;

        filePath = RESOURCEDIRETORY.resolve(imageName);
        Files.createDirectories(RESOURCEDIRETORY);
        FileCopyUtils.copy(imageBytes, filePath.toFile());
        Files.createDirectories(RESOURCEDIRETORY);

        return filePath.toFile();
    }

    @Override
    public URI createImagelink(File fileImage) {
        logger.info("inside createImagelink in FilePaserImpl");
        return fileImage.toURI();
    }

    @Override
    public String getImageHttpLink(String name, String Surname, File csvFile) throws AccountProfileNotFound, CsvFileStringIndexOutOfBounds, IOException {

        logger.info("inside getImageHttpLink in FilePaserImpl");

        parseCSV(csvFile);

        return getHttpLinkFromDb(name, Surname);

    }

    public String createHttpImageLink(URI imageUri) {

        logger.info("inside createHttpImageLink in FilePaserImpl");

        // string http = "http://localhost:" + serverPort;
        String http = "http://sydwellNgwenya.grad001.assessment.eviro.com";
        String stringUri = imageUri.toString();
        int httpIndex = stringUri.indexOf("src") - 1;
        String stringUr = stringUri.substring(httpIndex);
        String imageHttp = http + stringUr;

        String httpLink = http + stringUr;
        return httpLink;
    }

    public String getHttpLinkFromDb(String name, String surname) throws AccountProfileNotFound {

        List<AccountProfile> accountProfile = accountProfileRepository.findByNameAndSurname(name, surname);

        if (!accountProfile.isEmpty()) {
            AccountProfile accountProfile1 = accountProfile.get(0);
            return accountProfile1.getHttpImageLink();
        } else {
            throw new AccountProfileNotFound("Account Profile is not found");
        }

    }
}
