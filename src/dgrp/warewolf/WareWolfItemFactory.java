package dgrp.warewolf;

import java.util.Collections;
import java.util.LinkedList;

import dgrp.warewolf.model.Ability;
import dgrp.warewolf.model.AbilityTimingEnum;
import dgrp.warewolf.model.Item;

/**
 * アイテム山札の作成・管理
 * @author butsuryo
 *
 */
public class WareWolfItemFactory {

    // 山札
    private static LinkedList<Item> stackItems;

    // 返却・破棄・使用済みの札
    private static LinkedList<Item> spentStackItems = new LinkedList<Item>();

    public static int getStackCount() {
        return stackItems.size();
    }
    /**
     * 山札の一番上から1枚引いて返す
     * @return アイテム1枚
     */
    public static Item getOneItem() {

        // もし残り1枚なら、使用済みアイテムをシャッフルして新たな山札とする
        if (stackItems.size() == 1) {
            Item getItem = stackItems.removeFirst();

            // 使用済みをシャッフル
            Collections.shuffle(spentStackItems);

            // 山札にセット
            stackItems = spentStackItems;

            // 使用済みを初期化
            spentStackItems = new LinkedList<Item>();

            return getItem;

        } else {
            System.out.println("残り山札：" + String.valueOf(stackItems.size()-1) + "枚");
            return stackItems.removeFirst();
        }
    }

    /**
     * 使用済み山札の一番下に1枚追加する
     * @param アイテム1枚
     */

    public static void spendOneItem(Item item) {
        spentStackItems.addLast(item);
    }


