package dgrp.classtrial.wizard;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import dgrp.StringUtil;
import dgrp.Util;
import dgrp.classtrial.AnnounceFactory;
import dgrp.classtrial.model.Player;



/**
 * ゲーム開始設定のウィザードページ7
 * 投票結果アナウンス文作成
 *
 * @author butsuryo
 *
 */
public class ClassTrialWizardPage7 extends WizardPage {

    private ClassTrialWizard wizard;
    private ArrayList<Player> playerList;
    private Text voteResultLabel;
    private Composite checkBoxGroup ;
    private Composite composite;

    private ArrayList<Player> voteList;


    /**
     * コンストラクタ
     * @param pageName
     */
    protected ClassTrialWizardPage7(String pageName) {
        super(pageName);
        setDescription("議論終了後、最多得票者をチェックして結果を作成し、告知してください。\r\n最多得票者が同票で複数人いる場合は、全てチェックを入れてください。");

        // 初期化
        voteList = new ArrayList<Player>();
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        composite = Util.createGridComposite(parent, 0);

        setTitle("ゲーム開始(4/4) -- 投票");


        // 投票結果表示用
        Composite voteResultGroup = Util.createGridComposite(composite, 4);
        Label label = Util.createLabel(voteResultGroup, "最多得票者：");
        label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));

        // 最多得票者チェックボックス
        checkBoxGroup = Util.createGridComposite(voteResultGroup, 1);
        checkBoxGroup.setLayoutData(Util.createFillBothGridData());

        // 結果表示用のラベル
        voteResultLabel = new Text(voteResultGroup, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
        Util.createCopyLink(voteResultGroup, voteResultLabel);

        setControl(composite);
    }

    /**
     * 最多得票者選択のチェックボックスを作成する
     */
    private void createCheckBox() {

        // 「前へ」⇒「次へ」でページ再遷移した場合を考慮し、一度チェックボックスを全て破棄
        Control[] buttons = checkBoxGroup.getChildren();
        for (int i=0; i<buttons.length; i++) {
            buttons[i].dispose();
        }

        // 結果表示ラベルと選択済みリストも初期化
        voteResultLabel.setText(StringUtil.EMPTY);
        voteList = new ArrayList<Player>();

        composite.layout();

        // 欠席者は投票対象から除く
        for (Player player : wizard.getCast().getNonAbsentList()) {

            // チェックボックス作成
            Button button = new Button(checkBoxGroup, SWT.CHECK);
            button.setText(player.getCharacter().getCharacterName());

            // クリックされた時のイベント
            button.addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected(SelectionEvent e){
                      // no operation.
                  }

                public void widgetSelected(SelectionEvent e){
                    Button selectedButton = (Button)e.widget;

                    // 選択された人の参加者オブジェクトをリストに追加
                    if(selectedButton.getSelection()){

                        // キャラクター名から参加者オブジェクトを特定
                        Player player = getRoleByCharacterName(selectedButton.getText());

                        // 最多投票者リストに追加
                        voteList.add(player);

                    // 選択解除された人はリストから削除
                    } else {
                        voteList.remove(getRoleByCharacterName(selectedButton.getText()));
                    }

                    // 勝利条件判定＆結果表示
                    voteResultLabel.setText(AnnounceFactory.getVoteResult(wizard.getCast(), voteList));
                    composite.layout();
                }
            });

        }
        // 再描画
        composite.layout();
    }

    /**
     * キャラクター名から参加者オブジェクトを取得する
     * @param characterName キャラクター名
     * @return 参加者オブジェクト
     */
    private Player getRoleByCharacterName(String characterName) {

        for (Player player : playerList) {
            if (player.getCharacter().getCharacterName().equals(characterName)) {
                return player;
            }
        }
        return null;
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    public void setVisible(boolean visible) {

        // ページ遷移を記録
        wizard = (ClassTrialWizard)getWizard();
        wizard.setCurrentPageNum(7);

        // 参加者オブジェクト取得
        playerList = wizard.getPlayerList();

        // 前画面から来た場合のみチェックボックスを再作成
        if (playerList != null && wizard.getBeforePageNum() == 6) {
            createCheckBox();
        }

        setPageComplete(true);
        super.setVisible(visible);
    }
}
