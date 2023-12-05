package com.ensak.connect.util.storage;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "\"C:\\Users\\iopha\\Documents\\WorkSpace\\ensak-connect-backend\\storage\"";

	public void setLocation(String location) {
		this.location = location;
	}

}
