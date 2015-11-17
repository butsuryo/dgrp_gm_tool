package dgrp.warewolf.model;

public class Item {

    // アイテム名
    private String itemName;

    // 効果など
    private Ability ability;

    // アイテム画像のURL
    private String itemImageUrl;

    public Item(String itemName, Ability ability, String itemImageUrl) {
        this.itemName =itemName;
        this.ability = ability;
        this.itemImageUrl = itemImageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

}
