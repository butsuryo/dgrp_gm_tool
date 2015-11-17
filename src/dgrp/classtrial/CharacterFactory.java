package dgrp.classtrial;

import java.util.ArrayList;
import java.util.Collections;

import dgrp.classtrial.model.Character;

/**
 * キャラクター情報を作成する
 * @author butsuryo
 *
 */
public class CharacterFactory {

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

        allCharacterList.add(new Character("苗木誠"));
        allCharacterList.add(new Character("舞園さやか"));
        allCharacterList.add(new Character("桑田怜恩"));
        allCharacterList.add(new Character("霧切響子"));
        allCharacterList.add(new Character("十神白夜"));
        allCharacterList.add(new Character("山田一二三"));
        allCharacterList.add(new Character("大和田紋土"));
        allCharacterList.add(new Character("腐川冬子"));
        allCharacterList.add(new Character("セレスティア・ルーデンベルク"));
        allCharacterList.add(new Character("朝日奈葵"));
        allCharacterList.add(new Character("石丸清多夏"));
        allCharacterList.add(new Character("大神さくら"));
        allCharacterList.add(new Character("葉隠康比呂"));
        allCharacterList.add(new Character("江ノ島盾子"));
        allCharacterList.add(new Character("不二咲千尋"));

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

        allCharacterList.add(new Character("日向創"));
        allCharacterList.add(new Character("狛枝凪斗"));
        allCharacterList.add(new Character("田中眼蛇夢"));
        allCharacterList.add(new Character("左右田和一"));
        allCharacterList.add(new Character("十神白夜(2)"));
        allCharacterList.add(new Character("花村輝々"));
        allCharacterList.add(new Character("弐大猫丸"));
        allCharacterList.add(new Character("九頭龍冬彦"));
        allCharacterList.add(new Character("終里赤音"));
        allCharacterList.add(new Character("七海千秋"));
        allCharacterList.add(new Character("ソニア・ネヴァーマインド"));
        allCharacterList.add(new Character("西園寺日寄子"));
        allCharacterList.add(new Character("小泉真昼"));
        allCharacterList.add(new Character("罪木蜜柑"));
        allCharacterList.add(new Character("澪田唯吹"));
        allCharacterList.add(new Character("辺古山ペコ"));

        // 次回以降作らなくていいように保存
        characterList2 = allCharacterList;

        return allCharacterList;
    }


}
