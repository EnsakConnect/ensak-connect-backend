package com.ensak.connect.resource;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.resource.model.Resource;
import com.ensak.connect.util.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final StorageService storageService;
    private final ResourceRepository resourceRepository;
    private final AuthenticationService authenticationService;
    public Resource createResource(User user, MultipartFile file){

            String filename = storageService.store(file);

            Resource resource = Resource.builder()
                    .user(user)
                    .used(false)
                    .filename(filename)
                    .build();
            resourceRepository.save(resource);

            return resource;
    }

    public List<Resource> getAllUserResource(User user){
        return resourceRepository.findAllByUser(user).orElse(Collections.emptyList());
    }

    public void useResource(Integer resource_id) throws ForbiddenException {
        Resource resource = resourceRepository.findById(resource_id).orElseThrow(
                () -> new NotFoundException("Resource with ID: " + resource_id + " not found.")
        );
        User user = authenticationService.getAuthenticatedUser();
        if(resource.getUser().getId() == user.getId()){
            resource.setUsed(true);
            resourceRepository.save(resource);
        }
        else
            throw new ForbiddenException("User " + user.getId() + " is not allowed to use resource " + resource_id);

    }

    public void deleteResource(Resource resource){
        storageService.delete(resource.getFilename());
        resourceRepository.delete(resource);
    }

    public void deleteAllResources(List<Resource> resources){
        for(Resource resource : resources){
            storageService.delete(resource.getFilename());
            resourceRepository.delete(resource);
        }
    }
}
