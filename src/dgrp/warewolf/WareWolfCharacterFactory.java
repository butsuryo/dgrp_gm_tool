package dgrp.warewolf;

import java.util.ArrayList;
import java.util.Collections;

import dgrp.warewolf.model.Ability;
import dgrp.warewolf.model.AbilityTimingEnum;
import dgrp.warewolf.model.Character;

public class WareWolfCharacterFactory {

    private static ArrayList<Character> characterList1;
    private static ArrayList<Character> characterList2;



    /**
     * 参加人数、バージョンを指定し、人数分のキャラクターをリストで取得する
     *
     * @param playerNum 参加人数
     * @param versionFlg 特定のバージョンに限定するためのフラグ(1:無印のみ 2:スーダンのみ 0(もしくはそれ以外):限定なし)
     * @return 配役オブジェクトのリスト
     */
    public static ArrayList<Character> createCharacterList(int playerNum, int versionFlg) {

        ArrayList<Character> characterList = null;
        ArrayList<Character> allCharacterList1 = null;
        ArrayList<Character> allCharacterList2 = null;

        // 1のキャラだけ、2のキャラだけ、という選択が出来るようにしておく
        switch (versionFlg) {

            // 1のキャラだけ
            case 1 :
                // 1の全キャラ作成
                if (characterList1 != null) {
                    allCharacterList1 = characterList1;
                }else{
                    allCharacterList1 = create1CharacterList();
                }
                // 参加人数分抽選して取得
                characterList = createPlayerCharacterList(allCharacterList1, playerNum);
                break;

            // 2のキャラだけ
            case 2 :
                if (characterList2 != null) {
                    allCharacterList2 = characterList2;
                }else{
                    allCharacterList2 = create2CharacterList();
                }
                // 参加人数分抽選して取得
                characterList = createPlayerCharacterList(allCharacterList2, playerNum);
                break;

            // 指定なし(全バージョンから)
            default :

                // 1キャラ
                if (characterList1 != null) {
                    allCharacterList1 = characterList1;
                }else{
                    allCharacterList1 = create1CharacterList();
                }

                // 2キャラ
                if (characterList2 != null) {
                    allCharacterList2 = characterList2;
                }else{
                    allCharacterList2 = create2CharacterList();
                }

                // 配列マージ
                characterList = mergeCharacterList(allCharacterList1, allCharacterList2);

                // 参加人数分抽出して取得
                characterList = createPlayerCharacterList(characterList, playerNum);

                break;
        }

        return characterList;
    }

    /**
     * 1キャラと2キャラのリストをマージする。ソートはしない。
     * @param list1 1キャラリスト
     * @param list2 2キャラリスト
     * @return 1キャラと2キャラをマージしたリスト
     */
    private static ArrayList<Character> mergeCharacterList(ArrayList<Character> list1, ArrayList<Character> list2) {
        ArrayList<Character> mergeList = new ArrayList<Character>();

        for (Character role1 : list1) {
            mergeList.add(role1);
        }

        for (Character role2 : list2) {
            mergeList.add(role2);
        }

        return mergeList;
    }


    /**
     * 作成した全キャラリストの中から、参加人数分を抽選して取得する
     * リストをランダムソートした後、人数分だけ上から取得する
     * @param allCharacterList 全キャラリスト
     * @param playerNum 参加人数
     * @return 人数分だけ抽選取得したキャラリスト
     */
    private static ArrayList<Character> createPlayerCharacterList(ArrayList<Character> allCharacterList, int playerNum) {

        // ランダムソート
        Collections.shuffle(allCharacterList);

        // 人数分だけ上から取得
        ArrayList<Character> characterList = new ArrayList<Character>();
        for (int i=0; i<playerNum; i++) {
            characterList.add(allCharacterList.get(i));
        }

        return characterList;
    }


