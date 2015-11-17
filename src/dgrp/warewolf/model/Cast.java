package dgrp.warewolf.model;

import java.util.ArrayList;

public class Cast {

    // 配役名称
    private String castName;

    // 参加人数
    private String playerNum;

    // 難易度
    private DifficultyEnum difficulty;

    // 配役リスト
    private ArrayList<Player> roleList;

    // 公表配役名称
    private String openCastName;



    public Cast(String castName, DifficultyEnum difficulty, String playerNum) {
        this.castName = castName;
        this.difficulty = difficulty;
        this.playerNum = playerNum;
    }

    /**
     * 配役ごとの目標殺害数を取得
     * @return 目標殺害数
     */
    public int getTargetVictimCount() {

        switch(Integer.parseInt(getPlayerNum())) {
            case 4 :
            case 5 :
            case 6 :
                return 1;

            case 7 :
            case 8 :
            case 9 :
                return 2;

            case 10 :
            case 11 :
            case 12 :
                return 3;

            case 13 :
            case 14 :
            case 15 :
                return 4;

            case 16 :
                return 5;

            default :
                return 0;
        }
    }

    public String getCastName() {
        return castName;
    }
    public void setCastName(String castName) {
        this.castName = castName;
    }
    public String getPlayerNum() {
        return playerNum;
    }
    public void setPlayerNum(String playerNum) {
        this.playerNum = playerNum;
    }
    public DifficultyEnum getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(DifficultyEnum difficulty) {
        this.difficulty = difficulty;
    }
    public ArrayList<Player> getRoleList() {
        return roleList;
    }
    public void setRoleList(ArrayList<Player> roleList) {
        this.roleList = roleList;
    }
    public String getOpenCastName() {
        return openCastName;
    }
    public void setOpenCastName(String openCastName) {
        this.openCastName = openCastName;
    }

    /**
     * モノミを含み得る配役かどうか
     * @return モノミを含み得る配役かどうか
     */
    public boolean isPossibilityMonomi() {

        // いーじぃの場合は常にfalse
        if (DifficultyEnum.EASY.equals(difficulty)) {
            return false;
        }

        // のーまる、えくすとりぃむでモノミを含み得る配役人数は7-16人時
        if (Integer.parseInt(playerNum) < 7) {
            return false;

        } else {
            return true;
        }
    }

    /**
     * 絶望病患者を含み得る配役かどうか
     * @return 絶望病患者を含み得る配役かどうか
     */

    public boolean isPossibilityPatient() {

        // いーじぃの場合は常にfalse
        if (DifficultyEnum.EASY.equals(difficulty)) {
            return false;
        }

        // のーまる、えくすとりぃむで絶望病患者を含み得る配役人数は4-6人時
        if (Integer.parseInt(playerNum) < 7) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * 裏切り者を含み得る配役かどうか
     * @return 裏切り者を含み得る配役かどうか
     */

    public boolean isPossibilityTraitor() {

        // いーじぃの場合は常にfalse
        if (DifficultyEnum.EASY.equals(difficulty)) {
            return false;
        }

        // のーまる、えくすとりぃむで裏切り者を含み得る配役人数は7-16人時
        if (Integer.parseInt(playerNum) < 7) {
            return false;

        } else {
            return true;
        }
    }

    /**
     * 超高校級の絶望を含み得る配役かどうか
     * @return 超高校級の絶望を含み得る配役かどうか
     */

    public boolean isPossibilityDespair() {

        // いーじぃの場合は常にfalse
        if (DifficultyEnum.EASY.equals(difficulty)) {
            return false;
        }

        // のーまる、えくすとりぃむで超高校級の絶望を含み得る配役人数は7-16人時
        if (Integer.parseInt(playerNum) < 7) {
            return false;

        } else {
            return true;
        }
    }
}
