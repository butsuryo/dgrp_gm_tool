package dgrp.classtrial.model;

import java.util.ArrayList;

public class Log {

    private String timestamp;
    private String name;
    private String message;
    private String color;
    private ArrayList<OptionEnum> optionArray = new ArrayList<OptionEnum>();

    public Log(String timestamp, String name, String message,
            String color, boolean isBold, boolean isItalic, boolean isStrike, String size) {

        this.timestamp = timestamp;
        this.name = name;
        this.message = message;
        this.color = color;

        // オプション
        if (isBold) {
            optionArray.add(OptionEnum.BOLD);
        }

        if (isItalic) {
            optionArray.add(OptionEnum.ITALIC);
        }

        if (isStrike) {
            optionArray.add(OptionEnum.STRIKE);
        }

        if ("small".equals(size)) {
            optionArray.add(OptionEnum.SMALL);
        } else if ("large".equals(size)) {
            optionArray.add(OptionEnum.BIG);
        } else {
            optionArray.add(OptionEnum.NORMAL);
        }
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<OptionEnum> getOptionArray() {
        return optionArray;
    }

    public void setOptionArray(ArrayList<OptionEnum> optionArray) {
        this.optionArray = optionArray;
    }


}
