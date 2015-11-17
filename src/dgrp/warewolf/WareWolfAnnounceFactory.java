package dgrp.warewolf;

import java.util.ArrayList;

import dgrp.StringUtil;
import dgrp.warewolf.model.Cast;
import dgrp.warewolf.model.DifficultyEnum;
import dgrp.warewolf.model.Player;
import dgrp.warewolf.model.Character;
import dgrp.warewolf.model.RoleEnum;


public class WareWolfAnnounceFactory {



    /**
     * ゲーム設定完了後、ゲーム開始前に参加者に配布する説明文を作成する
     * （参加者の人数分のリスト）
     * @param list 参加者オブジェクトリスト
     * @param cast 配役
     * @param difficulty 難易度
     * @return ゲーム開始前に参加者に配布する説明文のリスト
     */
    public static ArrayList<String> createStartBeforeAnnounce(ArrayList<Player> list, Cast cast, DifficultyEnum difficulty) {

        ArrayList<String> announceList = new ArrayList<String>();

        // 参加者分作成
        for (Player model : list) {

            String announce = "";
            RoleEnum role = model.getRole();
            Character character = model.getCharacter();

            // プレイヤー名
            announce += model.getPlayerName()+ StringUtil.LINE_CODE;

            // 【絶望サイド】クロ
            announce += StringUtil.createBigParenthesesStr(getSide(role)) + role.roleName + StringUtil.LINE_CODE;

            // あなたはクロです。
            announce += "あなたは" + StringUtil.createParenthesesStr(role.roleName) + "です。" + StringUtil.LINE_CODE;

            // キャラクター名は「西園寺日寄子」です。
            announce += "キャラクター名は" + StringUtil.createParenthesesStr(character.getCharacterName()) + "です。" + StringUtil.LINE_CODE;

            // http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc31.jpg
            announce += character.getCharacterImageUrl() + StringUtil.LINE_CODE;
            announce += StringUtil.LINE_CODE;


            // ●役職「クロ」詳細
            //　毎晩「襲撃」を行い、誰かを殺すことができます。
            // 　GMが個別skypeで襲撃先を尋ねるので、お答えください。
            announce += getRoleAbilityDescription(model, list, cast, difficulty) + StringUtil.LINE_CODE;

            // ●味方陣営詳細
            // 【絶望サイド】のあなたの味方は「裏切り者」です。
            announce += "●味方陣営詳細" + StringUtil.LINE_CODE;
            announce += StringUtil.createBigParenthesesStr(getSide(role)) + "のあなたの味方は" + getSameSide(role, cast, list, character) + StringUtil.LINE_CODE;

            // ●勝敗条件
            // 勝利条件：超高校級の絶望をおしおきしていない状態で、2人の殺人を成功させ、クロがその日の学級裁判までにおしおきされないで逃げ切る。
            // 敗北条件：超高校級の絶望をおしおきする。もしくは、2人の殺人を成功させる前、もしくは成功させた日の学級裁判でクロがおしおきされる。
            announce += "●勝敗条件" + StringUtil.LINE_CODE;
            announce += "勝利条件：" + getWinCondition(role, cast) + StringUtil.LINE_CODE;
            announce += "敗北条件：" + getLoseCondition(role, cast) + StringUtil.LINE_CODE;

            // <えくすとりぃむ時のみ>
            // ●キャラクター「西園寺日寄子」能力詳細
            // あなたは男性キャラクターがキャラカードの能力を使用したとき、それを1度だけ無効にすることができる。
            // ただし、常駐型の能力(苗木、狛枝、不二咲)には使用出来ない。
            // 【能力が使用されたタイミングで宣言することで使用可能。発動前に宣言して封じることはできない。】
            if (DifficultyEnum.EXTREAM.equals(difficulty)) {
                announce += StringUtil.LINE_CODE;
                announce += getCharacterAbilityDescription(character);
            }

            // <のーまる、えくすとりぃむ時のみ>
            // ●アイテム詳細
            // 【希望のカケラ】
            // 使用可能時間帯：朝、襲撃発覚時
            // 持ち主がクロの襲撃を受けたとき、それを無効にすることができる。
            // 【襲撃された朝に宣言することで使用可能。事前宣言による襲撃無効化はできない。】
            //
            // 【ミネラルウォーター】
            // 学級裁判の投票前に誰か1人を選び使用できる。
            // 選ばれたキャラクターは、当日の投票に限り、使用者に投票することができない。
            // 対象に選ばれたキャラクターは決戦投票になった場合も引き続き投票出来ない。
            if (! DifficultyEnum.EASY.equals(difficulty)) {
                announce += StringUtil.LINE_CODE;
                announce += getItemDescription(model);
            }

            // 区切り文字
            announce += "=============================================================================" + StringUtil.LINE_CODE;;

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
            case WHITE :
            case EGO :
            case MONOMI :
            case PATIENT :
                return "希望サイド";

            case BLACK :
            case TRAITOR :
                return "絶望サイド";

            case DESPAIR :
                return "孤立陣営";

            default :
                return StringUtil.EMPTY;
        }
    }


