package com.ensak.connect.util.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Profile("test")
@Service
@RequiredArgsConstructor
public class TestFileSystemStorageService implements StorageService{
    @Override
    public void init() {

    }

    @Override
    public String store(MultipartFile file, String[] extensions) {
        return null;
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void delete(String filename) {

    }
}
