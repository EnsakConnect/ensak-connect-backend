package com.ensak.connect.util.storage;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	@Value("${storage.location}")
	private String location ;

	public void setLocation(String location) {
		this.location = location;
	}

}
