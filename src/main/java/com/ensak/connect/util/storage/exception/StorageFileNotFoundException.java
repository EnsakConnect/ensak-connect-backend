package com.ensak.connect.util.storage.exception;

import com.ensak.connect.util.storage.exception.StorageException;

public class StorageFileNotFoundException extends StorageException {

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
