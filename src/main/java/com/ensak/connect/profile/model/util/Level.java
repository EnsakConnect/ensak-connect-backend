package com.ensak.connect.profile.model.util;

public enum Level {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