    /**
     * 役職説明を取得する
     * @param role 役職
     * @param cast 配役
     * @return 役職説明
     */
    private static String getRoleAbilityDescription(Player model, ArrayList<Player> list, Cast cast, DifficultyEnum difficulty) {

        String str = "●役職「" + model.getRole().roleName + "」詳細" + StringUtil.LINE_CODE;

        switch(model.getRole()) {

            // シロ
            case WHITE :
                str += "特殊な能力はありません。" + StringUtil.LINE_CODE
                     + "あなたがアルターエゴに解析されると、希望サイド判定となります。"+ StringUtil.LINE_CODE;
                break;

            // アルターエゴ
            case EGO :
                str += "自分が生きている限り、毎晩誰かを解析することができます。" + StringUtil.LINE_CODE
                     + "就寝後、個別skypeでGMに解析希望先をお伝え下さい。" + StringUtil.LINE_CODE
                     + "解析結果は「希望サイド」「絶望サイド」のどちらかになります。"+ StringUtil.LINE_CODE;
                str += StringUtil.LINE_CODE;
                str += getEgoAbilityDescription(cast);
                break;

            // モノミ
            case MONOMI :
                str += "自分が生きている限り、毎晩誰かを護衛することができます。" + StringUtil.LINE_CODE
                     + "就寝後、個別skypeでGMに護衛先をお伝え下さい。" + StringUtil.LINE_CODE
                     + "護衛先がクロの襲撃先と重なった場合、その人を守ることができます。" + StringUtil.LINE_CODE
                     + "ただし、その時は代わりに自分が爆死します。"  + StringUtil.LINE_CODE
                     + StringUtil.LINE_CODE
                     + "誰かを守って爆死した場合、GMから「モノミが爆死しました」とアナウンスがあり、あなたの役職が公開されます。" + StringUtil.LINE_CODE
                     + "クロが直接モノミを襲撃した場合や、おしおきで死亡したときは、あなたの役職は公開されません。" + StringUtil.LINE_CODE
                     + "爆死した場合は、襲撃とは異なるため、いかなる能力・アイテムでも救うことはできません。" + StringUtil.LINE_CODE
                     + StringUtil.LINE_CODE
                     + "あなたがアルターエゴに解析されると、絶望サイド判定となってしまいます。絶望的！" + StringUtil.LINE_CODE;
                break;

            // 絶望病患者
            case PATIENT :
                str += "特殊な能力はありません。" + StringUtil.LINE_CODE
                     + "ですが、あなたは厄介な病にかかっているため、アルターエゴに解析されると絶望サイド判定となってしまいます。" + StringUtil.LINE_CODE;
                break;

            // クロ
            case BLACK :
                str += "毎晩「襲撃」を行い、誰か1人を殺すことができます。" + StringUtil.LINE_CODE
                     + "就寝後、個別skypeでGMに襲撃先をお伝え下さい。" + StringUtil.LINE_CODE;

                // 難易度のーまる時(+アイテム説明)
                if (DifficultyEnum.NORMAL.equals(difficulty)) {
                    str += "アイテムにより、襲撃は無効化される可能性があります。" + StringUtil.LINE_CODE
                         + "アイテム「万力」が使われた日の夜は、襲撃自体を行うことができません。" + StringUtil.LINE_CODE;

                // 難易度えくすとりぃむ時(+能力、アイテム説明)
                } else if (DifficultyEnum.EXTREAM.equals(difficulty)) {
                    str += "能力やアイテムにより、襲撃は無効化される可能性があります。" + StringUtil.LINE_CODE
                         + "アイテム「万力」が使われた日の夜は、襲撃自体を行うことができません。" + StringUtil.LINE_CODE;
                }

                // モノミがいる可能性のある配役時は追記
                if(cast.isPossibilityMonomi()) {
                    str += "もしモノミが守っている場所を襲撃すると、翌朝の死体はモノミになります。" + StringUtil.LINE_CODE;
                }

                str += "あなたがアルターエゴに解析されると、絶望サイド判定となります。" + StringUtil.LINE_CODE;
                break;

            // 裏切り者
            case TRAITOR :

                ArrayList<String> otherTraitorsList = getOtherTraitorsList(list, model.getCharacter().getCharacterName());

                str += "このゲームであなた";
                if (otherTraitorsList != null && otherTraitorsList.size() != 0) {
                    str += "方";
                }
                str += "裏切り者だけが「クロが誰か」を知っています。" + StringUtil.LINE_CODE;
                str += "今回のクロは" +  StringUtil.createParenthesesStr(getCharacterNameByRole(list, RoleEnum.BLACK)) + "さんです。" + StringUtil.LINE_CODE;

                str += "自分以外の裏切り者は";
                if (otherTraitorsList != null && otherTraitorsList.size() != 0) {
                    str += StringUtil.createBigParenthesesStr(StringUtil.implode(otherTraitorsList, "、")) + "です。" + StringUtil.LINE_CODE;
                } else {
                    str += "いません。" + StringUtil.LINE_CODE;
                }

                str += "あなたがアルターエゴに解析されると、絶望サイド判定となります。" + StringUtil.LINE_CODE;
                break;

            // 超高校級の絶望
            case DESPAIR :
                str += "特別な能力はありません。" + StringUtil.LINE_CODE
                    +  "あなたがアルターエゴに解析されると、死人が出ていない状態ならば「希望サイド」、一人でも死人が出ている状態なら「絶望サイド」判定となります。" + StringUtil.LINE_CODE
                    +  "※処理順は「アルターエゴの解析⇒クロの襲撃」のため、翌朝死人が発生しているかどうかは解析結果に影響しません。" + StringUtil.LINE_CODE;
                break;
        }

        return str;
    }


