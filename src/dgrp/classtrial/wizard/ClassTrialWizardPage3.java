package dgrp.classtrial.wizard;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import dgrp.Util;
import dgrp.classtrial.AnnounceFactory;
import dgrp.classtrial.model.Cast;
import dgrp.classtrial.model.Player;


/**
 * ゲーム開始設定のウィザードページ3
 * アナウンスファイルを出力したことを通知する
 *
 * @author butsuryo
 *
 */
public class ClassTrialWizardPage3 extends WizardPage {

    private Cast cast;
    private ArrayList<Player> playerList;
    public boolean isFinishOutputFile;
    private Text announceText;

    // プラグインのカレントディレクトリに出力
    private String path = "classTrial.txt";

    /**
     * コンストラクタ
     * @param pageName
     */
    protected ClassTrialWizardPage3(String pageName) {
        super(pageName);
        setDescription("");

        setPageComplete(false);
        isFinishOutputFile = false;
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite composite =  Util.createGridComposite(parent, 0);

        setTitle("ゲーム開始設定(3/3) -- アナウンスリスト出力完了");

        Util.createLabel(composite, "①：役職が決定しました。以下の告知を全体に行ってください。") ;

        // ゲーム設定アナウンス
        announceText = new Text(composite,SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
        announceText.setLayoutData(Util.createFillBothGridData());

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
     * アナウンスリストを出力する
     */
    private void outputAnnounceList(){

        // 告知コピペ文作成
        ArrayList<String> announceList = AnnounceFactory.createStartBeforeAnnounce(playerList, cast);

        // とりあえずファイルに出力してみる
        Util.outputFileStrList(announceList, path);
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    public void setVisible(boolean visible) {

        // ページ遷移を記録
        ClassTrialWizard wizard = (ClassTrialWizard) getWizard();
        wizard.setCurrentPageNum(3);

        // 配役、取得
        cast = wizard.getCast();
        playerList = wizard.getPlayerList();

        // 前画面から遷移してきた場合、リスト出力
        if (wizard.getBeforePageNum() == 2 && cast != null && playerList != null) {

            // リスト出力
            outputAnnounceList();
            isFinishOutputFile = true;

            // 配役決定アナウンス文をテキストにセットする
            announceText.setText(AnnounceFactory.getGameSettingCompleteAnnounce(cast));
        }

        setPageComplete(true);
        super.setVisible(visible);
    }


}
