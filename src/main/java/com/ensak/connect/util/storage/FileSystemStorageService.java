package com.ensak.connect.util.storage;

import com.ensak.connect.util.storage.exception.FileExtensionNotAllowedException;
import com.ensak.connect.util.storage.exception.StorageException;
import com.ensak.connect.util.storage.exception.StorageFileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Profile("dev")
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	public String randomSequence(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			sb.append(characters.charAt(random.nextInt(characters.length())));
		}

		return sb.toString();
	}

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		if(properties.getLocation().trim().isEmpty()){
            throw new StorageException("File upload location can not be Empty.");
        }

		this.rootLocation = Paths.get(properties.getLocation());
	}

	private boolean allowedExtension(String[] extensions, String filename){

		int index = filename.lastIndexOf('.');
		if(index == -1)
			return false;

		for(String ext : extensions){
			if(ext.equals(filename.substring(index+1)))
				return true;
		}

		return false;
	}
	@Override
	public String store(MultipartFile file, String[] extensions) {
			
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}

			if(!allowedExtension(extensions, file.getOriginalFilename())){
				throw new FileExtensionNotAllowedException("File Extension Not Allowed");
			}
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
			try {
				String newfilename = randomSequence(10)+"."+extension;
				Files.copy(file.getInputStream(), this.rootLocation.resolve(newfilename));
				return newfilename;
			}
			catch (IOException e){
				throw new StorageException("Failed to store file.", e);
			}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void delete(String filename) {
		try {
			FileSystemUtils.deleteRecursively(load(filename));
		} catch (IOException e) {
			throw new StorageException("Could not delete file :"+filename,e);
		}
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
