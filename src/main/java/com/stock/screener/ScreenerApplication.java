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
// - kiedy przychodzi event - nigdzie nie jest gwarantowana kolejność przetwarzania.
// 		Jesli np. idzie stockSummary o nowej spółce <- i pierwszy odbierze ją FundamentalDAtaHandler, to DB się wywali, bo nie ma takiej Stock
//			insert or update on table "fundamental_data" violates foreign key constraint "fundamental_data_symbol_fkey"
// - muszę jakoś sprytnie sprawdzać w stockRepository czy istnieje spółka dla danego tickera
//		(jeszcze nie wiem jak to zrobić jak jej nie ma XD)

//  jak się okazało, niektóre spółki mają dane jako null - wtedy wali nullPointery przy mapowaniu (trzeba to jakoś obsłużyć)