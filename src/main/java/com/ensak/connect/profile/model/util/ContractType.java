package com.ensak.connect.profile.model.util;

public enum ContractType {
    Internship,
    FullTime,
    PartTime,
    FixedTerm,
    Freelance;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

}
