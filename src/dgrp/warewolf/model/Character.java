package dgrp.warewolf.model;

public class Character {

    // キャラ名
    private String characterName;

    // キャラ能力
    private Ability characterAbility;

    // 女かどうか
    private boolean isFemale;

    // キャラクターカード画像のURL
    private String characterImageUrl;

    public Character(String characterName, Ability ability, boolean isFemale, String imageUrl) {
        this.characterName = characterName;
        this.characterAbility = ability;
        this.isFemale = isFemale;
        this.characterImageUrl = imageUrl;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Ability getCharacterAbility() {
        return characterAbility;
    }

    public void setCharacterAbility(Ability characterAbility) {
        this.characterAbility = characterAbility;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean isFemale) {
        this.isFemale = isFemale;
    }

    public String getCharacterImageUrl() {
        return characterImageUrl;
    }

    public void setCharacterImageUrl(String characterImageUrl) {
        this.characterImageUrl = characterImageUrl;
    }

}