    /**
     * アルターエゴの解析結果例文
     * 配役に含み得る役職のみ出力する
     * @param cast 配役
     * @return アルターエゴの解析結果例文
     */
    private static String getEgoAbilityDescription(Cast cast) {
        String str = "";

        // 希望サイド
        str += "「希望サイド」と解析される役職一覧" + StringUtil.LINE_CODE;
        str += "　・シロ" + StringUtil.LINE_CODE;
        // 超高校級の絶望がいる場合のみ
        if (cast.isPossibilityDespair()) {
            str += "　・死人が一人も出ていない段階での超高校級の絶望" + StringUtil.LINE_CODE;
        }

        str += StringUtil.LINE_CODE;

        // 絶望サイド
        str += "「絶望サイド」と解析される役職一覧" + StringUtil.LINE_CODE;
        str += "　・クロ" + StringUtil.LINE_CODE;
        // モノミがいる場合のみ
        if (cast.isPossibilityMonomi()) {
            str += "　・モノミ" + StringUtil.LINE_CODE;
        }
        // 絶望病患者がいる場合のみ
        if (cast.isPossibilityPatient()) {
            str += "　・絶望病患者" + StringUtil.LINE_CODE;
        }
        // 裏切り者がいる場合のみ
        if (cast.isPossibilityTraitor()) {
            str += "　・裏切り者" + StringUtil.LINE_CODE;
        }
        // 超高校級の絶望がいる場合のみ
        if (cast.isPossibilityDespair()) {
            str += "　・死人が一人以上出た段階での超高校級の絶望" + StringUtil.LINE_CODE;
            str += StringUtil.LINE_CODE;
            str += "※処理順は「アルターエゴの解析⇒クロの襲撃」のため、翌朝死人が発生しているかどうかは解析結果に影響しません。" + StringUtil.LINE_CODE;
        }

        return str;
    }

