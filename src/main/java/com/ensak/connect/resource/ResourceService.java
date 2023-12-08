package com.ensak.connect.resource;

import com.ensak.connect.resource.model.Resource;
import com.ensak.connect.resource.model.ResourceOwner;
import com.ensak.connect.util.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final StorageService storageService;
    private final ResourceRepository resourceRepository;
    public Resource createResource(ResourceOwner rowner, ResourceType rtype, MultipartFile file){

            String filename = storageService.store(file, rowner.getAllowedExtensions());

            Resource resource = Resource.builder()
                    .owner(rowner)
                    .type(rtype)
                    .filename(filename)
                    .build();
            resourceRepository.save(resource);

            return resource;
    }

    public List<Resource> getAllOwnerResource(ResourceOwner rowner){
        return resourceRepository.findAllByOwner(rowner).orElse(null);
    }

    public List<Resource> getAllOwnerResource(ResourceOwner rowner,ResourceType type){
        return resourceRepository.findAllByOwnerAndType(rowner,type).orElse(null);
    }


    public Resource updateResource(Resource resource, ResourceType resourceType, MultipartFile file) {
        String filename = storageService.store(file, resource.getOwner().getAllowedExtensions());

        String oldFilename = resource.getFilename();

        resource.setFilename(filename);
        resource.setType(resourceType);

        resourceRepository.save(resource);

        storageService.delete(oldFilename);

        return resource;
    }
}
