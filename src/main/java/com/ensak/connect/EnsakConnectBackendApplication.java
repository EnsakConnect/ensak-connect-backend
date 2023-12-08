package com.ensak.connect;

import com.ensak.connect.util.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EnsakConnectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnsakConnectBackendApplication.class, args);
	}

}
