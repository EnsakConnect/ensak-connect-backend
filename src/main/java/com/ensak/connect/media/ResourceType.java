package com.ensak.connect.media;

public enum ResourceType {

    Picture,
    File,
    Resume,
    ProfilePicture,
    Banner;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
