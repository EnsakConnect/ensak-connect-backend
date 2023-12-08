package com.ensak.connect.resource.model;

public interface IResourceOwner {
    public Integer getId();
    public abstract String getResourceOwnerType();

    public abstract String[] getAllowedExtensions();
}
