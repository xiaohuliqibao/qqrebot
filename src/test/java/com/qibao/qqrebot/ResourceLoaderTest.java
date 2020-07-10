package com.qibao.qqrebot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ResourceLoaderTest {
    private ResourceLoader resourceLoader;

    public ResourceLoaderTest() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    void test() throws IOException {
        String line;
        Resource resource = resourceLoader.getResource("classpath:test.json");
        System.out.println(resource.getFilename());
        InputStream inputStream = resource.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        System.out.println(stringBuilder.toString());
        bufferedReader.close();
    }

}