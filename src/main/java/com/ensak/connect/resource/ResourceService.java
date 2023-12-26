package com.ensak.connect.resource;

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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final StorageService storageService;
    private final ResourceRepository resourceRepository;
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

    public Resource useResource(Integer resource_id, User user ) throws ForbiddenException {
        Resource resource = resourceRepository.findById(resource_id).orElseThrow(
                () -> new NotFoundException("Resource with ID: " + resource_id + " not found.")
        );
        if(Objects.equals(resource.getUser().getId(), user.getId())){
            resource.setUsed(true);
            resourceRepository.save(resource);
        }
        else
            throw new ForbiddenException("User " + user.getId() + " is not allowed to use resource " + resource_id);
        return resource;
    }
    public void unuseResource(Integer resource_id, User user) throws ForbiddenException {
        Resource resource = resourceRepository.findById(resource_id).orElseThrow(
                () -> new NotFoundException("Resource with ID: " + resource_id + " not found.")
        );

        if(Objects.equals(resource.getUser().getId(), user.getId())) {
            resource.setUsed(false);
            resourceRepository.save(resource);
        } else {
            throw new ForbiddenException("User " + user.getId() + " is not allowed to unuse resource " + resource_id);
        }
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
