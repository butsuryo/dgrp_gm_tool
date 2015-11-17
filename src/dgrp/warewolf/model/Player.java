package dgrp.warewolf.model;

import dgrp.warewolf.WareWolfItemFactory;

public class Player {

    // 役職
    private RoleEnum role;

    // 参加者名
    private String playerName;

    // キャラクター
    private Character character;

    // アイテム1
    private Item item1;

    // アイテム2
    private Item item2;

    // 引き直し前アイテム1
    private Item beforeRedrawItem1;

    // 引き直し前アイテム2
    private Item beforeRedrawItem2;

    // アイテム引き直し済みかどうか
    private boolean isItemRedraw;

    // 死んだかどうか
    private boolean isDead;


    public Player(RoleEnum roleEnum) {
        this.role = roleEnum;
        this.isItemRedraw = false;
        this.isDead = false;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Item getItem1() {
        return item1;
    }

    public void setItem1(Item item1) {
        this.item1 = item1;
    }

    public Item getItem2() {
        return item2;
    }

    public void setItem2(Item item2) {
        this.item2 = item2;
    }

    public Item getBeforeRedrawItem1() {
        return beforeRedrawItem1;
    }
    public void setBeforeRedrawItem1(Item beforeRedrawItem1) {
        this.beforeRedrawItem1 = beforeRedrawItem1;
    }
    public Item getBeforeRedrawItem2() {
        return beforeRedrawItem2;
    }
    public void setBeforeRedrawItem2(Item beforeRedrawItem2) {
        this.beforeRedrawItem2 = beforeRedrawItem2;
    }
    public boolean isItemRedraw() {
        return isItemRedraw;
    }
    public void setItemRedraw(boolean isItemRedraw) {
        this.isItemRedraw = isItemRedraw;
    }
    public boolean isDead() {
        return isDead;
    }
    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    /**
     * アイテム2枚の引き直し処理
     * @param newItem1
     * @param newItem2
     */
    public void redrawItems(Item newItem1, Item newItem2) {

        // 現在のアイテムを破棄山札に追加
        WareWolfItemFactory.spendOneItem(item1);
        WareWolfItemFactory.spendOneItem(item2);

        // 現在のアイテムを引き直し前アイテムにセット
        this.beforeRedrawItem1 = this.item1;
        this.beforeRedrawItem2 = this.item2;

        // 引き直しアイテムを現アイテムにセット
        this.item1 = newItem1;
        this.item2 = newItem2;

        // 引き直しフラグを立てる
        this.isItemRedraw = true;
    }

    /**
     * アイテム引き直し処理のキャンセル
     */
    public void redrawItemCancel() {

        // 引いてしまったアイテムを破棄山札に追加
        WareWolfItemFactory.spendOneItem(item1);
        WareWolfItemFactory.spendOneItem(item2);

        // 引き直し前アイテムを現在のアイテムにセット
        this.item1 = this.beforeRedrawItem1;
        this.item2 = this.beforeRedrawItem2;

        // 引き直し前アイテムを破棄
        this.beforeRedrawItem1 = null;
        this.beforeRedrawItem2 = null;

        // 引き直しフラグを戻す
        this.isItemRedraw = false;
    }


}
