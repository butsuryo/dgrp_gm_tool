package dgrp.warewolf.model;

import java.util.ArrayList;

import dgrp.StringUtil;

/**
 * 1ゲームで使う情報の総合
 * どのビューからも使えるように、このインスタンス一つをActivatorで保持しておく
 *
 * @author butsuryo
 *
 */
public class GameData {

    // 参加者オブジェクトのリスト
    private ArrayList<Player> list;

    // 難易度
    private DifficultyEnum difficulty;

    // 配役
    private Cast cast;

    // 現在の時間帯
    private TimeZoneEnum currentTimeZone;

    // 現在の日にち
    private int day;

    // 本日の襲撃が成功するかどうか
    private boolean isAttackSuccess;

    // 最新の襲撃先
    private Player attackTarget;

    // 最新の調査先
    private Player egoTarget;

    // 最新の護衛先
    private Player monomiTarget;

    // 現在の殺害達成数
    private int victimCnt;

    // 襲撃が無効化される人々のリスト（モノミ護衛、黄金銃、ロボ、幸運組能力）
    private ArrayList<Player> invalidAttackList = new ArrayList<Player>();

    // 死体
    private Player victim;

    // モノミの爆死フラグ
    private boolean isMonomiGuard;


    public GameData(ArrayList<Player> list, DifficultyEnum difficulty, Cast cast) {

        // 参加者オブジェクト、難易度、配役はウィザードで設定したもの
        this.list = list;
        this.difficulty = difficulty;
        this.cast = cast;

        // 初期時間帯は深夜
        currentTimeZone = TimeZoneEnum.LATE_NIGHT;

        // 0日目
        day = 0;

        // 襲撃成功フラグの初期値をfalseに
        isAttackSuccess = false;

        // 殺害達成数の初期値を0に
        victimCnt = 0;

        // 爆死フラグオフ
        isMonomiGuard = false;
    }

    public ArrayList<Player> getList() {
        return list;
    }
    public int getDay() {
        return day;
    }
    public TimeZoneEnum getCurrentTimeZone() {
        return currentTimeZone;
    }
    public boolean isAttackSuccess() {
        return isAttackSuccess;
    }
    public void setAttackTarget(Player attackTarget) {
        this.attackTarget = attackTarget;
    }
    public Player getAttackTarget() {
        return attackTarget;
    }
    public void setEgoTarget(Player egoTarget) {
        this.egoTarget = egoTarget;
    }
    public Player getEgoTarget() {
        return egoTarget;
    }
    public void setMonomiTarget(Player monomiTarget) {
        this.monomiTarget = monomiTarget;
    }
    public Player getMonomiTarget() {
        return monomiTarget;
    }
    public int getVictimCnt() {
        return victimCnt;
    }
    public Cast getCast() {
        return cast;
    }
    public DifficultyEnum getDifficulty() {
        return difficulty;
    }
    public Player getVictim() {
        return victim;
    }
    public boolean isMonomiGuard() {
        return isMonomiGuard;
    }

    /**
     * アルターエゴの判定結果が●かどうかを返す
     * @return アルターエゴの判定結果が●かどうか
     */
    public boolean isEgoResultBlack() {
        if (egoTarget != null) {
            return egoTarget.getRole().isEgoResultBlack(victimCnt);
        } else {
            return false;
        }
    }

    /**
     * 指定した役職のキャラクター名を取得する
     *
     * @param roleEnum キャラクター名を取得したい役職
     * @return 指定した役職のキャラクター名
     */
    public String getCharaNameByRole(RoleEnum roleEnum) {
        Player model = getModelByRole(roleEnum);
        if (model != null) {
            return model.getCharacter().getCharacterName();
        }
        return StringUtil.EMPTY;
    }

    /**
     * 指定した役職のオブジェクトだけを取得する
     *
     * @param roleEnum オブジェクトを取得したい役職
     * @return 指定した役職のオブジェクト
     */
    public Player getModelByRole(RoleEnum roleEnum) {

        // 指定した役職のオブジェクトだけを取得
        for (Player model : list) {
            if (roleEnum.equals(model.getRole())) {
                return model;
            }
        }
        return null;
    }

