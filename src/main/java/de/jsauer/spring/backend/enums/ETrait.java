package de.jsauer.spring.backend.enums;

/**
 * Contains hidden traits.
 */
public enum ETrait {
    AIRBORNE("Airborne"),
    GROUNDED("Grounded"),
    UNKNOWN("Not yet implemented. Please contact admin");

    private final String name;

    private ETrait(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
