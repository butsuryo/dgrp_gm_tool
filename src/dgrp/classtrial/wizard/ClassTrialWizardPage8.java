package dgrp.classtrial.wizard;

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
import dgrp.classtrial.model.Log;



/**
 * ゲーム開始設定のウィザードページ8
 * ログ整理
 *
 * @author butsuryo
 *
 */
public class ClassTrialWizardPage8 extends WizardPage {

    private ClassTrialWizard wizard;
    private Text logText;


    /**
     * コンストラクタ
     * @param pageName
     */
    protected ClassTrialWizardPage8(String pageName) {
        super(pageName);
        setDescription("ゲームログを入力してください。");
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite composite = Util.createGridComposite(parent, 0);

        setTitle("ゲーム終了(1/1) -- ログ整理（開発中）");

        Util.createLabel(composite, "①：管理ページからログのCSVをDLし、以下にゲーム終了ログをCSV形式で貼りつけてください。") ;

        Button fileSelectBtn = new Button(composite, SWT.PUSH);
        fileSelectBtn.setText("CSVファイルから開く");
        fileSelectBtn.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                // 一度テキストボックスを初期化
                logText.setText(StringUtil.EMPTY);

                // ファイル選択ダイアログを開く
                String extensions[] = {"*.csv"};
                String path = Util.openFileSelectDialogWithExtension(extensions);

                // ファイル内容の読み取り
                String str = Util.inputSJisFile(path);

                // テキストボックスに出力
                logText.setText(str);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // no operation
            }

        });

        // ログ貼りつけエリア
        this.logText = new Text(composite,SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
        this.logText.setLayoutData(Util.createFillBothGridData());

        // ボタンを押されたら文字を整形
        Button button = new Button(composite, SWT.PUSH);
        button.setText("ログを整形しファイルに出力");
        button.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                if ( !StringUtil.isEmpty(logText.getText()) ){
                    // 整形
                    ArrayList<Log> formatedLog = formatLog(logText.getText());


                    // 出力
                    outputFile(formatedLog);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // no operation.
            }
        });

        setControl(composite);
    }

    /**
     * 入力されたログ情報を元にログを整形する
     *
     * @param text ログ
     * @return 整形したログデータ
     */
    private ArrayList<Log> formatLog(String text) {

        // 改行コードで分割
        String row[] = text.split(StringUtil.LINE_CODE);

        // 時間、名前、発言をタブ区切りの文字列で保持するようにする
        ArrayList<Log> logRowList = new ArrayList<Log>();

        // 一行目はヘッダ扱いで除く
        for (int i=1; i<row.length; i++) {

            // ダブルクォートを除去(1文字目、最終文字目、",")
            String str = row[i].substring(1, row[i].length() -1);
            str = str.replace("\",\"", ",");

            // カンマで要素を分割(logno,name,message,ip,color,user_option,serv_option,logtime)
            String elements[] = str.split(StringUtil.COMMA);

            // システムログは除く
            if ("NOTIFY".equals(elements[3])) {
                continue;
            }

            String name = elements[1];
            String message = elements[2];
            String color = elements[4];

            // オプションを&で分割（bold=false&italic=false&strike=false&size=normal）の形
            String options[] = elements[5].split(StringUtil.And);

            // bold,italic,strike,sizeの順
            boolean isBold = this.getOptionValue(options[0]);
            boolean isItalic = this.getOptionValue(options[1]);
            boolean isStrike = this.getOptionValue(options[2]);
            String size = options[3].split(StringUtil.EQUAL)[1];

            String timestamp = elements[7];

            logRowList.add(new Log(timestamp, name, message, color, isBold, isItalic, isStrike, size));
        }

        return logRowList;
    }

    private boolean getOptionValue(String keyValue) {
        // イコールで分割
        String option[] = keyValue.split(StringUtil.EQUAL);

        // key=valueの形なので二番目を返す
        if ("true".equals(option[1])) {
            return true;
        }
        return false;
    }

    private void outputFile(ArrayList<Log> formatedLog) {
        // TODO 自動生成されたメソッド・スタブ

    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    public void setVisible(boolean visible) {

        // ページ遷移を記録
        this.wizard = (ClassTrialWizard)getWizard();
        wizard.setCurrentPageNum(8);

        setPageComplete(true);
        super.setVisible(visible);
    }


}
