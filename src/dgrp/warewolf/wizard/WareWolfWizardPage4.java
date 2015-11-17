package dgrp.warewolf.wizard;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import dgrp.StringUtil;
import dgrp.Util;
import dgrp.warewolf.WareWolfAnnounceFactory;
import dgrp.warewolf.model.Cast;
import dgrp.warewolf.model.DifficultyEnum;
import dgrp.warewolf.model.Player;


/**
 * ゲーム開始設定のウィザードページ4
 * ゲーム開始アナウンスファイルを出力し、プレイヤーへの告知を始める
 *
 * @author butsuryo
 */
public class WareWolfWizardPage4 extends WizardPage {

    // ゲーム開始用アナウンス文はプラグインのカレントディレクトリに出力
    private String path = "warewolf.txt";

    private WareWolfWizard wizard;

    private Text announceText;
    private Composite composite;
    private Composite parent;

    private boolean isFirst;


    /**
     * コンストラクタ
     * @param pageName
     */
    protected WareWolfWizardPage4(String pageName) {
        super(pageName);

        String message = "";
        setDescription(message);

        this.isFirst = true;

        setPageComplete(false);
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {

        Composite composite =  Util.createGridComposite(parent, 0);

        setTitle("ゲーム開始設定(4/5) -- アナウンスリスト出力完了");

        Util.createLabel(composite, "①：役職が決定しました。以下の告知を全体に行ってください。") ;

        // ゲーム設定アナウンス
        this.announceText = new Text(composite,SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
        this.announceText.setLayoutData(Util.createFillBothGridData());

        Util.createEmptyLineLabel(composite);
        Util.createLabel(composite, "②：参加者に役職案内を行ってください。(以下のボタンからファイルを開き、コピペして、各プレイヤー個人に案内)") ;

        // ファイルを開くボタン
        Button button = new Button(composite, SWT.PUSH);
        button.setText("役職決定文ファイルを開く");
        button.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                Util.fileOpen(path);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // do nothing
            }
        });

        Util.createEmptyLineLabel(composite);
        Util.createLabel(composite, "③：全員への案内が完了したら、「次へ」でゲームを進めてください。");

        setPageComplete(true);
        setControl(composite);
    }

    /**
     * ゲーム開始アナウンス用ファイルを出力する
     */
    private void outputGameStartFile() {

        // 選択された配役
        Cast cast = wizard.getCastModel();

        // 選択された難易度
        DifficultyEnum difficulty = wizard.getDifficultyEnum();

        // 告知コピペ文作成
        ArrayList<String> announceList = WareWolfAnnounceFactory.createStartBeforeAnnounce(wizard.getRoleList(), cast, difficulty);

        // 全てのゲーム設定が完了したら、コピペ用のビューを表示させる
        System.out.println("");

        // とりあえずファイルに出力してみる
        Util.outputFileStrList(announceList, path);
    }

    /**
     * 配役決定アナウンス文をテキストにセットする
     */
    private void setAnnounceText() {
        String announce = "配役が決定しました。" + StringUtil.LINE_CODE;
        announce += "●配役：" + wizard.getCastName() + StringUtil.LINE_CODE;
        announce += "●参加者：" + StringUtil.LINE_CODE;
        for (Player role : wizard.getRoleList()) {
            announce += StringUtil.FULL_WIDTH_SPACE + role.getCharacter().getCharacterName() + StringUtil.LINE_CODE;
        }
        announce += StringUtil.LINE_CODE + "ただいまよりキャラクター・役職の告知、および役職実行を行います。\r\n個別skypeでの連絡をお待ちください。";
        this.announceText.setText(announce);
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    public void setVisible(boolean visible) {

        // 前画面までを取得
        wizard = (WareWolfWizard)getWizard();
        wizard.setCurrentPageNum(4);

        // 初回時以外は再描画
        if (!this.isFirst && composite != null) {
            this.composite.dispose();
            this.createControl(this.parent);
            this.parent.layout();
        }

        this.isFirst = false;

        // 前画面から遷移してきた場合、リスト出力
        if (wizard.getBeforePageNum() == 3) {

            // リスト出力
            this.outputGameStartFile();

            // テキストセット
            this.setAnnounceText();
        }

        setPageComplete(true);
        super.setVisible(visible);
    }

}
