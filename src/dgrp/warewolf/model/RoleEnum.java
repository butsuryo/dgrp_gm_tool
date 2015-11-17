package dgrp.warewolf.model;


public enum RoleEnum {

    WHITE("シロ"),
    BLACK("クロ"),
    EGO("アルターエゴ"),
    MONOMI("モノミ"),
    TRAITOR("裏切り者"),
    DESPAIR("超高校級の絶望"),
    PATIENT("絶望病患者");

    public final String roleName;

    private RoleEnum(String roleName) {
        this.roleName = roleName;
    }


    /**
     * アルターエゴの判別結果が●かどうかを返す
     *
     * @param victimCnt 現在の犠牲者数
     * @return ●かどうか
     */
    public boolean isEgoResultBlack(int victimCnt){

        switch(this){
            case BLACK:
            case MONOMI:
            case PATIENT:
            case TRAITOR:
                return true;

            // 一人でも犠牲者が出ていれば●
            case DESPAIR:
                if (victimCnt > 0) {
                    return true;
                } else {
                    return false;
                }

            case WHITE:
                return false;

            default:
                return false;
        }
    }

}
