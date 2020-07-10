package com.qibao.qqrebot;

import com.forte.component.forcoolqhttpapi.CoolQHttpApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		// SpringApplication.run(DemoApplication.class, args);
		CoolQHttpApplication application = new CoolQHttpApplication();
		application.run(QQRunApplication.class);
	}

}
