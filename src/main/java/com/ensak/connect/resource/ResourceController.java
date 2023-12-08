package com.ensak.connect.resource;

import com.ensak.connect.util.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLConnection;

@Controller
@RequiredArgsConstructor
public class ResourceController {

    private final StorageService storageService;

    @GetMapping("/api/v1/resources/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> displayFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        String contentType = URLConnection.guessContentTypeFromName(file.getFilename());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

}
