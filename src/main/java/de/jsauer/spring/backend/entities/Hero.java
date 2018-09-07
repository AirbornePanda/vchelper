package de.jsauer.spring.backend.entities;

import de.jsauer.spring.backend.enums.EDamageType;
import de.jsauer.spring.backend.enums.EElement;
import de.jsauer.spring.backend.enums.EFieldEffect;
import de.jsauer.spring.backend.enums.EGender;
import de.jsauer.spring.backend.enums.ERace;
import de.jsauer.spring.backend.enums.ETrait;
import de.jsauer.spring.backend.enums.EType;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String garmId;

    private String name;

    private String bio;

    private Integer initialRarity;

    private EType type;

    private ERace race;

    private EGender gender;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ETrait> traits = new ArrayList<>();

    private EDamageType damageType;

    private EFieldEffect fieldResistence;

    private EElement skillElement;

    @Lob
    private byte[] image;

    public Hero() {

    }

    public Long getId() {
        return id;
    }

    public String getGarmId() {
        return garmId;
    }

    public void setGarmId(String garmId) {
        this.garmId = garmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getInitialRarity() {
        return initialRarity;
    }

    public void setInitialRarity(Integer initialRarity) {
        this.initialRarity = initialRarity;
    }

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }

    public ERace getRace() {
        return race;
    }

    public void setRace(ERace race) {
        this.race = race;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public List<ETrait> getTraits() {
        return traits;
    }

    public void setTraits(List<ETrait> traits) {
        this.traits = traits;
    }

    public void addTrait(ETrait trait) {
        if (!traits.contains(trait)) {
            traits.add(trait);
        }
    }

    public void removeTrait(ETrait trait) {
        traits.remove(trait);
    }

    public EDamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(EDamageType damageType) {
        this.damageType = damageType;
    }

    public EFieldEffect getFieldResistence() {
        return fieldResistence;
    }

    public void setFieldResistence(EFieldEffect fieldResistence) {
        this.fieldResistence = fieldResistence;
    }

    public EElement getSkillElement() {
        return skillElement;
    }

    public void setSkillElement(EElement skillElement) {
        this.skillElement = skillElement;
    }
}
