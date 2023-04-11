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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static com.eviro.assessment.grad001.sydwellNgwenya.controller.ImageController.logger;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author Administrator
 */
@Service
public class FilePaserImpl implements FileParser {

    @Autowired
    private AccountProfileRepository accountProfileRepository;
    private CsvFlateFile csvFlateFile;

    @Override
    public void parseCSV(File csvFile) {
        boolean skip = false;

        try {
            //getting physical image absolute path
            Path path = Paths.get(csvFile.getPath());

            //passing the file content to a list of a string
            List<String> lines = Files.readAllLines(path);

            //reading line by line of the string list
            for (String line : lines) {
                if (skip == true) {
                    logger.info("parseCSV file in FilePaserImpl ");
                    String[] fields = line.split(",");
                    if (fields.length == 4) {
                        //passing the csv content to a new csvFlateFile
                        csvFlateFile = new CsvFlateFile(fields[0], fields[1], fields[2].substring(6), fields[3]);
                        logger.info("convertCSVDataToImage in FilePaserImpl size =" + fields.length);
                        //calling the convertCSVDataToImage to create a physical image file
                        File imageFile = convertCSVDataToImage(csvFlateFile.getImageData());
                        logger.info("createImagelink in FilePaserImpl");
                        //creating the fille image uri
                        URI imageUri = createImagelink(imageFile);
                        logger.info("createHttpImageLink in FilePaserImpl");
                        //creating image URL
                        String imageUrl = createHttpImageLink(imageUri);
                        //Saving  Account Profile
                        AccountProfile dbAccountProfile = new AccountProfile(csvFlateFile.getName(), csvFlateFile.getSurName(), imageUrl);
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
        String imageName = csvFlateFile.getName() + "_" + csvFlateFile.getSurName() + "." + csvFlateFile.getImageFormat();
        Path filePath = null;
        try {

            Path resourceDirectory = Paths.get("src", "main", "resources", "images");
            filePath = resourceDirectory.resolve(imageName);
            Files.createDirectories(resourceDirectory);
            FileCopyUtils.copy(imageBytes, filePath.toFile());

            Files.createDirectories(resourceDirectory);

            FileCopyUtils.copy(imageBytes, filePath.toFile());
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
    public String getImageHttpLink(String name, String Surname, File csvFile) {

        logger.info("inside getImageHttpLink in FilePaserImpl");

//        parseCSV(csvFile);
//        String link = "file:/C:/Users/Public/Desktop/Momentum_Health.png";
        //  AccountProfile dbUser = accountProfileRepository.getByNameAndSurnameAndHttpImageLink(name, Surname, link);
        parseCSV(csvFile);

        try {
            return getImageUrl(name, Surname);
        } catch (AccountProfileNotFound ex) {
            Logger.getLogger(FilePaserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Account not Found";
    }

    private String createHttpImageLink(URI imageUri) {

        logger.info("inside createHttpImageLink in FilePaserImpl");

        URL url = null;
        String imageUrl = null;
        try {
            url = imageUri.toURL();
            imageUrl = url.toString();
            imageUrl = imageUrl.replace("file:", "http:");
            imageUrl = URLEncoder.encode(imageUrl, "UTF-8");

        } catch (IOException ex) {
            Logger.getLogger(FilePaserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imageUrl;
    }

    public String getImageUrl(String name, String surname) throws AccountProfileNotFound {
        AccountProfile image = accountProfileRepository.findByNameAndSurname(name, surname);
        if (image != null) {
            return image.getHttpImageLink();
        } else {
            throw new AccountProfileNotFound("Account Profile is not foubd");
        }
    }
}
