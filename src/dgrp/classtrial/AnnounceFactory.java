package dgrp.classtrial;

import java.util.ArrayList;

import dgrp.StringUtil;
import dgrp.classtrial.model.Cast;
import dgrp.classtrial.model.Player;
import dgrp.classtrial.model.Character;
import dgrp.classtrial.model.RoleEnum;


/**
 * 参加者へ告知するアナウンスを作成する
 * @author butsuryo
 *
 */
public class AnnounceFactory {



    /**
     * ゲーム設定完了後、ゲーム開始前に参加者に配布する説明文を作成する
     * （参加者の人数分のリスト）
     * @param list 参加者オブジェクトリスト
     * @param cast 配役
     * @return ゲーム開始前に参加者に配布する説明文のリスト
     */
    public static ArrayList<String> createStartBeforeAnnounce(ArrayList<Player> list, Cast cast) {

        ArrayList<String> announceList = new ArrayList<String>();

        // 参加者分作成
        for (Player model : list) {

            String announce = "";
            RoleEnum role = model.getCurrentRole();
            Character character = model.getCharacter();

            announce += "--------------------------------------------------------------------------------" + StringUtil.LINE_CODE;

            // プレイヤーネーム
            announce += model.getPlayerName() + StringUtil.LINE_CODE;

            // 【シロ陣営】探偵
            announce += StringUtil.createBigParenthesesStr(getSide(role)) + role.roleName + StringUtil.LINE_CODE;

            // あなたは探偵です。
            announce += "あなたは" + StringUtil.createParenthesesStr(role.roleName) + "です。" + StringUtil.LINE_CODE;

            // キャラクター名は「西園寺日寄子」です。
            announce += "キャラクター名は" + StringUtil.createParenthesesStr(character.getCharacterName()) + "です。" + StringUtil.LINE_CODE;
            announce += StringUtil.LINE_CODE;


            // ●役職「探偵」詳細
            //　"ゲーム開始前に一度だけ誰か一人を調査することができます。人狼でいう占い師です。～～
            announce += getRoleAbilityDescription(model, list, cast) + StringUtil.LINE_CODE;

            // ●勝敗条件
            // 勝利条件：投票の結果、最多得票者が【クロ】である。(最多得票【絶望】は敗北。)
            announce += "●勝利条件" + StringUtil.LINE_CODE;
            announce +=  getWinCondition(role, cast) + StringUtil.LINE_CODE;


            announceList.add(announce);
        }

        return announceList;
    }


    /**
     * 所属陣営名を取得する
     * @param role 役職
     * @return 所属陣営名
     */
    private static String getSide(RoleEnum role) {
        switch(role) {
            case HOPE :
            case DETECTIVE :
            case GAMBLER :
                return "シロ陣営";

            case BLACK :
            case DESPAIR :
                return "クロ陣営";

            default :
                return StringUtil.EMPTY;
        }
    }


