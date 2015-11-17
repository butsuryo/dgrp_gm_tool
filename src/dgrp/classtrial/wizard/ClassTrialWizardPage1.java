package dgrp.classtrial.wizard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import dgrp.StringUtil;
import dgrp.Util;


/**
 * ゲーム開始設定のウィザードページ1
 * 参加人数、参加者名を設定する
 *
 * @author butsuryo
 *
 */
public class ClassTrialWizardPage1 extends WizardPage {

    private Combo playerNumCombo;
    private Text playerText;

    private boolean isFirst;

    /**
     * コンストラクタ
     * @param pageName
     */
    protected ClassTrialWizardPage1(String pageName) {
        super(pageName);
        setDescription("参加人数を選択の上、参加者名を改行区切りで入力してください。");
        setPageComplete(false);

        isFirst = true;
    }

    /**
     * 現在選択されている参加人数を取得する
     * @return 現在選択されている参加人数
     */
    public String getPlayerNum(){
        return playerNumCombo.getItem(playerNumCombo.getSelectionIndex());
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite composite = Util.createGridComposite(parent, 0);

        setTitle("ゲーム開始設定(1/3) -- 参加者設定");

        // 人数設定
        createSelectPlayerCountArea(composite);

        // 参加者設定
        createInputPlayerArea(composite);


        setControl(composite);
    }

    /**
     * 人数選択エリア作成
     * @param composite
     */
    private void createSelectPlayerCountArea(Composite inputGroup){

        Util.createLabel(inputGroup, "ゲームに参加する人数を選択してください。") ;

        // ドロップダウン
        playerNumCombo = new Combo(inputGroup, SWT.READ_ONLY);
        setPlaylerNumber();
        playerNumCombo.addModifyListener(e->validate());

        Util.createLabel(inputGroup, StringUtil.EMPTY);
    }

    /**
     * 参加者入力エリアの作成
     * @param composite
     */
    private void createInputPlayerArea(Composite composite){

        setTitle("ゲーム開始設定(1/3) -- 参加者選択");

        Group inputGroup = new Group(composite, SWT.NONE);
        inputGroup.setText("参加者設定");
        inputGroup.setLayout(new GridLayout());

        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        inputGroup.setLayoutData(gridData);

        /** 参加者入力 **/
        Util.createLabel(inputGroup, "ゲームに参加するメンバーの名前を改行区切りで入力してください。");

        // TODO 横幅はフル要らない
        playerText = new Text(inputGroup,SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
        playerText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));
        GridData gridData2 = new GridData();
        gridData2.horizontalAlignment = GridData.FILL;
        gridData2.verticalAlignment = GridData.FILL;
        gridData2.grabExcessHorizontalSpace = true;
        gridData2.grabExcessVerticalSpace = true;
        playerText.setLayoutData(gridData2);

        // リスナーをセット
        playerText.addModifyListener(e->validate());
    }

    /**
     * プレイヤー人数選択リストに選択肢の人数を表示する
     */
    private void setPlaylerNumber(){

        // 人数をリストに追加
        ArrayList<String> playerNumberArray = getPlayerNumber();

        for(String playerNumber : playerNumberArray){
            playerNumCombo.add(playerNumber);
        }
        playerNumCombo.select(0); // 最初のアイテムを選択状態にする

    }

    /**
     * ゲームの参加人数の選択肢を配列で取得する
     * @return ゲームの参加人数の選択肢(文字列配列)
     */
    private ArrayList<String> getPlayerNumber(){

        ArrayList<String> playerNumberArray = new ArrayList<String>();

        // 3人以上9人以下
        for(int i=3; i<=9; i++){
            playerNumberArray.add(Integer.toString(i));
        }

        return playerNumberArray;
    }

    /**
     * ページOKにして良いかどうかの入力チェック
     * OKであれば「次へ」活性化、NGであれば非活性化＆エラーメッセージを設定
     */
    private void validate() {

        if ( !StringUtil.isEmpty(playerText.getText()) ){

            // 書かれている行数を取得
            int playerNum = getPlayerList().size();

            // 選択した人数
            int selectedPlayerNum = Integer.parseInt(getPlayerNum());

            // 重複要素があればエラー
            if (playerNum != getInputPlayerDistinctCount()) {
                setPageComplete(false);
                setErrorMessage("同じ参加者が2つ以上入力されています。参加者名は被らないように入力してください。");

            // 前のページで選択された人数と異なっていたらエラー
            } else if (playerNum != selectedPlayerNum) {
                setPageComplete(false);
                setErrorMessage("現在入力されている参加人数が、選択した参加人数【"
                        + String.valueOf(selectedPlayerNum)  + "人】と異なります。");

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
     * 入力されている参加者一覧をStringのリストで取得する
     * @return 入力されている参加者一覧のリスト
     */
    ArrayList<String> getPlayerList() {
        return StringUtil.getRowList(playerText.getText());
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
        ClassTrialWizard wizard = (ClassTrialWizard) getWizard();
        if (isFirst) {
            wizard.setCurrentPageNumFirst(1);
            isFirst = false;
        } else {
            wizard.setCurrentPageNum(1);
        }

        // ページがアクティブになったら再チェック(前画面で設定変更⇒次へ　を押された時に通る)
        validate();

        super.setVisible(visible);
    }

}
