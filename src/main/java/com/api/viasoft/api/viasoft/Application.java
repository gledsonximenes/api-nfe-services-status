package com.api.viasoft.api.viasoft;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.api.viasoft.service.SaveHistoryServicesStatusService;

@SpringBootApplication(scanBasePackages = {"com.api.viasoft"})
@EntityScan(basePackages = {
		"com.api.viasoft"
})
@EnableJpaRepositories(basePackages = {
		"com.api.viasoft"
})
public class Application {

	@Autowired
	SaveHistoryServicesStatusService saveHistoryServicesStatusService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		saveHistoryServicesStatusService.jobSaveStatusServicesNfe();
	}

}
