package com.ensak.connect.resource;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.resource.model.Resource;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.util.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/resources")
public class ResourceController {

    private final StorageService storageService;
    private final AuthenticationService authenticationService;
    private final ResourceService resourceService;

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<org.springframework.core.io.Resource> displayFile(@PathVariable String filename) {
        org.springframework.core.io.Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        String contentType = URLConnection.guessContentTypeFromName(file.getFilename());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        User user = this.authenticationService.getAuthenticatedUser();
        Resource resource = resourceService.createResource(user,file);

        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAll(){
        User user = this.authenticationService.getAuthenticatedUser();
        List<Resource> resources = resourceService.getAllUserResource(user);
        return ResponseEntity.ok(resources);
    }

}
