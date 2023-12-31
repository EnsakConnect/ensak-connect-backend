package com.ensak.connect.resource;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.resource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    /*
    public Optional<List<Resource>> findAllByOwner(ResourceOwner owner);
    public Optional<List<Resource>> findAllByOwnerIdAndOwnerTypeAndType(Integer ownerId,String ownerType, ResourceType type);
     */

    public Optional<List<Resource>> findAllByUser(User user);
}
