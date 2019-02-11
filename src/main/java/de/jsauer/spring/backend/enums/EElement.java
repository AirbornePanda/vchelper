package de.jsauer.spring.backend.enums;

/**
 * Contains all elements
 */
public enum EElement {
    LIGHT("Light"),
    DARKNESS("Darkness"),
    WATER("Water"),
    FIRE("Fire"),
    EARTH("Earth"),
    OTHER("Other"),
    NEUTRAL("Neutral");

    private final String name;

    private EElement (final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
