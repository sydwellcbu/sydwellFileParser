/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.controller;

import com.eviro.assessment.grad001.sydwellNgwenya.error.AccountProfileNotFound;
import com.eviro.assessment.grad001.sydwellNgwenya.error.CsvFileStringIndexOutOfBounds;
import com.eviro.assessment.grad001.sydwellNgwenya.service.serviceImpl.FilePaserImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

/** n
 *
 * @author Administrator
 */
@WebMvcTest(ImageController.class)
public class ImageControllerTest {


    @MockBean
    private static FilePaserImpl filePaser;

    @Autowired
    private ImageController imageController;

    @Autowired
    private MockMvc mockMvc;

    private final String httpLink = "http://sydwellNgwenya.grad001.assessment.eviro.com/src/main/resources/images/MomentumHealth.png";

    private final File csvFile = new File("C:/Users/Administrator/Downloads/enviro.csv");

    private final String imagePath = Paths.get("src", "main", "resources", "images").toString() + "/MomentumHealth.png";

    @BeforeEach
    void settingUp() throws AccountProfileNotFound, CsvFileStringIndexOutOfBounds, IOException {

        Mockito.when(filePaser.getImageHttpLink("Momentum", "Health", csvFile)).thenReturn(httpLink);

    }

    @Test
    public void getImageHttpLink() throws AccountProfileNotFound, CsvFileStringIndexOutOfBounds, IOException, Exception {


        FileInputStream input = new FileInputStream(csvFile);
        MultipartFile multipartFile = new MockMultipartFile("file", csvFile.getName(), "text/plain", input);

        ResponseEntity<FileSystemResource> response = imageController.getHttpImagelink("Momentum", "Health", "MomentumHealth.png", multipartFile);
        FileSystemResource fileSystemResource = response.getBody();

        String fileContent = new String(Files.readAllBytes(Paths.get(new File(imagePath).toURI())), StandardCharsets.UTF_8);

        mockMvc.perform(MockMvcRequestBuilders.get("v1/api/image/{name}/{surname}/{pattern}").contentType(MediaType.IMAGE_PNG).content(fileContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void convertMultiPartfileTofile() throws FileNotFoundException, IOException {
        FileInputStream input = new FileInputStream(csvFile);
        MultipartFile multipartFile = new MockMultipartFile("file", csvFile.getName(), "text/plain", input);

        File file = imageController.convertMultipartFileToFile(multipartFile);

        Assertions.assertArrayEquals(
                Files.readAllBytes(Paths.get(file.toURI())),
                Files.readAllBytes(Paths.get(csvFile.toURI())));

    }
}