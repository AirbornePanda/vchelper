package de.jsauer.valhalla.backend.enums;

/**
 * Contains all races.
 */
public enum ERace {
    AESIR("Aesir"),
    HUMAN("Human"),
    ELF("Elf"),
    DWARF("Dwarf"),
    THERIAN("Therian"),
    JOTUM("Jotun"),
    BEAST("Beast"),
    UNKNOWN("Not yet implemented. Please contact admin");

    private final String name;

    private ERace(final String description){
        name = description;
    }

    public String getName() {
        return name;
    }
}
