package dgrp.warewolf.wizard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import dgrp.StringUtil;
import dgrp.Util;

/**
 * ゲーム開始設定のウィザードページ2
 * 参加者を決定する
 *
 * @author butsuryo
 *
 */
public class WareWolfWizardPage2 extends WizardPage {

    private WareWolfWizard wizard;

    // 参加者入力欄
    private Text playerText;

    /**
     * コンストラクタ
     * @param pageName
     */
    protected WareWolfWizardPage2(String pageName) {
        super(pageName);
        setDescription("参加者を入力してください。(前画面で選択した人数と異なるとエラーになります。)");
        setPageComplete(false);
    }

    /**
     * 入力されている参加者一覧をStringのリストで取得する
     * @return 入力されている参加者一覧のリスト
     */
    public ArrayList<String> getPlayerList() {
        return StringUtil.getRowList(playerText.getText());
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());

        // 参加者入力エリアを作成
        createInputPlayerArea(composite);

        setControl(composite);
    }


    /**
     * 参加者入力エリアの作成
     * @param composite
     */
    private void createInputPlayerArea(Composite composite){

        setTitle("ゲーム開始設定(2/5) -- 参加者選択");

        Group inputGroup = new Group(composite, SWT.NONE);
        inputGroup.setText("参加者設定");
        inputGroup.setLayout(new GridLayout());
        inputGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        /** 参加者入力 **/
        Util.createLabel(inputGroup, "ゲームに参加する参加者を改行区切りで入力してください。");

        playerText = new Text(inputGroup,SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
        playerText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        // リスナーをセット
        playerText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {

                // 入力値に変化があれば逐次入力チェック
                validate();
            }
        });
    }


    /**
     * ページOKにして良いかどうかの入力チェック
     * OKであれば「次へ」活性化、NGであれば非活性化＆エラーメッセージを設定
     */
    private void validate() {

        // 前画面のインスタンスを取得
        WareWolfWizard wiz = (WareWolfWizard)getWizard();
        WareWolfWizardPage1 page1 = (WareWolfWizardPage1)wiz.getPreviousPage(this);

        if ( !StringUtil.isEmpty(playerText.getText()) ){

            // 書かれている行数を取得
            int playerNum = getPlayerList().size();

            // 前画面で選択した人数
            int selectedPlayerNum = Integer.parseInt(wizard.getPlayerNum());

            // 重複要素があればエラー
            if (playerNum != getInputPlayerDistinctCount()) {
                setPageComplete(false);
                setErrorMessage("同じ参加者が2つ以上入力されています。参加者名は被らないように入力してください。");

            // 前のページで選択された人数と異なっていたらエラー
            } else if (playerNum != selectedPlayerNum) {
                setPageComplete(false);
                setErrorMessage("現在入力されている参加人数が、前ページで選択した参加人数【"
                        + String.valueOf(selectedPlayerNum)  + "人】と異なります。");

            // GM参加を選択したのにGMがいなかったらエラー
            } else if (!checkGm(page1)) {
                setPageComplete(false);
                setErrorMessage("「GM」が参加者に含まれていません。前のページで「GMを初日犠牲者とする」のチェックを外すか、参加者に「GM」を含めてください。");

            // エラーがなければ次のページに進んでOK
            } else {
                setErrorMessage(null);
                setMessage("入力した参加者が正しければ「次へ」ボタンを押してください。");
                setPageComplete(true);
            }

        // 入力されてなければ次へ進ませない
        }else{
            setPageComplete(false);
        }
    }


    /**
     * GM設定がOKかどうかをチェックする
     * @return
     */
    private boolean checkGm(WareWolfWizardPage1 page1) {

        // 前のページでGMを選択したかどうか
        if (!page1.isContainGm()){
            // GM含む設定になっていなければ問題なし
            return true;
        }

        ArrayList<String> allPlayerList = getPlayerList();

        for (String player : allPlayerList){

            // 現在テキストにGMが含まれているかどうか(全角半角の表記ゆれも含めて比較)
            if (player.equals("GM") || player.equals("ＧＭ")){
                return true;
            }
        }

        // 「GM」が引っかからなかった場合はfalse
        return false;
    }


    /**
     * 入力された参加者を重複削除し、有効行数を返却する
     * @return 入力された参加者を重複削除してカウントした行数
     */
    private int getInputPlayerDistinctCount() {
        Set<String> set = new HashSet<String>();
        set.addAll(getPlayerList());
        ArrayList<String> uniqueList = new ArrayList<String>();
        uniqueList.addAll(set);
        return uniqueList.size();
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    @Override
    public void setVisible(boolean visible) {

        // 現在ページと前ページを設定
        wizard = (WareWolfWizard) getWizard();
        wizard.setCurrentPageNum(2);

        // ページがアクティブになったら再チェック(前画面で設定変更⇒次へ　を押された時に通る)
        validate();

        super.setVisible(visible);
    }
}
