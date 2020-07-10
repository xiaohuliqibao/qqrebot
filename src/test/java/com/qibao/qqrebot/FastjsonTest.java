package com.qibao.qqrebot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

class FastjsonTest {

    @Test
    void testFastjson() {
        String path = "src\\main\\resources\\test.json";
        // String fileName = "city.json";
        File jsonfile = new File(path);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(jsonfile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            System.out.println(stringBuilder.toString());
        } catch (Exception e) {
            // TODO: handle exception
        }

        JSONObject jObject = JSONObject.parseObject(stringBuilder.toString());
        // System.out.println(jObject.getJSONArray("city").get(0));
        JSONArray cities = jObject.getJSONArray("city");
        String cityName = "长洲";
        for (int i = 0; i < cities.size(); i++) {
            if (cities.getJSONObject(i).getString("City_CN").equals(cityName)) {
                System.out.println(cities.getJSONObject(i).getString("City_ID"));
                break;
            }
        }
        // System.out.println(cities.size());
        // JSONObject city = cities.getJSONObject(0);
        // System.out.println(city.getString("City_ID"));
        // JSONObject txt = jObject.getJSONObject("text");
        // System.out.println(txt.getString("1"));
    }

    @Test
    void testGetCity() {
        String cityName = "乌鲁木齐天气";
        cityName = cityName.substring(0, cityName.length() - 2);
        System.out.println(cityName);

    }

}