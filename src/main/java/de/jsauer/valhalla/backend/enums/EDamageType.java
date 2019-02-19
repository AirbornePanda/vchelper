package de.jsauer.valhalla.backend.enums;

public enum EDamageType {
    P_ATK("Physical Attack", "ATK"),
    M_ATK("Magical Attack", "MATK"),
    UNKNOWN("Not yet implemented. Please contact admin", "UNKWN");

    private final String nameLong;
    private final String nameShort;

    private EDamageType(final String nameLong, final String nameShort){
        this.nameLong = nameLong;
        this.nameShort = nameShort;
    }

    public String getNameLong() {
        return nameLong;
    }

    public String getNameShort() {
        return nameShort;
    }
}