    /**
     * 指定したキャラ名のオブジェクトだけを取得する
     *
     * @param charaName オブジェクトを取得したいキャラ名
     * @return 指定したキャラ名のオブジェクト
     */
    public Player getModelByCharaName(String charaName) {

        // 指定したキャラ名のオブジェクトだけを取得
        for (Player model : list) {
            if (charaName.equals(model.getCharacter().getCharacterName())) {
                return model;
            }
        }
        return null;
    }

    /**
     * 指定した役職が死んでいるかどうかを返す
     *
     * @param roleEnum 死んでいるかどうを取得したい役職
     * @return 指定した役職が死んでいるかどうか
     */
    public boolean isRoleDead(RoleEnum roleEnum) {
        Player model = getModelByRole(roleEnum);
        return model.isDead();
    }


//    /**
//     * クロのキャラクターの名前を取得する
//     * @return クロのキャラクターの名前
//     */
//    public String getBlackCharacterName() {
//
//        // クロのキャラクター名だけを取得
//        for (WareWolfRoleBaceModel role : this.list) {
//            if (WareWolfRoleEnum.BLACK.equals(role.getRole())) {
//                return role.getCharacter().getCharacterName();
//            }
//        }
//        return "";
//    }
//
//    /**
//     * クロのキャラクターのプレイヤー名を取得する
//     * @return クロのキャラクターのプレイヤー名
//     */
//    public String getBlackPlayerName() {
//
//        // クロのキャラクターのプレイヤー名だけを取得
//        for (WareWolfRoleBaceModel role : this.list) {
//            if (WareWolfRoleEnum.BLACK.equals(role.getRole())) {
//                return role.getPlayerName();
//            }
//        }
//        return "";
//    }

    /**
     * 時間帯を次へ送る
     */
    public void nextTimeZone() {

        switch (currentTimeZone) {
            case LATE_NIGHT :

                // 日付を進める
                day ++;

                // 死体の判定
                // モノミの護衛と襲撃先がぶつかった場合、モノミが爆死
                if (attackTarget == monomiTarget) {
                    victim = getModelByRole(RoleEnum.MONOMI);
                    isMonomiGuard = true;

                // ロボの護衛と襲撃先がぶつかった場合、犠牲者なし
                } else if (invalidAttackList.contains(attackTarget)) {
                    victim = null;

                // 襲撃先がモノミ・ロボに守られていなければ成功
                } else {
                    victim = attackTarget;
                }

                // 襲撃無効化リストの初期化
                invalidAttackList = new ArrayList<Player>();

                // 時間帯を進める
                currentTimeZone = TimeZoneEnum.MORNING;
                break;

            case MORNING :

                // 死者にsetDeadする

                currentTimeZone = TimeZoneEnum.DAY;
                break;

            case DAY :

                // 襲撃が成功したら裁判、しなかったらブリーフィング（初日は必ずブリーフィング）
                if (isAttackSuccess) {
                    currentTimeZone = TimeZoneEnum.TRIAL;
                } else {
                    currentTimeZone = TimeZoneEnum.BRIEFING;
                }

            case TRIAL :
                currentTimeZone = TimeZoneEnum.VOTE;
                break;

            case VOTE :
            case BRIEFING :
                currentTimeZone = TimeZoneEnum.NIGHT;
                break;

            case NIGHT:

                // 今晩の襲撃無効化リストを作成

                // 時間帯を進める
                currentTimeZone = TimeZoneEnum.LATE_NIGHT;
                break;

            default:
                break;
        }
    }

    public String getInvalidAttackReason() {

        String reason = StringUtil.EMPTY;

        if (invalidAttackList.contains(attackTarget)) {

            // モノミが守った

            // ロボが守った

            // 黄金銃が守った

            // 幸運組能力

        }

        return reason;
    }


}