    /**
     * アイテムの山札を作成する
     * @param isRemake 強制作り直しフラグ
     */
    public static void createStackItems(boolean isRemake) {

        // 強制作り直しフラグが立っておらず、山札が既に作成されている場合は処理しない
        if (!isRemake && stackItems != null) {
            return;
        }

        LinkedList<Item> itemList = new LinkedList<Item>();

        // 全部のアイテムを作成
        // アルターボール2枚
        itemList = setItem("アルターボール", new Ability("アルターボール", "夜時間に誰か1人を選び、アイテムカードを場に1枚捨てさせる。\r\n捨てるアイテムカードはその持ち主が選べる。捨てるアイテムカードを公開してはならない。\r\n(使用と同時に対象を宣言し、就寝時間を待たずにその場で捨てさせる。)",
                            AbilityTimingEnum.NIGHT), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic01.jpg", 2,  itemList);

        // 動くこけし2枚
        itemList = setItem("動くこけし", new Ability("動くこけし", "昼時間にこれまで死亡したキャラクターから1人を選び、そのアイテムカードを1枚手持ちに加えることができる。\r\n(対象キャラクターが2枚所持している時は、伏せたままランダムで1枚取得する。)",
                AbilityTimingEnum.DAY), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic02.jpg", 2,  itemList);

        // 黄金銃2枚
        itemList = setItem("黄金銃", new Ability("黄金銃", "夜時間に使用できる。その夜、自身へのクロの襲撃を無効にすることができる。\r\n(クロが襲撃の対象に選ぶことは可能。)",
                AbilityTimingEnum.NIGHT), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic03.jpg", 2,  itemList);

        // おでこのメガネ2枚
        itemList = setItem("おでこのメガネ", new Ability("おでこのメガネ", "アルターエゴが死亡しているときのみ使用できる。夜時間に誰か1人を選び、希望サイドか絶望サイドかを判別することができる。\r\n(就寝前に使用のみ宣言し、就寝後に判別先を指定する。判別結果はGMがあなたのみに知らせる。)",
                AbilityTimingEnum.NIGHT), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic04.jpg", 2,  itemList);

        // 希望のカケラ2枚
        itemList = setItem("希望のカケラ", new Ability("希望のカケラ", "持ち主がクロの襲撃を受けたとき、それを無効にすることができる。\r\n(襲撃された朝に宣言することで使用可能。事前宣言による襲撃無効化はできない。)",
                AbilityTimingEnum.MORNING), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic05.jpg", 2,  itemList);

        // キルリアンカメラ2枚
        itemList = setItem("キルリアンカメラ", new Ability("キルリアンカメラ", "昼時間にこれまで死亡したキャラクターから1人を選び、そのロールカードを見ることができる。\r\n(結果はあなたにのみ知らされ、他のキャラクターには開示されない。得た情報を伝えるのは自由。)",
                AbilityTimingEnum.DAY), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic06.jpg", 2,  itemList);

        // 校章バッジ4枚
        itemList = setItem("校章バッジ", new Ability("校章バッジ", "持ち主がクロの襲撃を受けて死亡したとき使用できる。\r\n死亡当日の学級裁判に限り、誰か1人に投票することができる。\r\n会話、および学級裁判には参加してはいけない。また能力やアイテムの使用もできない。",
                AbilityTimingEnum.MORNING_DEAD), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic07.jpg", 4,  itemList);

        // G-SICK2枚
        itemList = setItem("G-SICK", new Ability("G-SICK", "学級裁判のとき、ノンストップ議論を3分延長することができる。\r\nノンストップ議論の最中に使用を宣言することで使用可能。",
                AbilityTimingEnum.TRIAL), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic08.jpg", 2, itemList);

        // 塩4枚
        itemList = setItem("塩", new Ability("塩", "持ち主がクロの襲撃を受けて死亡したとき使用できる。(おしおきによる死亡時は使用することができない。)\r\n以後、会話のみ参加することができる。投票への参加、アイテム・能力の使用はできない。",
                AbilityTimingEnum.MORNING_DEAD), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic09.jpg", 4, itemList);

        // ジャスティスロボ6枚
        itemList = setItem("ジャスティスロボ", new Ability("ジャスティスロボ", "夜時間に使用者を除く誰か1人を選び、ジャスティスロボに護衛させる。\r\n護衛されたキャラクターは、その夜クロの襲撃を無効にすることができる。\r\n(使用と同時に護衛対象を宣言する。クロが襲撃先に選ぶことは可能。)",
                AbilityTimingEnum.NIGHT), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic10.jpg", 6, itemList);

        // 水晶のドクロ4枚
        itemList = setItem("水晶のドクロ", new Ability("水晶のドクロ", "持ち主がおしおきされて死亡したとき使用できる。(クロの襲撃による死亡時は使用することができない。)\r\n以後、会話のみ参加することができる。投票への参加、アイテム・能力の使用はできない。",
                AbilityTimingEnum.VOTE_DEAD), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic11.jpg", 4, itemList);

        // 絶望バット2枚
        itemList = setItem("絶望バット", new Ability("絶望バット", "学級裁判のとき、ノンストップ議論を強制終了することができる。\r\nノンストップ議論の最中に使用を宣言することで使用可能。",
                AbilityTimingEnum.TRIAL), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic12.jpg", 2,  itemList);

        // 誰かの卒業アルバム2枚
        itemList = setItem("誰かの卒業アルバム", new Ability("誰かの卒業アルバム", "夜時間に誰か1人を選び、裏切者かそれ以外かを判別することができる。\r\n(就寝前に使用のみ宣言し、就寝後に判別先を指定する。判別結果はGMがあなたのみに知らせる。)",
                AbilityTimingEnum.NIGHT), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic13.jpg", 2,  itemList);

        // 超技林2枚
        itemList = setItem("超技林", new Ability("超技林", "学級裁判のとき、2票分の投票権を得ることができる。投票前に宣言することで使用可能。\r\n1人に2票分投票可、2人に1票ずつは不可。",
                AbilityTimingEnum.VOTE_BEFORE), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic14.jpg", 2,  itemList);

        // 武神のお守り2枚
        itemList = setItem("武神のお守り", new Ability("武神のお守り", "持ち主がクロの襲撃を受けたとき、その襲撃先を使用者の右どなりに受け流すことができる。\r\n(襲撃された朝に宣言することで使用可能。事前宣言による襲撃無効化はできない。\r\n襲撃対象変更後は再度朝時間を開始し、変更先のキャラクターが襲撃を無効化する手段を持っている場合は使用できる。\r\nこのアイテムを重ねがけすることもできる。\r\n右どなりが死亡している場合はその右に流れる。\r\n襲撃先がクロ自身になった場合、クロが無効化手段を持っていなければ希望サイドの勝利となる。)",
                AbilityTimingEnum.MORNING), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic15.jpg", 2,  itemList);

        // ボージョボー人形2枚
        itemList = setItem("ボージョボー人形", new Ability("ボージョボー人形", "使用者が男女2人を選び、選ばれたキャラクターは、山札からアイテムカードを1枚ずつ引くことができる。\r\n自分を対象に含むことも可能。\r\n指定した相手がカードを2枚持っていた場合、持てるアイテムは2枚までのため、相手は新たなカードを引いた後、1枚を選択して捨てる。",
                AbilityTimingEnum.DAY), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic16.jpg", 2,  itemList);

        // 万力2枚
        itemList = setItem("万力", new Ability("万力", "持ち主がクロの襲撃を受けて死亡したとき使用できる。次の夜時間、クロは誰も襲撃することができない。\r\n(襲撃無効化ではなく、夜時間のクロの襲撃そのものをスキップさせる。)",
                AbilityTimingEnum.MORNING_DEAD), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic17.jpg", 2,  itemList);

        // ミネラルウォーター3枚
        itemList = setItem("ミネラルウォーター", new Ability("ミネラルウォーター", "学級裁判の投票前に誰か1人を選び使用できる。選ばれたキャラクターは、当日の投票に限り、使用者に投票することができない。\r\n対象に選ばれたキャラクターは決戦投票になった場合も引き続き投票出来ない。",
                AbilityTimingEnum.VOTE_BEFORE), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic18.jpg", 3,  itemList);

        // 無言電話2枚
        itemList = setItem("無言電話", new Ability("無言電話", "夜時間に誰か1人を選び、そのキャラクターが希望サイドの「シロ」かそれ以外かを判別することができる。\r\n(就寝前に使用のみ宣言し、就寝後に判別先を指定する。判別結果はGMがあなたのみに知らせる。)",
                AbilityTimingEnum.NIGHT), "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/ic19.jpg", 2,  itemList);

        // ランダムシャッフル
        Collections.shuffle(itemList);

        // 山札にセット
        stackItems = itemList;
    }

    /**
     * アイテムを指定個数分作成して山札にセットする。
     *
     * @param itemName アイテム名
     * @param ability  アイテム効果
     * @param url      アイテムカードURL
     * @param NumCount 枚数
     * @param itemList 山札のリスト
     * @return アイテムを追加した山札のリスト
     */
    private static LinkedList<Item> setItem(String itemName, Ability ability, String url, int NumCount,  LinkedList<Item> itemList) {

        for (int i=0; i<NumCount; i++) {
            itemList.add(new Item(itemName, ability, url));
        }
        return itemList;
    }
}
