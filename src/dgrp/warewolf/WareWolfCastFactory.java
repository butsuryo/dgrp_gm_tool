package dgrp.warewolf;

import java.util.ArrayList;

import dgrp.warewolf.model.Cast;
import dgrp.warewolf.model.DifficultyEnum;
import dgrp.warewolf.model.Player;
import dgrp.warewolf.model.RoleEnum;

public class WareWolfCastFactory {


    /**
     * 参加人数、難易度を指定し、配役オブジェクトをリストで取得する
     *
     * @param playerNum 参加人数
     * @param difficulty 難易度
     * @return 配役オブジェクトのリスト
     */
    public static ArrayList<Cast> createCast(int playerNum, DifficultyEnum difficulty){

        ArrayList<Cast> castList = null;

        if (difficulty.equals(DifficultyEnum.EASY)){
            // いーじぃ
            castList = createEasyCast(playerNum, difficulty);

        }else{
            // のーまる・えくすとりぃむ
            castList = createNormalExtreamCast(playerNum, difficulty);
        }
        return castList;
    }


    /**
     * いーじぃ時の配役リストを作成して返す
     * @param playerNum 参加人数
     * @return ArrayList<CastModel> いーじぃ時の配役リスト
     */
    private static ArrayList<Cast> createEasyCast(int playerNum, DifficultyEnum difficulty){

        ArrayList<Cast> castList = new ArrayList<Cast>();

        switch(playerNum) {

            case 4 :
                // 4:エゴ1/シロ2/クロ1
                Cast easy4 = new Cast("エゴ1/シロ2/クロ1", DifficultyEnum.EASY, "4");
                easy4.setRoleList(createRoleList(2, 0, false, false, false));
                easy4.setOpenCastName(createOpenCastName(playerNum, difficulty, easy4.getCastName()));
                castList.add(easy4);
                break;

            case 5 :
                // 5:エゴ1/シロ3/クロ1
                Cast easy5 = new Cast("エゴ1/シロ3/クロ1", DifficultyEnum.EASY, "5");
                easy5.setRoleList(createRoleList(3, 0, false, false, false));
                easy5.setOpenCastName(createOpenCastName(playerNum, difficulty, easy5.getCastName()));
                castList.add(easy5);
                break;

            case 6 :
                // 6:エゴ1/シロ3/クロ1/裏切り者1
                Cast easy6 = new Cast("エゴ1/シロ3/クロ1/裏切り者1", DifficultyEnum.EASY, "6");
                easy6.setRoleList(createRoleList(3, 1, false, false, false));
                easy6.setOpenCastName(createOpenCastName(playerNum, difficulty, easy6.getCastName()));
                castList.add(easy6);
                break;

            case 7 :
                // 7:エゴ1/シロ4/クロ1/裏切り者1
                Cast easy7 = new Cast("エゴ1/シロ4/クロ1/裏切り者1", DifficultyEnum.EASY, "7");
                easy7.setRoleList(createRoleList(4, 1, false, false, false));
                easy7.setOpenCastName(createOpenCastName(playerNum, difficulty, easy7.getCastName()));
                castList.add(easy7);
                break;

            case 8 :
                // 8:エゴ1/シロ5/クロ1/裏切り者1
                Cast easy8 = new Cast("エゴ1/シロ5/クロ1/裏切り者1", DifficultyEnum.EASY, "8");
                easy8.setRoleList(createRoleList(5, 1, false, false, false));
                easy8.setOpenCastName(createOpenCastName(playerNum, difficulty, easy8.getCastName()));
                castList.add(easy8);
                break;

            case 9 :
                // 9-1:エゴ1/シロ6/クロ1/裏切り者1
                Cast easy9 = new Cast("エゴ1/シロ6/クロ1/裏切り者1", DifficultyEnum.EASY, "9");
                easy9.setRoleList(createRoleList(6, 1, false, false, false));
                easy9.setOpenCastName(createOpenCastName(playerNum, difficulty, easy9.getCastName()));
                castList.add(easy9);

                // 9-2:エゴ1/シロ5/クロ1/裏切り者2
                Cast easy9_2 = new Cast("エゴ1/シロ5/クロ1/裏切り者2", DifficultyEnum.EASY, "9");
                easy9_2.setRoleList(createRoleList(5, 2, false, false, false));
                easy9_2.setOpenCastName(createOpenCastName(playerNum, difficulty, easy9_2.getCastName()));
                castList.add(easy9_2);
                break;

            case 10 :
                // 10:エゴ1/シロ6/クロ1/裏切り者2
                Cast easy10 = new Cast("エゴ1/シロ6/クロ1/裏切り者2", DifficultyEnum.EASY, "10");
                easy10.setRoleList(createRoleList(6, 2, false, false, false));
                easy10.setOpenCastName(createOpenCastName(playerNum, difficulty, easy10.getCastName()));
                castList.add(easy10);
                break;

            case 11 :
                // 11:エゴ1/シロ7/クロ1/裏切り者2
                Cast easy11 = new Cast("エゴ1/シロ7/クロ1/裏切り者2", DifficultyEnum.EASY, "11");
                easy11.setRoleList(createRoleList(7, 2, false, false, false));
                easy11.setOpenCastName(createOpenCastName(playerNum, difficulty, easy11.getCastName()));
                castList.add(easy11);
                break;

            case 12 :
                // 12-1:エゴ1/シロ8/クロ1/裏切り者2
                Cast easy12 = new Cast("エゴ1/シロ8/クロ1/裏切り者2", DifficultyEnum.EASY, "12");
                easy12.setRoleList(createRoleList(8, 2, false, false, false));
                easy12.setOpenCastName(createOpenCastName(playerNum, difficulty, easy12.getCastName()));
                castList.add(easy12);

                // 12-2:エゴ1/シロ7/クロ1/裏切り者3
                Cast easy12_2 = new Cast("エゴ1/シロ7/クロ1/裏切り者3", DifficultyEnum.EASY, "12");
                easy12_2.setRoleList(createRoleList(7, 3, false, false, false));
                easy12_2.setOpenCastName(createOpenCastName(playerNum, difficulty, easy12_2.getCastName()));
                castList.add(easy12_2);
                break;

            case 13 :
                // 13:エゴ1/シロ8/クロ1/裏切り者3
                Cast easy13 = new Cast("エゴ1/シロ8/クロ1/裏切り者3", DifficultyEnum.EASY, "13");
                easy13.setRoleList(createRoleList(8, 3, false, false, false));
                easy13.setOpenCastName(createOpenCastName(playerNum, difficulty, easy13.getCastName()));
                castList.add(easy13);
                break;

            case 14 :
                // 14:エゴ1/シロ9/クロ1/裏切り者3
                Cast easy14 = new Cast("エゴ1/シロ9/クロ1/裏切り者3", DifficultyEnum.EASY, "14");
                easy14.setRoleList(createRoleList(9, 3, false, false, false));
                easy14.setOpenCastName(createOpenCastName(playerNum, difficulty, easy14.getCastName()));
                castList.add(easy14);
                break;

            case 15 :
                // 15-1:エゴ1/シロ10/クロ1/裏切り者3
                Cast easy15 = new Cast("エゴ1/シロ10/クロ1/裏切り者3", DifficultyEnum.EASY, "15");
                easy15.setRoleList(createRoleList(10, 3, false, false, false));
                easy15.setOpenCastName(createOpenCastName(playerNum, difficulty, easy15.getCastName()));
                castList.add(easy15);

                // 15-2:エゴ1/シロ9/クロ1/裏切り者4
                Cast easy15_2 = new Cast("エゴ1/シロ9/クロ1/裏切り者4", DifficultyEnum.EASY, "15");
                easy15_2.setRoleList(createRoleList(9, 4, false, false, false));
                easy15_2.setOpenCastName(createOpenCastName(playerNum, difficulty, easy15_2.getCastName()));
                castList.add(easy15_2);
                break;

            case 16 :
                // 16:エゴ1/シロ10/クロ1/裏切り者4
                Cast easy16 = new Cast("エゴ1/シロ10/クロ1/裏切り者4", DifficultyEnum.EASY, "16");
                easy16.setRoleList(createRoleList(10, 4, false, false, false));
                easy16.setOpenCastName(createOpenCastName(playerNum, difficulty, easy16.getCastName()));
                castList.add(easy16);
                break;

            default :
                castList = null;
                break;
        }

        return castList;
    }