    /**
     * 参加者オブジェクトの一覧から、指定した役職のキャラクター名を取得して返す
     * @param list 参加者オブジェクトのリスト
     * @param roleEnum キャラクター名を取得したい役職
     * @return 指定した役職のキャラクター名
     */
    private static String getCharacterNameByRole(ArrayList<Player> list, RoleEnum roleEnum) {

        // 指定した役職のキャラクター名だけを取得
        for (Player role : list) {
            if (roleEnum.equals(role.getRole())) {
                return role.getCharacter().getCharacterName();
            }
        }
        return "";
    }

    /**
     * 参加者オブジェクトの一覧から、指定した役職のプレイヤー名を取得して返す
     * @param list 参加者オブジェクトのリスト
     * @param roleEnum プレイヤー名を取得したい役職
     * @return 指定した役職のプレイヤー名
     */
    private static String getPlayerNameByRole(ArrayList<Player> list, RoleEnum roleEnum) {

        // 指定した役職のプレイヤー名だけを取得
        for (Player role : list) {
            if (roleEnum.equals(role.getRole())) {
                return role.getPlayerName();
            }
        }
        return "";
    }

    /**
     * 自分以外の裏切り者のキャラクター名をリストで取得する
     * @param list 参加データ一覧
     * @param myselfName 自分のキャラの名前
     * @return 自分以外の裏切り者のキャラクター名のリスト
     */
    private static ArrayList<String> getOtherTraitorsList(ArrayList<Player> list, String myselfName) {

        ArrayList<String> traitorsList = new ArrayList<String>();

        // 裏切り者のキャラクター名だけを取得
        for (Player role : list) {
            if (RoleEnum.TRAITOR.equals(role.getRole())) {
                traitorsList.add(role.getCharacter().getCharacterName());
            }
        }

        // 自分は抜く
        traitorsList.remove(traitorsList.indexOf(myselfName));

        return traitorsList;
    }


    /**
     * キャラクター能力の詳細説明を取得する
     * @param character キャラクターオブジェクト
     * @return キャラクター能力の詳細説明
     */
    private static String getCharacterAbilityDescription(Character character) {

        String str = "●キャラクター「" + character.getCharacterName() + "」能力詳細" + StringUtil.LINE_CODE;
        str += StringUtil.createParenthesesStr(character.getCharacterAbility().getAbilityName());
        str += "発動可能時間" + StringUtil.COLON;
        str += character.getCharacterAbility().getAbilityTimingEnum().abilityTimingLabel + StringUtil.LINE_CODE;
        str += character.getCharacterAbility().getAbilityDescription() + StringUtil.LINE_CODE;

        return str;
    }


