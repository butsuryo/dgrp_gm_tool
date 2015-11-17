package dgrp.classtrial.model;


/**
 * 役職定義
 * @author butsuryo
 *
 */
public enum RoleEnum {

    HOPE("希望"),
    BLACK("クロ"),
    DETECTIVE("探偵"),
    GAMBLER("ギャンブラー"),
    DESPAIR("絶望");

    public final String roleName;

    private RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 役職がシロ陣営かどうかを返す
     * @return シロ陣営ならtrue、クロ陣営ならfalse
     */
    public boolean isWhite() {
        switch (this) {
            case HOPE :
            case DETECTIVE :
            case GAMBLER :
                return true;
            default:
                return false;
        }
    }

}
