package de.jsauer.valhalla.backend.entities;

import de.jsauer.valhalla.backend.enums.EDamageType;
import de.jsauer.valhalla.backend.enums.EElement;
import de.jsauer.valhalla.backend.enums.EFieldEffect;
import de.jsauer.valhalla.backend.enums.EGender;
import de.jsauer.valhalla.backend.enums.ERace;
import de.jsauer.valhalla.backend.enums.ETrait;
import de.jsauer.valhalla.backend.enums.EType;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hero extends de.jsauer.valhalla.backend.entities.Entity {

    private String garmId;

    private String name;

    @Column(length = 500)
    private String bio;

    private Integer initialRarity;

    private EType type;

    private ERace race;

    private EGender gender;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ETrait> traits = new ArrayList<>();

    private EDamageType damageType;

    private EFieldEffect fieldResistance;

    private EElement skillElement;

    private String skillName;

    @Column(length = 500)
    private String skillDescription;

    @ManyToOne
    private LimitBurst limitBurst;

    @Lob
    private byte[] image;

    public Hero() {

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

    public EFieldEffect getFieldResistance() {
        return fieldResistance;
    }

    public void setFieldResistance(EFieldEffect fieldResistance) {
        this.fieldResistance = fieldResistance;
    }

    public EElement getSkillElement() {
        return skillElement;
    }

    public void setSkillElement(EElement skillElement) {
        this.skillElement = skillElement;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

    public LimitBurst getLimitBurst() {
        return limitBurst;
    }

    public void setLimitBurst(LimitBurst limitBurst) {
        if (this.limitBurst != null && this.limitBurst != limitBurst) {
            this.limitBurst.remove(this);
            limitBurst.addHero(this);
        }
        this.limitBurst = limitBurst;
    }
}
