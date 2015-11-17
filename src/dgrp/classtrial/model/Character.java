package dgrp.classtrial.model;

/**
 * キャラクターモデル
 * @author butsuryo
 *
 */
public class Character {

    // キャラ名
    private String characterName;


    public Character(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
