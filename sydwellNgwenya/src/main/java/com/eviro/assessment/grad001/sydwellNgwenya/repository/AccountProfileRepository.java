/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.repository;

import com.eviro.assessment.grad001.sydwellNgwenya.entity.AccountProfile;
import com.eviro.assessment.grad001.sydwellNgwenya.error.AccountProfileNotFound;
import java.io.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Administrator
 */
public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long>{

    public AccountProfile findByNameAndSurname(String name, String surname)throws AccountProfileNotFound;

    public AccountProfile findByNameAndSurnameAndHttpImageLink(String name, String Surname, String imageLinkString);
    
}
