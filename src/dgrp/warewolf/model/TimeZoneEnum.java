package dgrp.warewolf.model;


/**
 * 時間帯
 * @author butsuryo
 *
 */
public enum TimeZoneEnum {

    MORNING("朝時間"),
    DAY("昼時間"),
    BRIEFING("ブリーフィング"),
    TRIAL("ノンストップ議論"),
    VOTE("投票"),
    NIGHT("夜時間"),
    LATE_NIGHT("深夜");

    public final String timeZoneLabel;

    private TimeZoneEnum(String timeZoneLabel) {
        this.timeZoneLabel = timeZoneLabel;
    }

}
