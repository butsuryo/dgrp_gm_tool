package dgrp.classtrial.wizard;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import dgrp.classtrial.CastFactory;
import dgrp.classtrial.CharacterFactory;
import dgrp.classtrial.model.Cast;
import dgrp.classtrial.model.Character;
import dgrp.classtrial.model.Player;


/**
 * ゲーム開始設定ウィザード
 * @author butsuryo
 *
 */
public class ClassTrialWizard extends Wizard implements INewWizard{

    private ClassTrialWizardPage1 page;
    private ClassTrialWizardPage2 page2;
    private ClassTrialWizardPage3 page3;
    private ClassTrialWizardPage4 page4;
    private ClassTrialWizardPage5 page5;
    private ClassTrialWizardPage6 page6;
    private ClassTrialWizardPage7 page7;
    private ClassTrialWizardPage8 page8;

    // ウィザードページを行き来するとき、前から来たか後から来たかを判断するための苦肉の策
    private int beforePageNum;
    private int currentPageNum;
    private boolean isNextPageEvent;

    // 配役
    private Cast cast;


    public void setCast(Cast cast) {
        this.cast = cast;
    }

    public Cast getCast() {
        return cast;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.cast.setPlayerList(playerList);
    }

    public ArrayList<Player> getPlayerList() {
        return cast.getPlayerList();
    }

    @Override
    public void init(IWorkbench workbanch, IStructuredSelection selection) {
        this.isNextPageEvent = true;
    }

    @Override
    public void addPages() {
        this.isNextPageEvent = true;

        // ウィザードのサイズを設定
        getContainer().getShell().setSize(900,780);

        this.page = new ClassTrialWizardPage1("ぺーじ1");
        this.page2 = new ClassTrialWizardPage2("ぺーじ2");
        this.page3 = new ClassTrialWizardPage3("ぺーじ3");
        this.page4 = new ClassTrialWizardPage4("ぺーじ4");
        this.page5 = new ClassTrialWizardPage5("ぺーじ5");
        this.page6 = new ClassTrialWizardPage6("ぺーじ6");
        this.page7 = new ClassTrialWizardPage7("ぺーじ7");
        this.page8 = new ClassTrialWizardPage8("ぺーじ8");
        addPage(this.page);
        addPage(this.page2);
        addPage(this.page3);
        addPage(this.page4);
        addPage(this.page5);
        addPage(this.page6);
        addPage(this.page7);
        addPage(this.page8);
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 3ページ目の完了ボタン押下に走る、完了時処理
     */
    @Override
    public boolean performFinish() {

        return true;

    }

    /**
     * 現在のページ番号をセットする
     * （直前まで「現在のページ番号」にセットされていたものは「一つ前のページ番号」に退避する）
     * @param int num 現在のページ番号
     */
    public void setCurrentPageNum(int num) {

        // ページ遷移時、遷移前と先で両方setVisibleに入ってしまうため、
        // 先に検知した方＝遷移先とし、遷移先のイベント時のみページ番号を設定する(苦肉の策)
        if (this.isNextPageEvent) {
            this.beforePageNum = this.currentPageNum;
            this.currentPageNum = num;
            this.isNextPageEvent = false;

        } else {
            this.isNextPageEvent = true;
        }
    }

    /**
     * 現在のページ番号をセットする
     * （直前まで「現在のページ番号」にセットされていたものは「一つ前のページ番号」に退避する）
     * @param int num 現在のページ番号
     */
    public void setCurrentPageNumFirst(int num) {
        this.beforePageNum = this.currentPageNum;
        this.currentPageNum = num;
    }

    /**
     * ひとつ前のページ番号を取得する
     * @return int ひとつ前のページ番号
     */
    public int getBeforePageNum() {
        return this.beforePageNum;
    }

    @Override
    public boolean canFinish() {
        // 最終ページに来たら完了ボタンを活性化
        if (this.currentPageNum == 8) {
            return true;
        }
        return false;
    }

    /**
     * 配役を新規作成
     * @param playerNum 参加人数
     * @return 新規作成した配役
     */
    public Cast createCast(int playerNum, ArrayList<String> playerNameList) {

        // 配役と役職枠だけ作成
        cast = CastFactory.createCast(playerNum);

        // キャラクターとプレイヤーを割り振る
        settingRoleModel(playerNameList);

        return cast;
    }

    /**
     * 作成済みの配役に「キャラクター」「プレイヤー」を割り振る
     *
     * @param playerNameList 参加者のリスト
     */
    private void settingRoleModel(ArrayList<String> playerNameList){

        ArrayList<Player> playerList = getPlayerList();

        // 欠席者をプレイヤーリストに追加
        int diffNum = (playerList.size() - playerNameList.size());
        for (int index=0; index<diffNum; index++) {
            playerNameList.add("欠席者");
        }

        // 抽選キャラリストを取得
        ArrayList<Character> characterList = CharacterFactory.createCharacterList(playerNameList.size(), 0);    // TODO キャラ1,2から選べるけど今んとこ固定

        // 参加者を抽選するためにランダムソートする
        Collections.shuffle(playerNameList);

        // 配役にキャラ、プレイヤーを設定する
        for (int index=0; index<playerList.size(); index++) {

            Player player = playerList.get(index);
            String playerName = playerNameList.get(index);

            // 役職に参加者を宛がう
            player.setPlayerName(playerName);

            // 役職にキャラを宛がう
            player.setCharacter(characterList.get(index));

            // 欠席者だった場合は欠席者フラグを立てる
            if ("欠席者".equals(playerName)) {
                player.setAbsent(true);
            }
        }

        // 席順をランダムにするためにランダムソートする
        Collections.shuffle(playerList);
    }
}
