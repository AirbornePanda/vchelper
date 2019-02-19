package de.jsauer.valhalla.backend.enums;

public enum  EFieldEffect {
    GRAVITY("Gravity"),
    MIST("Mist"),
    ECLIPSE("Eclipse"),
    VOID("Void"),
    UNKNOWN("Not yet implemented. Please contact admin");

    private final String name;

    private EFieldEffect(final String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