    /**
     * 役職説明を取得する
     * @param model 当該者の参加者オブジェクト
     * @param list 参加者オブジェクトリスト
     * @param cast 配役
     * @return 役職説明
     */
    private static String getRoleAbilityDescription(Player model, ArrayList<Player> list, Cast cast) {

        String str = "●役職「" + model.getCurrentRole().roleName + "」詳細" + StringUtil.LINE_CODE;

        switch(model.getCurrentRole()) {

            // 希望
            case HOPE :
                str += "何の能力も持ちません。人狼でいう素村です。" + StringUtil.LINE_CODE
                     + "あなたが【ギャンブラー】に役職交換された場合は、役職：ギャンブラーとなりますが、特殊能力は何も使えません。"+ StringUtil.LINE_CODE;
                break;

            // 探偵
            case DETECTIVE :
                str += "ゲーム開始前に一度だけ誰か一人を調査することができます。人狼でいう占い師です。" + StringUtil.LINE_CODE
                     + "調査結果は「クロ」「クロではない」のどちらかになります。（クロ陣営の【絶望】も「クロではない」判定です。）" + StringUtil.LINE_CODE
                     + "なお、【ギャンブラー】が役職交換を行っていた場合でも、調査結果はギャンブラーが交換する前の役職になります。" + StringUtil.LINE_CODE
                     + "欠席者を調査することも出来ます。欠席者が2人いる場合、2人両方の調査結果が得られます。" + StringUtil.LINE_CODE
                     + "個別skypeでGMに調査対象をお伝え下さい。" + StringUtil.LINE_CODE
                     + "あなたが【ギャンブラー】に役職交換された場合は、役職：ギャンブラーとなりますが、能力の処理順序は探偵⇒ギャンブラーのため、調査権限を奪われることはありません。"+ StringUtil.LINE_CODE;
                str += StringUtil.LINE_CODE;
                break;

            // ギャンブラー
            case GAMBLER :
                str += "ゲーム開始前に一度だけ誰か一人と役職を交換することができます。" + StringUtil.LINE_CODE
                     + "交換した結果、自分が何の役職に変わったかを知ることができます。" + StringUtil.LINE_CODE
                     + "交換された相手は交換された事実に気付かず、自分がギャンブラーにされたことも気付きません。" + StringUtil.LINE_CODE
                     + "あえて誰とも交換をしないこともできます。"  + StringUtil.LINE_CODE
                     + StringUtil.LINE_CODE
                     + "クロ・絶望と交換した場合は、クロ陣営となり、勝利条件も変わります。" + StringUtil.LINE_CODE
                     + "クロと交換した場合、仲間の名前を確認することはできません。（クロの仲間からも、あなたが仲間になったことがわかりません）" + StringUtil.LINE_CODE
                     + "交換をキャンセルしたり、交換し直したりすることはできません。" + StringUtil.LINE_CODE
                     + "探偵があなたや交換相手を調査した場合、調査結果は交換する前の[本来の役職]になります。" + StringUtil.LINE_CODE;
                break;

            // クロ
            case BLACK :
                ArrayList<String> otherBlackList = getOtherBlackCharacterList(list, model.getCharacter().getCharacterName());

                str += "何の能力も持ちません。（人狼ゲームでいう人狼役ですが、噛みがなく一日で終わるため）" + StringUtil.LINE_CODE
                     + "他のクロは";
                if (otherBlackList != null && otherBlackList.size() != 0) {
                    str += StringUtil.createBigParenthesesStr(StringUtil.implode(otherBlackList, "、")) + "です。" + StringUtil.LINE_CODE;
                }
                str += "仲間同士での相談はできません。" + StringUtil.LINE_CODE
                     + "【絶望】の名前は知ることができません。" + StringUtil.LINE_CODE
                     + "あなたが【ギャンブラー】に役職交換された場合は、役職：ギャンブラー(シロ陣営)となり、勝利条件が変わります。" + StringUtil.LINE_CODE
                     + "（交換された事実を知ることができないため、知らないうちにシロ陣営になっています）" + StringUtil.LINE_CODE;
                break;

            // 絶望
            case DESPAIR :

                str += "何の能力も持ちません。人狼でいう狂人です。" + StringUtil.LINE_CODE
                     + "あなたはクロ陣営でありながら【クロ】ではないため、自分が最多得票者になっても負けることはありません。" + StringUtil.LINE_CODE
                     + "【クロ】の名前は知ることができません。クロと相談することもできません。" + StringUtil.LINE_CODE
                     + "あなたが【ギャンブラー】に役職交換された場合は、役職：ギャンブラー(シロ陣営)となり、勝利条件が変わります。" + StringUtil.LINE_CODE
                     + "（交換された事実を知ることができないため、知らないうちにシロ陣営になっています）" + StringUtil.LINE_CODE;
                break;
        }

        return str;
    }

    // TODO 欠席者
    /**
     * 自分以外のクロのキャラクターの名前をリストで取得する
     * @param list 参加者オブジェクトリスト
     * @param myselfName 自分のキャラの名前
     * @return 自分以外のクロのキャラクターの名前のリスト
     */
    private static ArrayList<String> getOtherBlackCharacterList(ArrayList<Player> list, String myselfName) {

        ArrayList<String> blackList = new ArrayList<String>();

        // クロのキャラクター名だけを取得
        for (Player player : list) {
            if (RoleEnum.BLACK.equals(player.getCurrentRole()) && !player.getCharacter().getCharacterName().equals(myselfName)) {
                // 欠席者の場合はキャラクター名の後に(欠席者)を追記する
                if (player.isAbsent()) {
                    blackList.add(player.getCharacter().getCharacterName() + "(欠席者)");
                } else {
                    blackList.add(player.getCharacter().getCharacterName());
                }
            }
        }

        return blackList;
    }

    /**
     * 勝利条件の説明を作って返す
     * @param role 役職
     * @param cast 配役
     * @return 勝利条件の説明
     */
    private static String getWinCondition(RoleEnum role, Cast cast) {

        switch(role) {
            case HOPE :
            case DETECTIVE :
            case GAMBLER :
                return "投票の結果、最多得票者が【クロ】である。(最多得票【絶望】は敗北。)";

            case BLACK :
            case DESPAIR :
                return "投票の結果、最多得票者が【クロ】以外である。(最多得票が【絶望】でも勝利。)";

            default :
                return StringUtil.EMPTY;
        }
    }

