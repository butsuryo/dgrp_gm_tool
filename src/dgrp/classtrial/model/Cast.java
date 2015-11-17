package dgrp.classtrial.model;

import java.util.ArrayList;

public class Cast {

    // 配役ラベル
    private String castLabel;

    // 参加人数
    private String playerNum;

    // 配役リスト
    private ArrayList<Player> playerList;


    public Cast(String castName, String playerNum) {
        this.castLabel = castName;
        this.playerNum = playerNum;
    }

    public String getCastName() {
        return castLabel;
    }
    public void setCastName(String castName) {
        this.castLabel = castName;
    }
    public String getPlayerNum() {
        return playerNum;
    }
    public void setPlayerNum(String playerNum) {
        this.playerNum = playerNum;
    }
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    /**
     * 指定した役職と一致するオブジェクトをリストで返す
     * @param getRole 取得したい役職
     * @param isDefault 交換前役職かどうか(falseの場合交換後役職で判定)
     * @return 指定した役職と一致するオブジェクトのリスト
     */
    public ArrayList<Player> getRoleModelList(RoleEnum getRole, boolean isDefault) {

        ArrayList<Player> list = new ArrayList<Player>();

        for (Player player : this.playerList) {

            if (isDefault) {
                if (getRole.equals(player.getDefaultRole())) {
                    list.add(player);
                }
            } else {
                if (getRole.equals(player.getCurrentRole())) {
                    list.add(player);
                }
            }
        }
        return list;
    }

    /**
     * 指定した役職と一致するキャラクター名のリストを返す
     * @param getRole 取得したい役職
     * @param isDefault 交換前役職かどうか(falseの場合交換後役職で判定)
     * @return 指定した役職と一致するキャラクター名のリスト
     */
    public ArrayList<String> getCharacterNameList(RoleEnum getRole, boolean isDefault) {
        ArrayList<Player> list = getRoleModelList(getRole, isDefault);
        ArrayList<String> nameList = new ArrayList<String>();
        for (Player model : list) {
            nameList.add(model.getCharacter().getCharacterName());
        }
        return nameList;
    }

    /**
     * 指定した役職と一致するプレイヤー名のリストを返す
     * @param getRole 取得したい役職
     * @param isDefault 交換前役職かどうか(falseの場合交換後役職で判定)
     * @return 指定した役職と一致するプレイヤー名のリスト
     */
    public ArrayList<String> getPlayerNameList(RoleEnum getRole, boolean isDefault) {
        ArrayList<Player> list = getRoleModelList(getRole, isDefault);
        ArrayList<String> nameList = new ArrayList<String>();
        for (Player model : list) {
            nameList.add(model.getPlayerName());
        }
        return nameList;
    }

    /**
     * 役職だけをリストで返す
     * @return 役職リスト
     */
    public ArrayList<RoleEnum> getRoleEnumList() {

        ArrayList<RoleEnum> roleEnumList = new ArrayList<RoleEnum>();
        for (Player model : playerList) {
            roleEnumList.add(model.getDefaultRole());
        }
        return roleEnumList;
    }

    /**
     * 現在ギャンブラーの交換が行われているか
     * @return boolean
     */
    public boolean isTraded() {
        for (Player model : playerList) {
            if (model.getAfterTradeRole() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * ギャンブラーの交換結果をリセットする
     */
    public void resetTrade() {

        if (isTraded()) {
            for (Player model : playerList) {
                model.setAfterTradeRole(null);
            }
        }
    }

    /**
     * ギャンブラーと対象の交換後役職を入れ替える
     * @param tradeTargetModel 交換対象のmodel
     */
    public void tradeGambler(Player tradeTargetModel) {

        // ギャンブラーのオブジェクトのRoleを交換先に変更
        for (Player beforeRole : playerList) {
            if (RoleEnum.GAMBLER.equals(beforeRole.getCurrentRole())) {
                beforeRole.setAfterTradeRole(tradeTargetModel.getDefaultRole());
            }
        }

        // 交換先のオブジェクトのRoleをギャンブラーに変更
        for (Player beforeRole : playerList) {
            if (tradeTargetModel.equals(beforeRole)) {
                beforeRole.setAfterTradeRole(RoleEnum.GAMBLER);
            }
        }
    }


    /**
     * キャラクター名から参加者オブジェクトを取得する
     * @param characterName キャラクター名
     * @return 参加者オブジェクト
     */
    public Player getRoleByCharacterName(String characterName) {

        for (Player player : playerList) {
            if (player.getCharacter().getCharacterName().equals(characterName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * 欠席者を除く参加者の中にクロが一人でもいるかどうか
     * @return 欠席者を除く参加者の中にクロが一人でもいるかどうか
     */
    public boolean isNonBlack() {
        for (Player player : playerList) {
            if (!player.isAbsent() && RoleEnum.BLACK.equals(player.getCurrentRole())) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Player> getNonAbsentList() {

        ArrayList<Player> nonAbsentList = new ArrayList<Player>();
        for (Player player : playerList) {
            if (!player.isAbsent()) {
                nonAbsentList.add(player);
            }
        }
        return nonAbsentList;
    }

}
