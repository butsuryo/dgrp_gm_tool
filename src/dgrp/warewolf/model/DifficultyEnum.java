package dgrp.warewolf.model;


public enum DifficultyEnum {

    EASY("いーじぃ"),
    NORMAL("のーまる"),
    EXTREAM("えくすとりぃむ");

    public final String difficultyName;

    private DifficultyEnum(String difficulty) {
        this.difficultyName = difficulty;
    }

    public static DifficultyEnum getDifficultyEnumByName(String difficulty) {

        for (DifficultyEnum difficultyEnum : DifficultyEnum.values()) {
            if (difficultyEnum.difficultyName.equals(difficulty)) {
                return difficultyEnum;
            }
        }
        return null;
    }

}
