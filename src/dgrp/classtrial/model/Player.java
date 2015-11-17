package dgrp.classtrial.model;


/**
 * 1参加者の情報を表すオブジェクト
 * @author butsuryo
 *
 */
public class Player {

    // 役職
    private RoleEnum role;

    // 交換後役職
    private RoleEnum afterTradeRole;

    // 参加者名
    private String playerName;

    // キャラクター
    private Character character;

    // 欠席者フラグ
    private boolean isAbsent;


    /**
     * コンストラクタ
     * デフォルトの欠席者フラグを設定する
     */
    public Player() {
        // デフォルトは欠席者フラグOFF
        this.setAbsent(false);
    }


    public Player(RoleEnum roleEnum) {
        this.role = roleEnum;
    }

    /**
     * 交換前役職を返す
     * @return 交換前役職
     */
    public RoleEnum getDefaultRole() {
        return role;
    }

    /**
     * 役職を反す(交換している場合は交換後)
     * @return 役職(交換している場合は交換後)
     */
    public RoleEnum getCurrentRole() {
        if (this.afterTradeRole != null) {
            return this.afterTradeRole;
        } else {
            return role;
        }
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

    public boolean isAbsent() {
        return isAbsent;
    }

    public void setAbsent(boolean isAbsent) {
        this.isAbsent = isAbsent;
    }

    public RoleEnum getAfterTradeRole() {
        return afterTradeRole;
    }

    public void setAfterTradeRole(RoleEnum afterTradeRole) {
        this.afterTradeRole = afterTradeRole;
    }

}