    /**
     * ギャンブラーの交換結果の説明を作って返す
     * @param tradeTargetModel 交換相手のオブジェクト
     * @return ギャンブラーの交換結果
     */
    public static String getInvestigationResult(Player targetPlayer) {

        String characterName = targetPlayer.getCharacter().getCharacterName();
        String result = characterName + "さんは";

        if (RoleEnum.BLACK.equals(targetPlayer.getCurrentRole())) {
            result += "【クロ】でした。";
        } else {
            result += "【クロではありません】でした。";
        }

        return result;
    }


    /**
     * ギャンブラーの交換結果の説明を作って返す
     * @param targetPlayer 交換相手のオブジェクト
     * @return ギャンブラーの交換結果
     */
    public static String getTradeResult(Player targetPlayer) {

        String targetCharacterName = targetPlayer.getCharacter().getCharacterName();
        RoleEnum tradeTargetRole = targetPlayer.getCurrentRole();

        // 交換結果
        String result = "交換の結果、あなたの役職は";

        result += StringUtil.createBigParenthesesStr(tradeTargetRole.roleName) + "に、" + StringUtil.LINE_CODE;
        result += targetCharacterName + "さんの役職は【ギャンブラー(シロ陣営)】に変更されました。" + StringUtil.LINE_CODE;

        if (RoleEnum.BLACK.equals(tradeTargetRole) || RoleEnum.DESPAIR.equals(tradeTargetRole)) {
            result += StringUtil.LINE_CODE + "【クロ陣営】勝利条件：\r\n投票の結果、最多得票者が【クロ】以外である。(最多得票が【絶望】でも勝利。)" + StringUtil.LINE_CODE;

            if (RoleEnum.BLACK.equals(tradeTargetRole)) {
                result += "※役職交換でクロになったギャンブラーは仲間の名前を知ることができません。";
            }

        } else if (RoleEnum.DETECTIVE.equals(tradeTargetRole)) {
            result += "※役職交換で探偵になったギャンブラーは、調査(役職実行)を行うことができません。";
        }

        return result;
    }

    /**
     * 配役決定アナウンスを作成して返す
     * @param cast 配役オブジェクト
     * @return 配役決定アナウンス
     */
    public static String getGameSettingCompleteAnnounce(Cast cast) {

        String announce = "配役が決定しました。" + StringUtil.LINE_CODE;
        announce += "●配役：" + cast.getCastName() + StringUtil.LINE_CODE;
        announce += "●参加者：" + StringUtil.LINE_CODE;
        for (Player player : cast.getPlayerList()) {
            if (!player.isAbsent()) {
                announce += StringUtil.FULL_WIDTH_SPACE + player.getCharacter().getCharacterName() + StringUtil.LINE_CODE;
            }
        }

        for (Player player : cast.getPlayerList()) {
            if (player.isAbsent()) {
                announce += StringUtil.FULL_WIDTH_SPACE + player.getCharacter().getCharacterName() + "(欠席)" + StringUtil.LINE_CODE;
            }
        }
        announce += StringUtil.LINE_CODE + "ただいまよりキャラクター・役職の告知、および役職実行を行います。\r\n個別skypeでの連絡をお待ちください。";

        return announce;
    }