    /**
     * のーまる・えくすとりぃむ時の配役リストを作成して返す
     * @param playerNum 参加人数
     * @param difficulty 難易度
     * @return ArrayList<CastModel> のーまる・えくすとりぃむ時の配役リスト
     */
    private static ArrayList<Cast> createNormalExtreamCast(int playerNum, DifficultyEnum difficulty){

        ArrayList<Cast> castList = new ArrayList<Cast>();

        switch(playerNum) {

            case 4 :
                // 4-1:エゴ1/シロ2/クロ1
                Cast normal4 = new Cast("エゴ1/シロ2/クロ1", difficulty, "4");
                normal4.setRoleList(createRoleList(2, 0, false, false, false));
                normal4.setOpenCastName(createOpenCastName(playerNum, difficulty, normal4.getCastName()));
                castList.add(normal4);

                // 4-2:エゴ1/シロ1/絶望病患者1/クロ1
                Cast normal4_2 = new Cast("エゴ1/シロ1/絶望病患者1/クロ1", difficulty, "4");
                normal4_2.setRoleList(createRoleList(1, 0, true, false, false));
                normal4_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal4_2.getCastName()));
                castList.add(normal4_2);
                break;

            case 5 :
                // 5-1:エゴ1/シロ3/クロ1
                Cast normal5 = new Cast("エゴ1/シロ3/クロ1", difficulty, "5");
                normal5.setRoleList(createRoleList(3, 0, false, false, false));
                normal5.setOpenCastName(createOpenCastName(playerNum, difficulty, normal5.getCastName()));
                castList.add(normal5);

