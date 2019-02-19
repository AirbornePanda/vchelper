package de.jsauer.valhalla.backend.entities;

import javax.persistence.Entity;

@Entity
public class Item extends de.jsauer.valhalla.backend.entities.Entity {

    public Item() {

    }

    public Item (final String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
