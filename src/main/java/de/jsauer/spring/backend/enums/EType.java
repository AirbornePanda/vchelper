package de.jsauer.spring.backend.enums;

/**
 * Describes the type of combat.
 */
public enum EType {
    MELEE("Melee"),
    MAGIC("Magic"),
    RANGED("Ranged"),
    UNKNOWN("Not yet implemented. Please contact admin.");

    private final String fieldDescription;

    private EType(final String description){
        fieldDescription = description;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}
