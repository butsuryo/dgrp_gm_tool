package dgrp.classtrial.model;


/**
 * サイズ定義(ログの文字用)
 * @author butsuryo
 *
 */
public enum OptionEnum {

    BOLD("bold"),
    ITALIC("normal"),
    STRIKE("large"),
    SMALL("small"),
    NORMAL("normal"),
    BIG("large");

    public final String optionName;

    private OptionEnum(String optionName) {
        this.optionName = optionName;
    }

}
