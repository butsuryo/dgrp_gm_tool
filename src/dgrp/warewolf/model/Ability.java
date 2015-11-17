package dgrp.warewolf.model;

public class Ability {

    // 能力名(アイテム名)
    private String abilityName;

    // 能力説明
    private String abilityDescription;

    // 現在発動可能かどうか
    private boolean isAvailable;

    // 発動可能タイミング
    private AbilityTimingEnum abilityTimingEnum;

    public Ability(String abilityName, String abilityDescription, AbilityTimingEnum abilityTimingEnum) {
        this.abilityName = abilityName;
        this.abilityDescription = abilityDescription;
        this.abilityTimingEnum = abilityTimingEnum;
    }

    public String getAbilityName() {
        return abilityName;
    }
    public String getAbilityDescription() {
        return abilityDescription;
    }
    public AbilityTimingEnum getAbilityTimingEnum() {
        return abilityTimingEnum;
    }

}
