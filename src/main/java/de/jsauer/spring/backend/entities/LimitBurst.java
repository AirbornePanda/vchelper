package de.jsauer.spring.backend.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LimitBurst extends de.jsauer.spring.backend.entities.Entity {

    private String garmId;

    @OneToMany (mappedBy = "limitBurst", fetch = FetchType.EAGER)
    private List<Hero> heroes = new ArrayList<>();

    private String name;

    @Lob
    private String description;

    public LimitBurst() {

    }

    public String getGarmId() {
        return garmId;
    }

    public void setGarmId(String garmId) {
        this.garmId = garmId;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addHero(final Hero hero) {
        if(!this.heroes.contains(hero)) {
            heroes.add(hero);
        }
    }

    public void remove(final Hero hero) {
        heroes.remove(hero);
    }
}