                // 5-2:エゴ1/シロ2/絶望病患者1/クロ1
                Cast normal5_2 = new Cast("エゴ1/シロ2/絶望病患者1/クロ1", difficulty, "5");
                normal5_2.setRoleList(createRoleList(2, 0, true, false, false));
                normal5_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal5_2.getCastName()));
                castList.add(normal5_2);
                break;

            case 6 :
                // 6:エゴ1/シロ3/絶望病患者1/クロ1
                Cast normal6 = new Cast("エゴ1/シロ3/絶望病患者1/クロ1", difficulty, "6");
                normal6.setRoleList(createRoleList(3, 0, true, false, false));
                normal6.setOpenCastName(createOpenCastName(playerNum, difficulty, normal6.getCastName()));
                castList.add(normal6);
                break;

            case 7 :
                // 7-1:エゴ1/シロ3/モノミ1/クロ1/裏切り者1
                Cast normal7 = new Cast("エゴ1/シロ3/モノミ1/クロ1/裏切り者1", difficulty, "7");
                normal7.setRoleList(createRoleList(3, 1, false, true, false));
                normal7.setOpenCastName(createOpenCastName(playerNum, difficulty, normal7.getCastName()));
                castList.add(normal7);

                // 7-2:エゴ1/シロ3/モノミ1/クロ1/超高校級の絶望1
                Cast normal7_2 = new Cast("エゴ1/シロ3/モノミ1/クロ1/超高校級の絶望1", difficulty, "7");
                normal7_2.setRoleList(createRoleList(3, 0, false, true, true));
                normal7_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal7_2.getCastName()));
                castList.add(normal7_2);

                // 7-3:エゴ1/シロ3/クロ1/裏切り者1/超高校級の絶望1
                Cast normal7_3 = new Cast("エゴ1/シロ3/クロ1/裏切り者1/超高校級の絶望1", difficulty, "7");
                normal7_3.setRoleList(createRoleList(3, 1, false, false, true));
                normal7_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal7_3.getCastName()));
                castList.add(normal7_3);
                break;

            case 8 :
                // 8-1:エゴ1/シロ4/モノミ1/クロ1/裏切り者1
                Cast normal8 = new Cast("エゴ1/シロ4/モノミ1/クロ1/裏切り者1", difficulty, "8");
                normal8.setRoleList(createRoleList(4, 1, false, true, false));
                normal8.setOpenCastName(createOpenCastName(playerNum, difficulty, normal8.getCastName()));
                castList.add(normal8);

                // 8-2:エゴ1/シロ4/モノミ1/クロ1/超高校級の絶望1
                Cast normal8_2 = new Cast("エゴ1/シロ4/モノミ1/クロ1/超高校級の絶望1", difficulty, "8");
                normal8_2.setRoleList(createRoleList(4, 0, false, true, true));
                normal8_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal8_2.getCastName()));
                castList.add(normal8_2);

                // 8-3:エゴ1/シロ4/クロ1/裏切り者1/超高校級の絶望1
                Cast normal8_3 = new Cast("エゴ1/シロ4/クロ1/裏切り者1/超高校級の絶望1", difficulty, "8");
                normal8_3.setRoleList(createRoleList(4, 1, false, false, true));
                normal8_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal8_3.getCastName()));
                castList.add(normal8_3);

                // 8-4:エゴ1/シロ3/モノミ1/クロ1/裏切り者1/超高校級の絶望1
                Cast normal8_4 = new Cast("エゴ1/シロ3/モノミ1/クロ1/裏切り者1/超高校級の絶望1", difficulty, "8");
                normal8_4.setRoleList(createRoleList(3, 1, false, true, true));
                normal8_4.setOpenCastName(createOpenCastName(playerNum, difficulty, normal8_4.getCastName()));
                castList.add(normal8_4);
                break;

            case 9 :
                // 9-1:エゴ1/シロ5/モノミ1/クロ1/裏切り者1
                Cast normal9 = new Cast("エゴ1/シロ5/モノミ1/クロ1/裏切り者1", difficulty, "9");
                normal9.setRoleList(createRoleList(5, 1, false, true, false));
                normal9.setOpenCastName(createOpenCastName(playerNum, difficulty, normal9.getCastName()));
                castList.add(normal9);

                // 9-2:エゴ1/シロ5/モノミ1/クロ1/超高校級の絶望1
                Cast normal9_2 = new Cast("エゴ1/シロ5/モノミ1/クロ1/超高校級の絶望1", difficulty, "9");
                normal9_2.setRoleList(createRoleList(5, 0, false, true, true));
                normal9_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal9_2.getCastName()));
                castList.add(normal9_2);

                // 9-3:エゴ1/シロ5/クロ1/裏切り者1/超高校級の絶望1
                Cast normal9_3 = new Cast("エゴ1/シロ5/クロ1/裏切り者1/超高校級の絶望1", difficulty, "9");
                normal9_3.setRoleList(createRoleList(5, 1, false, false, true));
                normal9_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal9_3.getCastName()));
                castList.add(normal9_3);

                // 9-4:エゴ1/シロ4/モノミ1/クロ1/裏切り者1/超高校級の絶望1
                Cast normal9_4 = new Cast("エゴ1/シロ4/モノミ1/クロ1/裏切り者1/超高校級の絶望1", difficulty, "9");
                normal9_4.setRoleList(createRoleList(4, 1, false, true, true));
                normal9_4.setOpenCastName(createOpenCastName(playerNum, difficulty, normal9_4.getCastName()));
                castList.add(normal9_4);
                break;

            case 10 :
                // 10-1:エゴ1/シロ5/モノミ1/クロ1/裏切り者1/超高校級の絶望1
                Cast normal10 = new Cast("エゴ1/シロ5/モノミ1/クロ1/裏切り者1/超高校級の絶望1", difficulty, "10");
                normal10.setRoleList(createRoleList(5, 1, false, true, true));
                normal10.setOpenCastName(createOpenCastName(playerNum, difficulty, normal10.getCastName()));
                castList.add(normal10);

                // 10-2:エゴ1/シロ5/モノミ1/クロ1/裏切り者2
                Cast normal10_2 = new Cast("エゴ1/シロ5/モノミ1/クロ1/裏切り者2", difficulty, "10");
                normal10_2.setRoleList(createRoleList(5, 2, false, true, false));
                normal10_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal10_2.getCastName()));
                castList.add(normal10_2);

                // 10-3:エゴ1/シロ5/クロ1/裏切り者2/超高校級の絶望1
                Cast normal10_3 = new Cast("エゴ1/シロ5/クロ1/裏切り者2/超高校級の絶望1", difficulty, "10");
                normal10_3.setRoleList(createRoleList(5, 2, false, false, true));
                normal10_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal10_3.getCastName()));
                castList.add(normal10_3);
                break;

            case 11 :
                // 11-1:エゴ1/シロ6/モノミ1/クロ1/裏切り者1/超高校級の絶望1
                Cast normal11 = new Cast("エゴ1/シロ6/モノミ1/クロ1/裏切り者1/超高校級の絶望1", difficulty, "11");
                normal11.setRoleList(createRoleList(6, 1, false, true, true));
                normal11.setOpenCastName(createOpenCastName(playerNum, difficulty, normal11.getCastName()));
                castList.add(normal11);

                // 11-2:エゴ1/シロ6/モノミ1/クロ1/裏切り者2
                Cast normal11_2 = new Cast("エゴ1/シロ6/モノミ1/クロ1/裏切り者2", difficulty, "11");
                normal11_2.setRoleList(createRoleList(6, 2, false, true, false));
                normal11_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal11_2.getCastName()));
                castList.add(normal11_2);

                // 11-3:エゴ1/シロ6/クロ1/裏切り者2/超高校級の絶望1
                Cast normal11_3 = new Cast("エゴ1/シロ6/クロ1/裏切り者2/超高校級の絶望1", difficulty, "11");
                normal11_3.setRoleList(createRoleList(6, 2, false, false, true));
                normal11_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal11_3.getCastName()));
                castList.add(normal11_3);

                // 11-4:エゴ1/シロ5/モノミ1/クロ1/裏切り者2/超高校級の絶望1
                Cast normal11_4 = new Cast("エゴ1/シロ5/モノミ1/クロ1/裏切り者2/超高校級の絶望1", difficulty, "11");
                normal11_4.setRoleList(createRoleList(5, 2, false, true, true));
                normal11_4.setOpenCastName(createOpenCastName(playerNum, difficulty, normal11_4.getCastName()));
                castList.add(normal11_4);
                break;

            case 12 :
                // 12-1:エゴ1/シロ7/モノミ1/クロ1/裏切り者1/超高校級の絶望1
                Cast normal12 = new Cast("エゴ1/シロ7/モノミ1/クロ1/裏切り者1/超高校級の絶望1", difficulty, "12");
                normal12.setRoleList(createRoleList(7, 1, false, true, true));
                normal12.setOpenCastName(createOpenCastName(playerNum, difficulty, normal12.getCastName()));
                castList.add(normal12);

                // 12-2:エゴ1/シロ7/モノミ1/クロ1/裏切り者2
                Cast normal12_2 = new Cast("エゴ1/シロ7/モノミ1/クロ1/裏切り者2", difficulty, "12");
                normal12_2.setRoleList(createRoleList(7, 2, false, true, false));
                normal12_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal12_2.getCastName()));
                castList.add(normal12_2);

                // 12-3:エゴ1/シロ7/クロ1/裏切り者2/超高校級の絶望1
                Cast normal12_3 = new Cast("エゴ1/シロ7/クロ1/裏切り者2/超高校級の絶望1", difficulty, "12");
                normal12_3.setRoleList(createRoleList(7, 2, false, false, true));
                normal12_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal12_3.getCastName()));
                castList.add(normal12_3);

                // 12-4:エゴ1/シロ6/モノミ1/クロ1/裏切り者2/超高校級の絶望1
                Cast normal12_4 = new Cast("エゴ1/シロ6/モノミ1/クロ1/裏切り者2/超高校級の絶望1", difficulty, "12");
                normal12_4.setRoleList(createRoleList(6, 2, false, true, true));
                normal12_4.setOpenCastName(createOpenCastName(playerNum, difficulty, normal12_4.getCastName()));
                castList.add(normal12_4);
                break;

            case 13 :
                // 13-1:エゴ1/シロ7/モノミ1/クロ1/裏切り者2/超高校級の絶望1
                Cast normal13 = new Cast("エゴ1/シロ7/モノミ1/クロ1/裏切り者2/超高校級の絶望1", difficulty, "13");
                normal13.setRoleList(createRoleList(7, 2, false, true, true));
                normal13.setOpenCastName(createOpenCastName(playerNum, difficulty, normal13.getCastName()));
                castList.add(normal13);

                // 13-2:エゴ1/シロ7/モノミ1/クロ1/裏切り者3
                Cast normal13_2 = new Cast("エゴ1/シロ7/モノミ1/クロ1/裏切り者3", difficulty, "13");
                normal13_2.setRoleList(createRoleList(7, 3, false, true, false));
                normal13_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal13_2.getCastName()));
                castList.add(normal13_2);

                // 13-3:エゴ1/シロ7/クロ1/裏切り者3/超高校級の絶望1
                Cast normal13_3 = new Cast("エゴ1/シロ7/クロ1/裏切り者3/超高校級の絶望1", difficulty, "13");
                normal13_3.setRoleList(createRoleList(7, 3, false, false, true));
                normal13_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal13_3.getCastName()));
                castList.add(normal13_3);

                // 13-4:エゴ1/シロ6/モノミ1/クロ1/裏切り者3/超高校級の絶望1
                Cast normal13_4 = new Cast("エゴ1/シロ6/モノミ1/クロ1/裏切り者3/超高校級の絶望1", difficulty, "13");
                normal13_4.setRoleList(createRoleList(6, 3, false, true, true));
                normal13_4.setOpenCastName(createOpenCastName(playerNum, difficulty, normal13_4.getCastName()));
                castList.add(normal13_4);
                break;

            case 14 :
                // 14-1:エゴ1/シロ8/モノミ1/クロ1/裏切り者2/超高校級の絶望1
                Cast normal14 = new Cast("エゴ1/シロ8/モノミ1/クロ1/裏切り者2/超高校級の絶望1", difficulty, "14");
                normal14.setRoleList(createRoleList(8, 2, false, true, true));
                normal14.setOpenCastName(createOpenCastName(playerNum, difficulty, normal14.getCastName()));
                castList.add(normal14);

                // 14-2:エゴ1/シロ8/モノミ1/クロ1/裏切り者3
                Cast normal14_2 = new Cast("エゴ1/シロ8/モノミ1/クロ1/裏切り者3", difficulty, "14");
                normal14_2.setRoleList(createRoleList(8, 3, false, true, false));
                normal14_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal14_2.getCastName()));
                castList.add(normal14_2);

                // 14-3:エゴ1/シロ8/クロ1/裏切り者3/超高校級の絶望1
                Cast normal14_3 = new Cast("エゴ1/シロ8/クロ1/裏切り者3/超高校級の絶望1", difficulty, "14");
                normal14_3.setRoleList(createRoleList(8, 3, false, false, true));
                normal14_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal14_3.getCastName()));
                castList.add(normal14_3);

                // 14-4:エゴ1/シロ7/モノミ1/クロ1/裏切り者3/超高校級の絶望1
                Cast normal14_4 = new Cast("エゴ1/シロ7/モノミ1/クロ1/裏切り者3/超高校級の絶望1", difficulty, "14");
                normal14_4.setRoleList(createRoleList(7, 3, false, true, true));
                normal14_4.setOpenCastName(createOpenCastName(playerNum, difficulty, normal14_4.getCastName()));
                castList.add(normal14_4);
                break;

            case 15 :
                // 15-1:エゴ1/シロ9/モノミ1/クロ1/裏切り者2/超高校級の絶望1
                Cast normal15 = new Cast("エゴ1/シロ9/モノミ1/クロ1/裏切り者2/超高校級の絶望1", difficulty, "15");
                normal15.setRoleList(createRoleList(9, 2, false, true, true));
                normal15.setOpenCastName(createOpenCastName(playerNum, difficulty, normal15.getCastName()));
                castList.add(normal15);

                // 15-2:エゴ1/シロ9/モノミ1/クロ1/裏切り者3
                Cast normal15_2 = new Cast("エゴ1/シロ9/モノミ1/クロ1/裏切り者3", difficulty, "15");
                normal15_2.setRoleList(createRoleList(9, 3, false, true, false));
                normal15_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal15_2.getCastName()));
                castList.add(normal15_2);

                // 15-3:エゴ1/シロ9/クロ1/裏切り者3/超高校級の絶望1
                Cast normal15_3 = new Cast("エゴ1/シロ9/クロ1/裏切り者3/超高校級の絶望1", difficulty, "15");
                normal15_3.setRoleList(createRoleList(9, 3, false, false, true));
                normal15_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal15_3.getCastName()));
                castList.add(normal15_3);

                // 15-4:エゴ1/シロ8/モノミ1/クロ1/裏切り者3/超高校級の絶望1
                Cast normal15_4 = new Cast("エゴ1/シロ8/モノミ1/クロ1/裏切り者3/超高校級の絶望1", difficulty, "15");
                normal15_4.setRoleList(createRoleList(8, 3, false, true, true));
                normal15_4.setOpenCastName(createOpenCastName(playerNum, difficulty, normal15_4.getCastName()));
                castList.add(normal15_4);
                break;

            case 16 :
                // 16-1:エゴ1/シロ9/モノミ1/クロ1/裏切り者3/超高校級の絶望1
                Cast normal16 = new Cast("エゴ1/シロ9/モノミ1/クロ1/裏切り者3/超高校級の絶望1", difficulty, "16");
                normal16.setRoleList(createRoleList(9, 3, false, true, true));
                normal16.setOpenCastName(createOpenCastName(playerNum, difficulty, normal16.getCastName()));
                castList.add(normal16);

                // 16-2:エゴ1/シロ9/モノミ1/クロ1/裏切り者4
                Cast normal16_2 = new Cast("エゴ1/シロ9/モノミ1/クロ1/裏切り者4", difficulty, "16");
                normal16_2.setRoleList(createRoleList(9, 4, false, true, false));
                normal16_2.setOpenCastName(createOpenCastName(playerNum, difficulty, normal16_2.getCastName()));
                castList.add(normal16_2);

                // 16-3:エゴ1/シロ9/クロ1/裏切り者4/超高校級の絶望1
                Cast normal16_3 = new Cast("エゴ1/シロ9/クロ1/裏切り者4/超高校級の絶望1", difficulty, "16");
                normal16_3.setRoleList(createRoleList(9, 4, false, false, true));
                normal16_3.setOpenCastName(createOpenCastName(playerNum, difficulty, normal16_3.getCastName()));
                castList.add(normal16_3);

                // 16-4:エゴ1/シロ8/モノミ1/クロ1/裏切り者4/超高校級の絶望1
                Cast normal16_4 = new Cast("エゴ1/シロ8/モノミ1/クロ1/裏切り者4/超高校級の絶望1", difficulty, "16");
                normal16_4.setRoleList(createRoleList(8, 4, false, true, true));
                normal16_4.setOpenCastName(createOpenCastName(playerNum, difficulty, normal16_4.getCastName()));
                castList.add(normal16_4);
                break;

            default :
                castList = null;
                break;
        }

        return castList;
    }


    /**
     * 配役の人数を指定し、ロールモデルを配役分だけ詰めたリストを作成して返す
     * ※エゴとクロはどの配役でも1人ずつ固定なので引数に含んでいない
     *
     * @param whiteNum シロの人数
     * @param traitorNum 裏切り者の人数
     * @param hasPatient 絶望病患者がいるかどうか
     * @param hasMmonomi モノミがいるかどうか
     * @param hasDdespair 超高校級の絶望がいるかどうか
     * @return ArrayList<RoleBaceModel> ロールモデルを配役分だけ詰めたリスト
     */
    private static ArrayList<Player> createRoleList(int whiteNum, int traitorNum, boolean hasPatient,
            boolean hasMmonomi, boolean hasDdespair){

        ArrayList<Player> roleList = new ArrayList<Player>();

        // シロ
        for (int i=0; i<whiteNum; i++) {
            roleList.add(new Player(RoleEnum.WHITE));
        }

        // 裏切り者
        for (int i=0; i<traitorNum; i++) {
            roleList.add(new Player(RoleEnum.TRAITOR));
        }

        // 絶望病患者
        if (hasPatient) {
            roleList.add(new Player(RoleEnum.PATIENT));
        }

        // モノミ
        if (hasMmonomi) {
            roleList.add(new Player(RoleEnum.MONOMI));
        }

        // 超高校級の絶望
        if (hasDdespair) {
            roleList.add(new Player(RoleEnum.DESPAIR));
        }

        // クロ
        roleList.add(new Player(RoleEnum.BLACK));

        // エゴ
        roleList.add(new Player(RoleEnum.EGO));

        return roleList;
    }


    /**
     * 参加者に公開してよい、公開用配役名称を取得する
     *
     * @param playerNum 参加人数
     * @param difficulty 難易度
     * @param castName 非公開配役名称
     * @return String 公開用配役名称
     */
    private static String createOpenCastName(int playerNum, DifficultyEnum difficulty, String castName) {
        String openCastName = "";

        // いーじぃ時
        if (DifficultyEnum.EASY.equals(difficulty)) {

            // 9,12,15人編成以外は役欠けなし
            switch(playerNum) {
                case 9 :
                    openCastName = "エゴ1/シロ5～6/クロ1/裏切り者1～2 (役欠け:シロor裏切り者)";
                    break;

                case 12 :
                    openCastName = "エゴ1/シロ7～8/クロ1/裏切り者2～3 (役欠け:シロor裏切り者)";
                    break;

                case 15:
                    openCastName = "エゴ1/シロ9～10/クロ1/裏切り者3～4 (役欠け:シロor裏切り者)";
                    break;

                default :
                    // 役欠けなしのときは配役名をそのまま公表してOK
                    openCastName = castName;
                    break;
            }

        // のーまる・えくすとりぃむ時
        }else{

            // 6人編成以外は全部役欠けあり
            switch(playerNum) {
                case 4 :
                    openCastName = "エゴ1/シロ1～2/絶望病患者0～1/クロ1 (役欠け:シロor絶望病患者)";
                    break;

                case 5 :
                    openCastName = "エゴ1/シロ2～3/絶望病患者0～1/クロ1 (役欠け:シロor絶望病患者)";
                    break;

                case 7 :
                    openCastName = "エゴ1/シロ3/モノミ0～1/クロ1/裏切り者0～1/超高校級の絶望0～1 (役欠け:モノミor裏切り者or超高校級の絶望)";
                    break;

                case 8 :
                    openCastName = "エゴ1/シロ3～4/モノミ0～1/クロ1/裏切り者0～1/超高校級の絶望0～1 (役欠け:シロorモノミor裏切り者or超高校級の絶望)";
                    break;

                case 9 :
                    openCastName = "エゴ1/シロ4～5/モノミ0～1/クロ1/裏切り者0～1/超高校級の絶望0～1 (役欠け:シロorモノミor裏切り者or超高校級の絶望)";
                    break;

                case 10 :
                    openCastName = "エゴ1/シロ5/モノミ0～1/クロ1/裏切り者1～2/超高校級の絶望0～1 (役欠け:モノミor裏切り者or超高校級の絶望)";
                    break;

                case 11 :
                    openCastName = "エゴ1/シロ5～6/モノミ0～1/クロ1/裏切り者1～2/超高校級の絶望0～1 (役欠け:シロorモノミor裏切り者or超高校級の絶望)";
                    break;

                case 12 :
                    openCastName = "エゴ1/シロ6～7/モノミ0～1/クロ1/裏切り者1～2/超高校級の絶望0～1 (役欠け:シロorモノミor裏切り者or超高校級の絶望)";
                    break;

                case 13 :
                    openCastName = "エゴ1/シロ6～7/モノミ0～1/クロ1/裏切り者2～3/超高校級の絶望0～1 (役欠け:シロorモノミor裏切り者or超高校級の絶望)";
                    break;

                case 14 :
                    openCastName = "エゴ1/シロ7～8/モノミ0～1/クロ1/裏切り者2～3/超高校級の絶望0～1 (役欠け:シロorモノミor裏切り者or超高校級の絶望)";
                    break;

                case 15 :
                    openCastName = "エゴ1/シロ8～9/モノミ0～1/クロ1/裏切り者2～3/超高校級の絶望0～1 (役欠け:シロorモノミor裏切り者or超高校級の絶望)";
                    break;

                case 16 :
                    openCastName = "エゴ1/シロ8～9/モノミ0～1/クロ1/裏切り者3～4/超高校級の絶望0～1 (役欠け:シロorモノミor裏切り者or超高校級の絶望)";
                    break;

                default :
                    openCastName = castName;
                    break;
            }
        }

        return openCastName;
    }

}
