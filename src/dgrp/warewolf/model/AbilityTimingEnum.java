package dgrp.warewolf.model;


public enum AbilityTimingEnum {

    // 希望のカケラ、武神のお守り
    // 苗木(常駐)、狛枝(常駐)、桑田(発動)、大神(発動)、終里(発動)、七海(発動)、辺古山(発動)
    MORNING("朝時間"),

    // 校章バッジ、塩、万力
    // 弐大(発動)、罪木(発動)
    MORNING_DEAD("朝時間、死亡確定時"),

    // 動くこけし、キルリアンカメラ、ボージョボー人形
    // 十神(使用)、十神2(使用)、大和田(使用)、セレス(使用？)、田中(使用)、左右田(使用)、花村(使用)、ソニア(使用)、澪田(使用)
    DAY("昼時間"),

    // ギャル江ノ島(使用)
    TRIAL_BEFORE("学級裁判前"),

    // G-SICK、絶望バット
    // 石丸(使用)
    TRIAL("ノンストップ議論"),

    // 超技林、ミネラルウォーター
    // 舞園(使用)、朝日奈(使用)、日向(使用)、九頭龍(使用)
    VOTE_BEFORE("投票前"),

    // 水晶のドクロ
    VOTE_DEAD("投票後、おしおき死亡時"),

    // アルターボール、黄金銃、おでこのメガネ、ジャスティスロボ、誰かの卒業アルバム、無言電話
    // 霧切(使用)、葉隠(使用)
    NIGHT("夜時間"),

    // 不二咲(常駐)、黒幕江ノ島(常駐)、山田(発動)、西園寺(発動)、むくろ(使用)、小泉(使用)
    ALL("全時間帯"),

    // 時間帯問わず死亡時
    // 腐川(発動)、ジェノサイダー(発動)
    DEAD("死亡時");

    public final String abilityTimingLabel;

    private AbilityTimingEnum(String abilityTimingLabel) {
        this.abilityTimingLabel = abilityTimingLabel;
    }

}
