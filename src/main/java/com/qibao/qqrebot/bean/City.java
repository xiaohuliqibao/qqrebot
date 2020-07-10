package com.qibao.qqrebot.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    private String City_ID;
    private String City_EN;
    private String City_CN;
    private String Country_code;
    private String Country_EN;
    private String Country_CN;
    private String Province_EN;
    private String Province_CN;
    private String Admin_district_EN;
    private String Admin_district_CN;
    private String Latitude;
    private String Longitude;
    private String AD_code;
}