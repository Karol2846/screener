package com.stock.screener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan
public class ScreenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreenerApplication.class, args);
	}

}
//DOCS:
//  jak się okazało, niektóre spółki mają dane jako null - wtedy wali nullPointery przy mapowaniu (trzeba to jakoś obsłużyć)