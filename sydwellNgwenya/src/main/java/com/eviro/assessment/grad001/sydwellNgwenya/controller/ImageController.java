/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.controller;

import com.eviro.assessment.grad001.sydwellNgwenya.service.FileParser;
import static com.eviro.assessment.grad001.sydwellNgwenya.service.serviceImpl.FilePaserImpl.resourceDirectory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("v1/api/image")
public class ImageController {

    @Autowired
    private FileParser fileParser;

    public static final Logger logger = Logger.getLogger(ImageController.class.getName());

    @GetMapping(value = "/{name}/{surname}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<FileSystemResource> getHttpImagelink(@PathVariable String name, @PathVariable String surname, @RequestParam("file") MultipartFile multipartFile) {
        logger.info("Inside get HttpImageLink in Image Conroller Class");

        File file = null;
        try {
            file = convertMultipartFileToFile(multipartFile);
        } catch (IOException ex) {
            Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String httpLink = fileParser.getImageHttpLink(name.trim().replaceAll("\\s", ""), surname.trim().replaceAll("\\s", ""), file);

        int lastIndex = httpLink.lastIndexOf("/");
        String imagePath = httpLink.substring(lastIndex);
        FileSystemResource resource = new FileSystemResource(resourceDirectory + imagePath);
        return ResponseEntity.ok()
                .body(resource);

    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        logger.info("Inside convertMultipartFileToFile in Image Conroller Class");
        File file;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            file = new File(multipartFile.getOriginalFilename());
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                int readBytes;
                byte[] buffer = new byte[8192];
                while ((readBytes = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, readBytes);
                }
            }
        }
        return file;
    }
}