    /**
     * 味方陣営の説明を作って返す
     * @param role 役職
     * @param cast 配役
     * @param list 役職リスト
     * @param character 自分のキャラクター
     * @return 味方陣営の説明
     */
    private static String getSameSide(RoleEnum role, Cast cast, ArrayList<Player> list, Character character) {

        String str = "";

        boolean isExistsSameSide = true;

        switch(role) {
            case WHITE :

                // 他のシロ
                str += StringUtil.createParenthesesStr("他の" + RoleEnum.WHITE.roleName);

                // エゴ
                str += StringUtil.createParenthesesStr(RoleEnum.EGO.roleName);

                // モノミがいるときのみ
                if (cast.isPossibilityMonomi()) {
                    str += StringUtil.createParenthesesStr(RoleEnum.MONOMI.roleName);
                }

                // 絶望病患者がいるときのみ
                if (cast.isPossibilityPatient()) {
                    str += StringUtil.createParenthesesStr(RoleEnum.PATIENT.roleName);
                }
                break;

            case EGO :
                // シロ
                str += StringUtil.createParenthesesStr(RoleEnum.WHITE.roleName);

                // モノミがいるときのみ
                if (cast.isPossibilityMonomi()) {
                    str += StringUtil.createParenthesesStr(RoleEnum.MONOMI.roleName);
                }

                // 絶望病患者がいるときのみ
                if (cast.isPossibilityPatient()) {
                    str += StringUtil.createParenthesesStr(RoleEnum.PATIENT.roleName);
                }
                break;

            case MONOMI :
                // シロ
                str += StringUtil.createParenthesesStr(RoleEnum.WHITE.roleName);

                // エゴ
                str += StringUtil.createParenthesesStr(RoleEnum.EGO.roleName);
                break;

            case PATIENT :
                // シロ
                str += StringUtil.createParenthesesStr(RoleEnum.WHITE.roleName);

                // エゴ
                str += StringUtil.createParenthesesStr(RoleEnum.EGO.roleName);
                break;

            case BLACK :
                // 裏切り者がいるときのみ
                if (cast.isPossibilityTraitor()) {
                    str += StringUtil.createParenthesesStr(RoleEnum.TRAITOR.roleName);

                // 裏切り者がいないときは仲間なし
                } else {
                    isExistsSameSide = false;
                }
                break;

            case TRAITOR :
                // クロ
                str += StringUtil.createParenthesesStr(RoleEnum.BLACK.roleName);

                // 他の裏切り者が存在し得るときのみ
                ArrayList<String> otherTraitors = getOtherTraitorsList(list, character.getCharacterName());
                if ( otherTraitors != null && otherTraitors.size() > 0) {
                    str += StringUtil.createParenthesesStr("他の" + RoleEnum.TRAITOR.roleName);
                }
                break;

            case DESPAIR :
                // 仲間なし
                isExistsSameSide = false;
                break;
        }

        if (isExistsSameSide) {
            str += "です。" + StringUtil.LINE_CODE;
        } else {
            str += "いません。" + StringUtil.LINE_CODE;
        }

        return str;
    }


    /**
     * 勝利条件の説明を作って返す
     * @param role 役職
     * @param cast 配役
     * @return 勝利条件の説明
     */
    private static String getWinCondition(RoleEnum role, Cast cast) {
        String str = "";

        switch(role) {
            case WHITE :
            case EGO :
            case MONOMI :
            case PATIENT :
                // 超高校級の絶望がいたら
                if (cast.isPossibilityDespair()) {
                    str += "超高校級の絶望をおしおきしていない状態で、";
                }
                str += String.valueOf(cast.getTargetVictimCount()) +  "番目の学級裁判終了までにクロをおしおきする。";
                break;

            case BLACK :
            case TRAITOR :
                // 超高校級の絶望がいたら
                if (cast.isPossibilityDespair()) {
                    str += "超高校級の絶望をおしおきしていない状態で、";
                }
                str += String.valueOf(cast.getTargetVictimCount()) +  "人の殺人を成功させ、クロがその日の学級裁判までにおしおきされないで逃げ切る。";
                break;

            case DESPAIR :
                str += String.valueOf(cast.getTargetVictimCount()) +  "番目の学級裁判終了までに自分がおしおきされる。";
                break;
        }

        return str;
    }


    /**
     * 敗北条件の説明を作って返す
     * @param role 役職
     * @param cast 配役
     * @return 敗北条件の説明
     */
    private static String getLoseCondition(RoleEnum role, Cast cast) {
        String str = "";

        switch(role) {
            case WHITE :
            case EGO :
            case MONOMI :
            case PATIENT :
                // 超高校級の絶望がいたら
                if (cast.isPossibilityDespair()) {
                    str += "超高校級の絶望をおしおきする。もしくは、";
                }
                str += String.valueOf(cast.getTargetVictimCount()) +  "人をクロに殺害され、その日の学級裁判でクロをおしおきできない。";
                break;

            case BLACK :
            case TRAITOR :
                // 超高校級の絶望がいたら
                if (cast.isPossibilityDespair()) {
                    str += "超高校級の絶望をおしおきする。もしくは、";
                }
                str += String.valueOf(cast.getTargetVictimCount()) +  "人の殺人を成功させる前、もしくは成功させた日の学級裁判でクロがおしおきされる。";
                break;

            case DESPAIR :
                str += "自分がクロに殺害される。もしくは、自分がおしおきされる前に他陣営が勝利条件を満たす。"+ StringUtil.LINE_CODE;
                str += "（他陣営の勝利条件：クロのおしおき、もしくは" + String.valueOf(cast.getTargetVictimCount()) + "番目の学級裁判の終了）" ;
                break;
        }

        return str;
    }


