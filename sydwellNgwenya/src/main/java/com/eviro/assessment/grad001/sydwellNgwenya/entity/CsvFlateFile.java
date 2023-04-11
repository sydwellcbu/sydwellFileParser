/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CsvFlateFile {

    private String name;
    private String surName;
    private String imageFormat;
    private String imageData;

}
