/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.repository;

import com.eviro.assessment.grad001.sydwellNgwenya.entity.AccountProfile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Administrator
 */
public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long>{

    public List<AccountProfile> findByNameAndSurname(String name, String surname);

    public List<AccountProfile> findByNameAndSurnameAndHttpImageLinkEndsWith(String name, String Surname, String imageLinkString);
    
}