    /**
     * 無印キャラのみの全キャラリストを作成して返す
     *
     * @return ArrayList<RoleCharacter> 無印キャラのみの全キャラリスト
     */
    private static ArrayList<Character> create1CharacterList() {

        // 全キャラのリストを作成
        ArrayList<Character> allCharacterList = new ArrayList<Character>();

        // 苗木
        Ability abilityNaegi = new Ability("超高校級の幸運", "・あなたは3日目の朝時間まで、自身へのクロの襲撃を無効にすることができる。\r\n・襲撃の対象に指定することは可能。3日の朝までなら何度でも無効にできる。",
                AbilityTimingEnum.MORNING);
        Character naegi = new Character("苗木誠", abilityNaegi, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc02.jpg");

        // 舞園
        Ability abilityMaizono = new Ability("超高校級のアイドル", "・あなたは1度だけ学級裁判の投票前に『桑田怜恩』の投票権を自分のものにすることが出来る。\r\n・桑田の投票は無効になり、あなたは2票分の投票権を得る。\r\n・1人に2票分投票可、2人に1票ずつは不可。\r\n・桑田がゲームに参加していない時、既に死亡している時は使用不可。",
                AbilityTimingEnum.VOTE_BEFORE);
        Character maizono = new Character("舞園さやか", abilityMaizono, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc03.jpg");

        // 桑田
        Ability abilityKuwata = new Ability("超高校級の野球選手", "・あなたはクロの襲撃を受けたとき、1度だけ手持ちのアイテムカードを場に1枚捨て、それを無効にすることができる。\r\n・手持ちのアイテムカードがない場合使用不可。\r\n・アイテムが2枚ある場合、捨てるアイテムは選択して良い。\r\n・襲撃された朝に宣言することで使用可能。事前宣言による襲撃無効化はできない。",
                AbilityTimingEnum.MORNING);
        Character kuwata = new Character("桑田怜恩", abilityKuwata, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc04.jpg");

        // 霧切
        Ability abilityKirigiri = new Ability("超高校級の？？？", "・あなたはこの能力を4日目以降に1度だけ使用できる。\r\n・夜時間に誰か1人を選び、希望サイドか絶望サイドかを判別することができる。\r\n・就寝前に使用のみ宣言し、就寝後に判別先を指定する。判別結果はGMがあなたのみに知らせる。",
                AbilityTimingEnum.NIGHT);
        Character kirigiri = new Character("霧切響子", abilityKirigiri, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc05.jpg");

        // 十神
        Ability abilityTogami = new Ability("超高校級の御曹司", "・あなたは1度だけ昼時間に手持ちのアイテムカードを場に1枚捨て、山札から新たにアイテムカードを1枚引くことができる。\r\n・捨てるアイテムカードを公開してはならない。\r\n・アイテムが2枚ある場合、捨てるアイテムは選択して良い。",
                AbilityTimingEnum.DAY);
        Character togami = new Character("十神白夜", abilityTogami, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc06.jpg");

        // 山田
        Ability abilityYamada = new Ability("超高校級の同人作家", "・あなたは誰かがアイテムカードを使用したとき、1度だけそれを無効にすることができる。\r\n・アイテムが使用されたタイミングで宣言することで使用可能。発動前に宣言して封じることはできない。",
                AbilityTimingEnum.ALL);
        Character yamada = new Character("山田一二三", abilityYamada, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc07.jpg");

        // 大和田
        Ability abilityOowada = new Ability("超高校級の暴走族", "・あなたは1度だけ昼時間に誰か1人のアイテムカードを見ることができる。\r\n・相手のカードを見た後、手持ちのアイテムカードと1枚を交換してもよい。\r\n・アイテムが2枚ある場合、交換する自分のカードと相手のカードをそれぞれ指定出来る。",
                AbilityTimingEnum.DAY);
        Character oowada = new Character("大和田紋土", abilityOowada, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc08.jpg");

        // 腐川
        Ability abilityFukawa = new Ability("超高校級の文学少女", "・あなたが死亡したとき、手持ちのアイテムカードを好きなだけ『十神白夜』にプレゼントすることができる。\r\n・クロの襲撃による死亡、おしおきによる死亡のどちらでも発動可能。",
                AbilityTimingEnum.DEAD);
        Character fukawa = new Character("腐川冬子", abilityFukawa, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc09.jpg");

        // セレス
        Ability abilityCeles = new Ability("・超高校級のギャンブラー", "あなたは1度だけ昼時間に、あなたと同数のアイテムカードを持つキャラクター1人を選び、手持ちのアイテムカード全てを交換することができる。\r\n・交換する際は必ず手持ちのアイテム全てを交換しなければならない。",
                AbilityTimingEnum.DAY);
        Character celes = new Character("セレスティア・ルーデンベルク", abilityCeles, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc10.jpg");

        // 朝日奈
        Ability abilityAsahina = new Ability("超高校級のスイマー", "・あなたは1度だけ学級裁判の投票前に誰か1人を選び、その投票を無効にすることができる。\r\n・対象に選ばれたキャラクターは決戦投票になった場合も引き続き投票出来ない。",
                AbilityTimingEnum.VOTE_BEFORE);
        Character asahina = new Character("朝日奈葵", abilityAsahina, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc11.jpg");

        // 石丸
        Ability abilityIshimaru = new Ability("超高校級の風紀委員", "・あなたは学級裁判のとき、1度だけノンストップ議論を3分延長することができる。\r\n・ノンストップ議論の最中に使用を宣言することで使用可能。",
                AbilityTimingEnum.TRIAL);
        Character ishimaru = new Character("石丸清多夏", abilityIshimaru, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc12.jpg");

        // 大神
        Ability abilityOokami = new Ability("超高校級の格闘家", "・あなたは自身を含む女性キャラクターがクロの襲撃を受けたとき、手持ちのアイテムカードを2枚捨て、1度だけそれを無効にすることができる。\r\n・アイテムが2枚ない場合は使用出来ない。\r\n・襲撃された朝に宣言することで使用可能。事前宣言による襲撃無効化はできない。",
                AbilityTimingEnum.MORNING);
        Character ookami = new Character("大神さくら", abilityOokami, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc13.jpg");

        // 葉隠
        Ability abilityHagakure = new Ability("超高校級の占い師", "・あなたは1度だけ夜時間に誰か1人を選び、絶望病患者かそれ以外か(もしくは、モノミかそれ以外か)を判別することができる。\r\n・絶望病患者、モノミは配役によって登場有無が変わる。\r\n・就寝前に使用のみ宣言し、就寝後に判別先を指定する。判別結果はGMがあなたのみに知らせる。",
                AbilityTimingEnum.NIGHT);
        Character hagakure = new Character("葉隠康比呂", abilityHagakure, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc14.jpg");

        // ギャル江ノ島
        Ability abilityEnoshima = new Ability("超高校級のギャル", "・あなたは1度だけその日の学級裁判に参加しなくてもよい。ただし、学級裁判のおしおき候補者には含まれる。\r\n・学級裁判開始前に宣言することで使用可能。",
                AbilityTimingEnum.TRIAL_BEFORE);
        Character enoshima = new Character("江ノ島盾子(ギャル)", abilityEnoshima, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc15.jpg");

        // 不二咲
        Ability abilityFujisaki = new Ability("超高校級のプログラマー", "・あなたは何度でも自身の性別を自由に選択することができる。\r\n・性別が範囲となる能力やアイテムが使用されたときに使用可能。",
                AbilityTimingEnum.ALL);
        Character fujisaki = new Character("不二咲千尋", abilityFujisaki, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc16.jpg");

        // ジェノサイダー
        Ability abilityGenocider = new Ability("超高校級の殺人鬼", "・あなたは『十神白夜』がクロの襲撃によって死亡したとき、全ての男性キャラクターのアイテムカードを強制的に場に1枚捨てさせる。\r\n・この能力は強制的に発動する。2枚以上持っているキャラクターは捨てるアイテムを選ぶことが出来る。",
                AbilityTimingEnum.ALL);
        Character genocider = new Character("ジェノサイダー翔", abilityGenocider, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc17.jpg");

        // むくろ
        Ability abilityIkusaba = new Ability("超高校級の軍人", "・あなたは手持ちのアイテムカードを場に1枚捨て、1度だけ誰か1人のキャラカードの能力を無効にすることができる。\r\n・常駐型の能力も対象に含まれる。\r\n・能力発動後に無効化することは出来ない。あらかじめ使用を制限することで発動を無効化する。",
                AbilityTimingEnum.ALL);
        Character ikusaba = new Character("戦場むくろ", abilityIkusaba, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc18.jpg");

        // シークレット江ノ島
        Ability abilitySecretEnoshima = new Ability("超高校級の絶望", "・あなたのロールカードが希望サイドの「シロ」だった場合、本来の勝利条件が変化し、あなたはクロが勝利したときに勝利する。\r\n・この能力はあなたの希望に関わらず、ゲーム開始時に強制的に発動する。\r\n・あなたがシロ以外だった場合には何の効果も発揮されない。",
                AbilityTimingEnum.ALL);
        Character secretEnoshima = new Character("江ノ島盾子(シークレット)", abilitySecretEnoshima, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc19.jpg");

        allCharacterList.add(naegi);
        allCharacterList.add(maizono);
        allCharacterList.add(kuwata);
        allCharacterList.add(kirigiri);
        allCharacterList.add(togami);
        allCharacterList.add(yamada);
        allCharacterList.add(oowada);
        allCharacterList.add(fukawa);
        allCharacterList.add(celes);
        allCharacterList.add(asahina);
        allCharacterList.add(ishimaru);
        allCharacterList.add(ookami);
        allCharacterList.add(hagakure);
        allCharacterList.add(enoshima);
        allCharacterList.add(fujisaki);
        allCharacterList.add(genocider);
        allCharacterList.add(ikusaba);
        allCharacterList.add(secretEnoshima);

        // 次回以降作らなくていいように保存
        characterList1 = allCharacterList;

        return allCharacterList;
    }


    /**
     * スーダンキャラのみの全キャラリストを作成して返す
     *
     * @return ArrayList<RoleCharacter> スーダンキャラのみの全キャラリスト
     */
    private static ArrayList<Character> create2CharacterList() {

        // 全キャラのリストを作成
        ArrayList<Character> allCharacterList = new ArrayList<Character>();

        // 日向
        Ability abilityHinata = new Ability("超高校級の？？？", "・学級裁判で決戦投票が発生したとき、あなたは1度だけ候補者からおしおきされるキャラクターを決めることができる。\r\n・自身が決選投票の候補者に含まれていても使用可能。\r\n・決選投票発生時に宣言することで使用可能。",
                AbilityTimingEnum.VOTE_BEFORE);
        Character hinata = new Character("日向創", abilityHinata, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc20.jpg");

        // 狛枝
        Ability abilityKomaeda = new Ability("超高校級の幸運", "・あなたは3日目の朝時間まで、自身へのクロの襲撃を無効にすることができる。\r\n・襲撃の対象に指定することは可能。3日の朝までなら何度でも無効にできる。",
                AbilityTimingEnum.MORNING);
        Character komaeda = new Character("狛枝凪斗", abilityKomaeda, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc21.jpg");

        // 田中
        Ability abilityTanaka = new Ability("超高校級の飼育委員", "・あなたは1度だけ昼時間に他のキャラクター4人のアイテムカードを見ることができる。\r\n・アイテムを見るキャラクターは任意に選択することができる。",
                AbilityTimingEnum.DAY);
        Character tanaka = new Character("田中眼蛇夢", abilityTanaka, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc22.jpg");

        // 左右田
        Ability abilitySouda = new Ability("超高校級のメカニック", "・あなたは1度だけ昼時間に山札からアイテムカードを1枚引き、『ソニア・ネヴァーマインド』にプレゼントすることができる。\r\n・ソニアがゲームに参加していない場合や、既に死亡している場合は使用できない。",
                AbilityTimingEnum.DAY);
        Character souda = new Character("左右田和一", abilitySouda, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc23.jpg");

        // 十神(2)
        Ability abilityTogami2 = new Ability("超高校級の御曹司", "・あなたは1度だけ昼時間に手持ちのアイテムカードを場に1枚捨て、山札から新たにアイテムカードを1枚引くことができる。\r\n・捨てるアイテムカードを公開してはならない。\r\n・アイテムが2枚ある場合、捨てるアイテムは選択して良い。",
                AbilityTimingEnum.DAY);
        Character togami2 = new Character("十神白夜(2)", abilityTogami2, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc24.jpg");

        // 花村
        Ability abilityHanamura = new Ability("超高校級の料理人", "・あなたは1度だけ昼時間に女性キャラクターから誰か1人を選び、アイテムカードを場に1枚捨てさせることができる。\r\n・捨てさせるアイテムカードを公開させてはならない。\r\n・アイテムを2枚持っている場合、捨てるカードは持ち主が選択することができる。",
                AbilityTimingEnum.DAY);
        Character hanamura = new Character("花村輝々", abilityHanamura, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc25.jpg");

        // 弐大
        Ability abilityNidai = new Ability("超高校級の暴走族", "・あなたがクロの襲撃で死亡したとき、手持ちのアイテムカード1枚を誰か1人にプレゼントすることができる。\r\n・プレゼントした相手がカードを2枚持っていた場合、持てるアイテムは2枚までのため、相手は1枚を選択して捨てる。",
                AbilityTimingEnum.MORNING_DEAD);
        Character nidai = new Character("弐大猫丸", abilityNidai, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc26.jpg");

        // 九頭龍
        Ability abilityKuzuryu = new Ability("超高校級の極道", "・あなたは学級裁判の投票前に、手持ちのアイテムカードを場に2枚捨て、自身を除く誰か1人を投票の候補者から外すことができる。\r\n・アイテムが2枚ない場合は使用不可。\r\n・捨てるアイテムカードを公開してはならない。",
                AbilityTimingEnum.VOTE_BEFORE);
        Character kuzuryu = new Character("九頭龍冬彦", abilityKuzuryu, false, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc27.jpg");

        // 終里
        Ability abilityOwari = new Ability("超高校級の体操部", "・あなたは、自身または『弐大猫丸』がクロの襲撃を受けたとき、手持ちのアイテムカードを場に1枚捨て、1度だけそれを無効にすることができる。\r\n・捨てるアイテムカードを公開してはならない。\r\n・アイテムが2枚ある場合、捨てるアイテムは選択して良い。\r\n・自分もしくは弐大が襲撃された朝に宣言することで使用可能。事前宣言による襲撃無効化はできない。",
                AbilityTimingEnum.MORNING);
        Character owari = new Character("終里赤音", abilityOwari, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc28.jpg");

        // 七海
        Ability abilityNanami = new Ability("超高校級のゲーマー", "・あなたは誰かがクロの襲撃を受けたとき、1度だけその身代わりとなることができる。\r\n・使用すると襲撃対象が自分に変更になり、襲撃を無効化する手段がある場合は重ねて使用できる。\r\n・自分以外の誰かが襲撃された朝に宣言することで使用可能。事前宣言による襲撃無効化はできない。",
                AbilityTimingEnum.MORNING);
        Character nanami = new Character("七海千秋", abilityNanami, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc29.jpg");

        // ソニア
        Ability abilitySonia = new Ability("超高校級の王女", "・あなたは1度だけ、昼時間に男性キャラクター1人からアイテムカードを1枚プレゼントされる。\r\n・プレゼントする相手はあなたが自由に選んで良い。\r\n・アイテムカードを1枚も持っていない相手は指定出来ない。\r\n・相手が2枚持っている場合、どちらをプレゼントするかは相手が選ぶことができる。",
                AbilityTimingEnum.DAY);
        Character sonia = new Character("ソニア・ネヴァーマインド", abilitySonia, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc30.jpg");

        // 西園寺
        Ability abilitySaionji = new Ability("超高校級の日本舞踊家", "・あなたは男性キャラクターがキャラカードの能力を使用したとき、それを1度だけ無効にすることができる。\r\n・ただし、常駐型の能力(苗木、狛枝、不二咲)には使用出来ない。\r\n・能力が使用されたタイミングで宣言することで使用可能。発動前に宣言して封じることはできない。",
                AbilityTimingEnum.ALL);
        Character saionji = new Character("西園寺日寄子", abilitySaionji, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc31.jpg");

        // 小泉
        Ability abilityKoizumi = new Ability("超高校級の写真家", "・あなたは手持ちのアイテムカードを使用したとき、1度だけ場に捨てずに手札に戻すことができる。\r\n・戻したいアイテムを使用した直後に宣言することで使用可能。",
                AbilityTimingEnum.ALL);
        Character koizumi = new Character("小泉真昼", abilityKoizumi, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc32.jpg");

        // 罪木
        Ability abilityTsumiki = new Ability("超高校級の保健委員", "・あなたは誰かがクロの襲撃によって死亡したとき、1度だけそのキャラクターのロールカードを見ることができる。ただし、死亡直後に限られる。\r\n・対象の死亡が確定した後に宣言することで使用可能。\r\n・次の時間帯へ進む前ならば、対象のロールカードがオープンされるかどうか確認してから使用を宣言しても良い。",
                AbilityTimingEnum.MORNING_DEAD);
        Character tsumiki = new Character("罪木蜜柑", abilityTsumiki, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc33.jpg");

        // 澪田
        Ability abilityMioda = new Ability("超高校級の軽音楽部", "・あなたは昼時間に誰か1人を選び、手持ちのアイテムカードを1枚プレゼントすることができる。\r\n・プレゼントした相手がカードを2枚持っていた場合、持てるアイテムは2枚までのため、相手は1枚を選択して捨てる。\r\n・手持ちのアイテムが1枚もない場合は使用不可。",
                AbilityTimingEnum.DAY);
        Character mioda = new Character("澪田唯吹", abilityMioda, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc34.jpg");

        // 辺古山
        Ability abilityPekoyama = new Ability("超高校級の剣道家", "・あなたは『九頭龍冬彦』がクロの襲撃を受けたとき、1度だけそれを無効にすることができる。\r\n・九頭龍がゲームに参加していない場合は使用出来ない。\r\n・九頭龍が襲撃された朝に宣言することで使用可能。事前宣言による襲撃無効化はできない。",
                AbilityTimingEnum.MORNING);
        Character pekoyama = new Character("辺古山ペコ", abilityPekoyama, true, "http://www.algernonproduct.co.jp/werewolf/img_dr1_2/card/cc18.jpg");


        allCharacterList.add(hinata);
        allCharacterList.add(komaeda);
        allCharacterList.add(tanaka);
        allCharacterList.add(souda);
        allCharacterList.add(togami2);
        allCharacterList.add(hanamura);
        allCharacterList.add(nidai);
        allCharacterList.add(kuzuryu);
        allCharacterList.add(owari);
        allCharacterList.add(nanami);
        allCharacterList.add(sonia);
        allCharacterList.add(saionji);
        allCharacterList.add(koizumi);
        allCharacterList.add(tsumiki);
        allCharacterList.add(mioda);
        allCharacterList.add(pekoyama);

        // 次回以降作らなくていいように保存
        characterList2 = allCharacterList;

        return allCharacterList;
    }


}