    /**
     * 投票結果の勝敗アナウンスを作成して返す
     * @param cast 配役オブジェクト
     * @param votedList 投票されたモデルのリスト
     * @return 投票結果の勝敗アナウンス
     */
    public static String getVoteResult(Cast cast, ArrayList<Player> votedList) {

        int count = votedList.size();

        // シロ勝利かどうか
        boolean isWhiteWin = true;

        String result = "投票の結果、";

        switch (count) {
            // 全選択解除された場合は結果を初期化
            case 0 :
                result = StringUtil.EMPTY;
                break;

            // 選択された最多得票者が1人だけの場合は勝利条件が単純
            case 1 :
                for (Player player : votedList) {
                    result += player.getCharacter().getCharacterName() + "様が最多得票者となりました。" + StringUtil.LINE_CODE;
                    result += StringUtil.LINE_CODE + "それでは結果の発表を行います。" + StringUtil.LINE_CODE;
                    result += player.getCharacter().getCharacterName() + "様の役職は";
                    result += StringUtil.createBigParenthesesStr(player.getCurrentRole().roleName) + "でした。" + StringUtil.LINE_CODE + StringUtil.LINE_CODE;

                    // クロでなければシロ陣営勝利
                    if (!RoleEnum.BLACK.equals(player.getCurrentRole())) {
                        isWhiteWin = false;
                    }
                }
                break;

            // 2人以上の場合はやや複雑
            default :
                result += getVotePlayerStr(votedList) + "の" + count + "人が同票で最多得票者となりました。" + StringUtil.LINE_CODE;
                result += StringUtil.LINE_CODE + "それでは結果の発表を行います。" + StringUtil.LINE_CODE;

                boolean hasBlack = false;

                for (Player player : votedList) {
                    result += player.getCharacter().getCharacterName() + "様の役職は";
                    result += StringUtil.createBigParenthesesStr(player.getCurrentRole().roleName) + StringUtil.LINE_CODE;
                    if (RoleEnum.BLACK.equals(player.getCurrentRole())) {
                        hasBlack = true;
                    }
                }
                result += "でした。" + StringUtil.LINE_CODE + StringUtil.LINE_CODE;

                // 全員選択＆配役にクロがゼロならシロ陣営勝利
                if (Integer.parseInt(cast.getPlayerNum()) == votedList.size() && cast.isNonBlack()) {
                    result += "参加者の中に【クロ】の方がいない配役で、全員一票ずつの分配投票になりました。" + StringUtil.LINE_CODE;

                // 一人でもクロがいればシロ陣営勝利
                } else if (hasBlack) {
                    result += "最多得票者の中に【クロ】の方がいらっしゃいました。" + StringUtil.LINE_CODE;

                // 一人一票ずつでもなく、最多得票者の中にクロがいなければクロ陣営勝利
                } else {
                    result += "最多得票者の中に【クロ】の方は一人もいらっしゃいませんでした。" + StringUtil.LINE_CODE;
                    isWhiteWin = false;
                }
        }

        // 1人以上選ばれている場合のみ
        if (count > 0) {
            if (isWhiteWin) {
                result += "おめでとうございます、【シロ陣営】の皆様の勝利です。" + StringUtil.LINE_CODE;
            } else {
                result += "おめでとうございます、【クロ陣営】の皆様の勝利です。" + StringUtil.LINE_CODE;
            }
            result += StringUtil.LINE_CODE + "なお、内訳は以下の通りでした。" + StringUtil.LINE_CODE;
            result += getAllCast(cast, isWhiteWin);
        }
        return result;
    }

    /**
     * 現在選択されている最多得票者のキャラクター名(様付き)をナカグロ区切りの文字列で取得する
     * @return 現在選択されている最多得票者のキャラクター名（複数の場合はナカグロ区切りの文字列）
     */
    private static String getVotePlayerStr(ArrayList<Player> votedList) {
        ArrayList<String> list = new ArrayList<String>();
        for (Player model : votedList) {
            list.add(model.getCharacter().getCharacterName() + "様");
        }
        return StringUtil.implode(list, StringUtil.MIDDLE_DOT);
    }

    /**
     * 全配役結果を文字列にして返却する
     * @return 全配役結果の文字列
     */
    private static String getAllCast(Cast cast, boolean isWhiteWin) {
        String str = StringUtil.EMPTY;

        // 先に参加者分をまとめて羅列
        for (Player player : cast.getPlayerList()) {

            if (!player.isAbsent()) {

                // 名前の先頭に【勝利】か【敗北】を表記
                boolean isWin = false;
                if (isWhiteWin) {
                    // シロ陣営勝利のときにシロ陣営
                    if (player.getCurrentRole().isWhite()) {
                        isWin = true;
                    }
                } else {
                    // クロ陣営勝利のときにクロ陣営
                    if (!player.getCurrentRole().isWhite()) {
                        isWin = true;
                    }
                }
                str += (isWin) ? "【勝利】" : "【敗北】";

                // キャラ名(カッコ内にプレイヤー名)
                str += player.getCharacter().getCharacterName() + StringUtil.createSmallParenthesesStr(player.getPlayerName());

                // 役職名
                str += StringUtil.FULL_WIDTH_SPACE + player.getCurrentRole().roleName;

                // 交換済みの場合はその旨を追記
                if (player.getAfterTradeRole() != null) {
                    str += "【交換済み(元:" + player.getDefaultRole().roleName + ")】";
                }
                str += StringUtil.LINE_CODE;
            }
        }

        // 末尾に欠席者分を追記
        for (Player player : cast.getPlayerList()) {

            if (player.isAbsent()) {
                str += player.getCharacter().getCharacterName() + StringUtil.createSmallParenthesesStr(player.getPlayerName());
                str += StringUtil.FULL_WIDTH_SPACE + player.getCurrentRole().roleName;
                str += StringUtil.LINE_CODE;
            }
        }

        return str;
    }
}
