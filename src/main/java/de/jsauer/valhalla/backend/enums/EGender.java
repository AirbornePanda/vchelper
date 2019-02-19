package de.jsauer.valhalla.backend.enums;

public enum  EGender {
    MALE("Male"),
    FEMALE("Female"),
    UNDEFINED("Undefined"),
    UNKNOWN("Not yet implemented. Please contact admin");

    private final String name;

    private EGender(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
