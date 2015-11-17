package dgrp.warewolf.wizard;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import dgrp.Activator;
import dgrp.Perspective;
import dgrp.Util;
import dgrp.warewolf.model.Cast;
import dgrp.warewolf.model.DifficultyEnum;
import dgrp.warewolf.model.Player;


/**
 * 超高校級の人狼ゲーム開始ウィザード
 *
 * @author butsuryo
 *
 */
public class WareWolfWizard extends Wizard implements INewWizard{

    private WareWolfWizardPage1 page;
    private WareWolfWizardPage2 page2;
    private WareWolfWizardPage3 page3;
    private WareWolfWizardPage4 page4;
    private WareWolfWizardPage5 page5;

    // 配役表示名
    private String castName;
    // 配役オブジェクト
    private Cast castModel;
    // 参加者人数
    private String playerNum;
    // 難易度
    private DifficultyEnum difficultyEnum;
    // キャラクター使用区分(1:無印のみ、2:スーダンのみ、3:両方)
    private int useCharacterType;
    // ゲームの基本データ(プレイヤーに役職、キャラ、アイテムを宛がったモデルのリスト)
    private ArrayList<Player> roleList;

    // ウィザードページを行き来するとき、前から来たか後から来たかを判断するための苦肉の策
    private int beforePageNum;
    private int currentPageNum;
    private boolean isNextPageEvent;

    // アクセサ群
    public void setCastName(String castName) {
        this.castName = castName;
    }
    public String getCastName() {
        return castName;
    }
    public void setCastModel(Cast castModel) {
        this.castModel = castModel;
    }
    public Cast getCastModel() {
        return castModel;
    }
    public void setPlayerNum(String playerNum) {
        this.playerNum = playerNum;
    }
    public String getPlayerNum() {
        return playerNum;
    }
    public void setDifficultyEnum(DifficultyEnum difficultyEnum) {
        this.difficultyEnum = difficultyEnum;
    }
    public DifficultyEnum getDifficultyEnum() {
        return difficultyEnum;
    }
    public void setUseCharacterType(int useCharacterType) {
        this.useCharacterType = useCharacterType;
    }
    public int getUseCharacterType() {
        return useCharacterType;
    }
    public void setRoleList(ArrayList<Player> roleList) {
        this.roleList = roleList;
    }
    public ArrayList<Player> getRoleList() {
        return roleList;
    }


    @Override
    public void init(IWorkbench workbanch, IStructuredSelection selection) {
        // no operation.
    }

    @Override
    public void addPages() {
        this.isNextPageEvent = true;
        page = new WareWolfWizardPage1("ぺーじ1");
        page2 = new WareWolfWizardPage2("ぺーじ2");
        page3 = new WareWolfWizardPage3("ぺーじ3");
        page4 = new WareWolfWizardPage4("ぺーじ4");
        page5 = new WareWolfWizardPage5("ぺーじ5");
        addPage(page);
        addPage(page2);
        addPage(page3);
        addPage(page4);
        addPage(page5);
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 3ページ目の完了ボタン押下に走る、完了時処理
     */
    @Override
    public boolean performFinish() {

        // キャラクターをダブルクリックするとコピペ文章が表示される
        // アイテムをダブルクリックすると使用時のコピペが表示される
        // (できれば選択されたアイテムを削除する⇒OK/キャンセル)
        // (できればチェック入れて)山札から一枚引く　ボタンで新しいアイテムが入る、空きがないときはどうする？
        //
        // ヘルプで能力解説一覧、アイテム解説一覧が見たい
        // OK押すと⇒即使用アイテムは、アイテムが使用済み判定になり、使用済みアイテム一覧：のところにaddされ、アイテム欄から消える(山札の下に戻す)
        // 夜使用アイテムは
        // 右に状態欄が欲しい。「襲撃」「護衛」「使用されているアイテム(黄金銃、ジャスティスロボ)」「死亡(襲撃)」「死亡(おしおき)」
        // 左にチェックボックスつける
        // ボタン「アルターエゴ解析」「襲撃」「護衛」

        // 設定した情報を保持
        //Util.setWareWolfData(getRoleList());
        Activator.createGameData(getRoleList(), getDifficultyEnum(), getCastModel());

        // パースペクティブを開く
        Util.openPerspective(Perspective.PERSPECTIVE_ID);

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
            this.isNextPageEvent = false;
            this.beforePageNum = this.currentPageNum;
            this.currentPageNum = num;

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
        // isNextPageEvent=trueのとき、まだ遷移先
        // isNextPageEvent=falseのとき、正常にとれる。

        return this.beforePageNum;
    }

//    /**
//     * ファイル名を指定し、エディタで開く
//     * @param fullpath ファイル名(Workspace上の仮想パス)
//     * @return 開けた場合にtrueを戻す
//     */
//    private boolean openEditor(String fullpath) {
////        IWorkbenchPage page = Util.getIWorkbenchPage();
////        File file = new File("warewolf.txt");
////        file.
////
////        if (file != null && file instanceof IFile) {
////            try {
////                return IDE.openEditor(page, (IFile) file, true) != null;
////            } catch (PartInitException e) {
////                e.printStackTrace();
////            }
////        }
//        return false;
//    }


}
