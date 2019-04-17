package de.jsauer.valhalla.backend.entities;

import javax.persistence.Entity;

@Entity
public class Gear extends de.jsauer.valhalla.backend.entities.Entity {

    public Gear() {

    }

    private String valkypediaId;

    private String name;

    public String getValkypediaId() {
        return valkypediaId;
    }

    public void setValkypediaId(String valkypediaId) {
        this.valkypediaId = valkypediaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
