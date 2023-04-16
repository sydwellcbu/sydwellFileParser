/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.repository;

import com.eviro.assessment.grad001.sydwellNgwenya.entity.AccountProfile;
import java.net.URI;
import static junit.framework.TestCase.assertEquals;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 *
 * @author Administrator
 */
@DataJpaTest
public class AccountProfileRepositoryTest {

    public AccountProfileRepositoryTest() {
    }

    @Autowired
    private AccountProfileRepository accountProfileRepository;

    private AccountProfile accountProfile;

    @Autowired
    private TestEntityManager entityManager;

    private String imageHttp = "file:/C:/Users/Administrator/Desktop/NEW/sydwellNgwenya/src/main/resources/MomentumHealth.png";

    @BeforeEach
    void setUp() {

        // mocking account profile 
        accountProfile = AccountProfile.builder().name("Momentum").surname("Health").httpImageLink(imageHttp).build();
        //persisting the data
        entityManager.persist(accountProfile);

    }

    @Test
    public void testFindByNameAndSurname() {

        AccountProfile accountProfile = accountProfileRepository.findByNameAndSurname("Momentum", "Health").get(0);
        assertEquals(imageHttp.trim(), accountProfile.getHttpImageLink().trim());

    }



}
