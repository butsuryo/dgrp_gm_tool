package dgrp.classtrial;

import java.util.ArrayList;

import dgrp.classtrial.model.Cast;
import dgrp.classtrial.model.Player;
import dgrp.classtrial.model.RoleEnum;

/**
 * 参加人数から配役を作成する
 * @author butsuryo
 *
 */
public class CastFactory {


    /**
     * 参加人数、難易度を指定し、配役オブジェクトをリストで取得する
     *
     * @param playerNum 参加人数
     * @return 配役オブジェクト
     */
    public static Cast createCast(int playerNum){

        switch(playerNum) {

            case 3 :
                Cast cast3 = new Cast("5人(欠席者2名)：希望1/ギャンブラー1/探偵1/クロ2", "3");
                cast3.setPlayerList(createPlayerList(1, 2, 1, 1, 0));
                return cast3;

            case 4 :
                Cast cast4 = new Cast("6人(欠席者2名)：希望2/ギャンブラー1/探偵1/クロ2", "4");
                cast4.setPlayerList(createPlayerList(2, 2, 1, 1, 0));
                return cast4;

            case 5 :
                Cast cast5 = new Cast("7人(欠席者2名)：希望3/ギャンブラー1/探偵1/クロ2", "5");
                cast5.setPlayerList(createPlayerList(3, 2, 1, 1, 0));
                return cast5;

            case 6 :
                Cast cast6 = new Cast("7人(欠席者1名)：希望3/ギャンブラー1/探偵1/クロ2", "6");
                cast6.setPlayerList(createPlayerList(3, 2, 1, 1, 0));
                return cast6;

            case 7 :
                Cast cast7 = new Cast("8人(欠席者1名)：希望3/ギャンブラー1/探偵1/クロ2/絶望1", "7");
                cast7.setPlayerList(createPlayerList(3, 2, 1, 1, 1));
                return cast7;

            case 8 :
                Cast cast8 = new Cast("9人(欠席者1名)：希望3/ギャンブラー1/探偵2/クロ2/絶望1", "8");
                cast8.setPlayerList(createPlayerList(3, 2, 2, 1, 1));
                return cast8;
            case 9 :
                Cast cast9 = new Cast("10人(欠席者1名)：希望3/ギャンブラー1/探偵2/クロ3/絶望1", "9");
                cast9.setPlayerList(createPlayerList(3, 3, 2, 1, 1));
                return cast9;

            default :
                return null;
        }

    }

    /**
     * 配役の人数を指定し、参加者モデルを配役分だけ詰めたリストを作成して返す
     *
     * @param integer hopeNum      希望の人数
     * @param integer blackNum     クロの人数
     * @param integer detecteveNum 探偵の人数
     * @param integer gamblerNum   ギャンブラーの人数
     * @param integer despairNum   絶望の人数
     * @return ArrayList<RoleBaceModel> ロールモデルを配役分だけ詰めたリスト
     */
    private static ArrayList<Player> createPlayerList(int hopeNum, int blackNum, int detecteveNum,
            int gamblerNum, int despairNum){

        ArrayList<Player> playerList = new ArrayList<Player>();

        // 希望
        for (int i=0; i<hopeNum; i++) {
            playerList.add(new Player(RoleEnum.HOPE));
        }

        // クロ
        for (int i=0; i<blackNum; i++) {
            playerList.add(new Player(RoleEnum.BLACK));
        }

        // 探偵
        for (int i=0; i<detecteveNum; i++) {
            playerList.add(new Player(RoleEnum.DETECTIVE));
        }

        // 絶望
        for (int i=0; i<despairNum; i++) {
            playerList.add(new Player(RoleEnum.DESPAIR));
        }

        // ギャンブラー
        for (int i=0; i<gamblerNum; i++) {
            playerList.add(new Player(RoleEnum.GAMBLER));
        }

        return playerList;
    }

}
