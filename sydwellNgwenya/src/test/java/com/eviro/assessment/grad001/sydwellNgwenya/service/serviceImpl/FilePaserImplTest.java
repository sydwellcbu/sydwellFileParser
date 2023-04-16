/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.service.serviceImpl;

import com.eviro.assessment.grad001.sydwellNgwenya.entity.AccountProfile;
import com.eviro.assessment.grad001.sydwellNgwenya.entity.CsvFlateFile;
import com.eviro.assessment.grad001.sydwellNgwenya.error.AccountProfileNotFound;
import com.eviro.assessment.grad001.sydwellNgwenya.error.CsvFileStringIndexOutOfBounds;
import com.eviro.assessment.grad001.sydwellNgwenya.repository.AccountProfileRepository;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static junit.framework.TestCase.assertEquals;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Administrator
 */
@SpringBootTest
public class FilePaserImplTest {

    @Autowired
    FilePaserImpl fileParser;

    @Mock
    static AccountProfileRepository accountProfileRepository;

    private AccountProfile accountProfile;
    private CsvFlateFile csvFlateFile;
    private final String RESOURCEDIRETORY = Paths.get("src", "main", "resources","images").toString()+"/";
    private final String httpLink = "http://sydwellNgwenya.grad001.assessment.eviro.com/src/main/resources/images/MomentumHealth.png";

    private final String base64ImageData = "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";
    private final File systemFile = new File(RESOURCEDIRETORY + "MomentumHealth.png");
    private final File csvFile = new File("C:/Users/Administrator/Downloads/enviro.csv");

    @BeforeEach
    void mySetUp() {

        csvFlateFile = CsvFlateFile.builder().name("Momentum").surName("Health").imageFormat("png").imageData(base64ImageData).build();

        accountProfile = AccountProfile.builder().name(csvFlateFile.getName()).surname(csvFlateFile.getSurName()).httpImageLink(httpLink).build();

        List<AccountProfile> accList = new ArrayList<>();
        accList.add(accountProfile);

        Mockito.when(accountProfileRepository.findByNameAndSurname("Momentum", "Health")).thenReturn(accList);

    }

    @Test
    public void convertBase64ImageDataToPhysicalImageFileMustBeTrue() throws IOException {

        File fileImage = fileParser.convertCSVDataToImage(csvFlateFile);
        assertEquals("MomentumHealth.png", fileImage.getName());

    }

    @Test
    public void createImagelinkMustBeTrue() {

        URI uri = fileParser.createImagelink(systemFile);

        assertEquals(systemFile.toURI(), uri);
    }

    @Test
    public void getHttpLinkFromDbMustBeTrue() {

        List<AccountProfile> accList = accountProfileRepository.findByNameAndSurname(csvFlateFile.getName(), csvFlateFile.getSurName());
        AccountProfile dbAcc = accList.get(0);

        assertEquals(httpLink, dbAcc.getHttpImageLink());

    }

    @Test
    public void getImageHttpLinkMustBetrue() throws AccountProfileNotFound, CsvFileStringIndexOutOfBounds, IOException {

        String httpFromDb = fileParser.getImageHttpLink(csvFlateFile.getName(), csvFlateFile.getSurName(), csvFile);
        assertEquals(httpFromDb, httpFromDb);
    }

    @Test
    public void createHttpImageLink() {

        String imageHttpLink = fileParser.createHttpImageLink(systemFile.toURI());
        assertEquals(httpLink, imageHttpLink);
    }
    

}