    /**
     * アイテム説明を作って返す
     * @param model 参加者オブジェクト
     * @return アイテム説明
     */
    private static String getItemDescription(Player model) {
        String str = "";

        // ●アイテム詳細
        str += "●アイテム詳細" + StringUtil.LINE_CODE;

        // 【希望のカケラ】
        str += "アイテム①";
        str += StringUtil.createBigParenthesesStr(model.getItem1().getItemName()) + StringUtil.LINE_CODE;
        str += model.getItem1().getItemImageUrl() + StringUtil.LINE_CODE;

        // 使用可能時間帯：朝、襲撃発覚時
        str += StringUtil.createSmallParenthesesStr("使用可能時間帯：" + model.getItem1().getAbility().getAbilityTimingEnum().abilityTimingLabel) + StringUtil.LINE_CODE;

        // 持ち主がクロの襲撃を受けたとき、それを無効にすることができる。
        // 【襲撃された朝に宣言することで使用可能。事前宣言による襲撃無効化はできない。】
        str += model.getItem1().getAbility().getAbilityDescription() + StringUtil.LINE_CODE;

        str += StringUtil.LINE_CODE;

        // アイテム2でも同じ
        str += "アイテム②";
        str += StringUtil.createBigParenthesesStr(model.getItem2().getItemName()) + StringUtil.LINE_CODE;
        str += model.getItem2().getItemImageUrl() + StringUtil.LINE_CODE;
        str += StringUtil.createSmallParenthesesStr("使用可能時間帯：" + model.getItem2().getAbility().getAbilityTimingEnum().abilityTimingLabel) + StringUtil.LINE_CODE;
        str += model.getItem2().getAbility().getAbilityDescription() + StringUtil.LINE_CODE;

        return str;
    }

    /**
     * アイテム引き直し結果アナウンスを作成する
     * @param model 対象の参加者オブジェクト
     * @return 引き直し結果アナウンス文
     */
    public static String createRedrawItemsAnnounce(Player model) {
        String str = "";

        str += "＞" + model.getCharacter().getCharacterName() + "さん" + StringUtil.LINE_CODE;
        str += "アイテム引き直しの結果、所持していた" + StringUtil.createBigParenthesesStr(model.getBeforeRedrawItem1().getItemName());
        str += "と" + StringUtil.createBigParenthesesStr(model.getBeforeRedrawItem2().getItemName());
        str += "は回収され、あなたのアイテムは以下の2枚になりました。" + StringUtil.LINE_CODE + StringUtil.LINE_CODE;

        // 新アイテム
        str += getItemDescription(model);

        return str;
    }

    /**
     * 特定役職向けのアナウンスの説明文を作成する
     * @param list 参加者オブジェクトのリスト
     * @return 特定役職向けのアナウンスの説明文
     */
    public static String createRoleAnnounceInfo(ArrayList<Player> list, RoleEnum role) {

        // 例：クロである【日向創】(ぶつ)さんに以下のアナウンスをしてください。
        String str = StringUtil.COLON + StringUtil.createBigParenthesesStr(role.roleName) + "である" + getCharacterNameByRole(list, role);
        str += StringUtil.createSmallParenthesesStr(getPlayerNameByRole(list, role)) + "さんに以下のアナウンスをしてください。";

        return str;
    }

    /**
     * 夜の襲撃アナウンスを作成する
     * @param list 参加者オブジェクトのリスト
     * @param day 現在の日にち
     * @return 夜の襲撃アナウンス
     */
    public static String createAttackAnnounce(ArrayList<Player> list, int day) {

        String str = "襲撃先を選び、GMに伝えてください。（自分を襲撃することも可能です。）" + StringUtil.LINE_CODE;

        // 初日はカード1枚だけ
        if (day == 0) {
            str += "初日は襲撃で殺害することはできませんが、対象キャラクターのカード一枚を捨てさせることができます。" + StringUtil.LINE_CODE;

        // TODO
        } else {
            str += "本日、襲撃が失敗することが判明しているキャラクターは" + StringUtil.LINE_CODE;
        }

        return str;
    }

}
