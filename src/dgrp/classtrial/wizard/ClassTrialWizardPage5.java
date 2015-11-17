package dgrp.classtrial.wizard;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import dgrp.Util;
import dgrp.classtrial.model.Player;



/**
 * ゲーム開始設定のウィザードページ5
 * 役職実行後の参加者情報を確認する
 *
 * @author butsuryo
 *
 */
public class ClassTrialWizardPage5 extends WizardPage {

    private ClassTrialWizard wizard;

    private ScrolledComposite sc;
    private TableViewer viewer;
    private Composite composite;

    private boolean isFirst;
    private Composite parent;


    /**
     * コンストラクタ
     * @param pageName
     */
    protected ClassTrialWizardPage5(String pageName) {
        super(pageName);
        setDescription("");
        setPageComplete(false);
        isFirst = true;
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {

        setTitle("ゲーム開始(2/4) -- 役職実行結果確認");

        this.parent = parent;
        composite = Util.createGridComposite(parent, 0);
        composite.setLayoutData(Util.createFillBothGridData());

        // 役職・キャラ確認ビュー
        createViewArea(composite);

        Text anoounceText = new Text(composite,SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
        anoounceText.setLayoutData(Util.createFillBothGridData());
        anoounceText.setText("http://chat.kanichat.com/chat?roomid=classTrial\r\nお待たせしました。開廷準備が完了しましたのでゲームを開始します。全員、上記URLのチャットに移動してください。\r\n●チャット利用方法\r\n①URLにアクセス\r\n②機能/設定タブの「アイコン設定」＞「アイコンを追加」から、自分のキャラクターのアイコンをアップロード\r\n③アイコンの設定ウィンドウを閉じ、入室/発言タブにキャラクター名を入力して「入室する」\r\n④入室が完了したら、入室/発言タブから発言ができます。");

        setControl(composite);
    }

    /**
     * 役職、キャラ、アイテムの抽選結果確認ビューを作成する
     * @param inputGroup
     */
    private void createViewArea(Composite inputGroup) {

        Util.createLabel(inputGroup, "参加者名、キャラクター名、役職を確認してください。");

        sc =  new ScrolledComposite(inputGroup, SWT.H_SCROLL |
                                             SWT.V_SCROLL);
        sc.setLayout(new GridLayout());
        sc.setLayoutData(Util.createFillBothGridData());

        viewer = new TableViewer(sc, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
    }

    /**
     * ページに表示する情報をセットする
     *
     * このページ自体はWizard生成時に作られてしまうので、そのタイミングでは前画面までの選択値がすべてnull。
     * なので前画面を経てこのページに来たタイミングで値を取得＆セットする
     */
    private void setData() {

        ArrayList<Player> playerList;

        if (wizard.getPlayerList() == null) {
            playerList = new ArrayList<Player>();
        } else {
            playerList = wizard.getPlayerList();
        }
        // テーブルビューワデータ作成
        createTable(playerList);
    }

    /**
     * テーブルビューワを作成する
     *
     * @param viewList 表示するモデルのリスト
     */
    private void createTable(ArrayList<Player> viewList) {

        Table table = viewer.getTable();

        // ウィザードウィンドウの横幅を取得
        int tableFullWidth =( composite.getClientArea().width - table.getBorderWidth() * 2)-10;

        createColumn(table, 0, "参加者名", (int)(tableFullWidth * 0.13));
        createColumn(table, 1, "キャラクター", (int)(tableFullWidth * 0.25));
        createColumn(table, 2, "役職", (int)(tableFullWidth * 0.16));
        createColumn(table, 3, "交換前役職", (int)(tableFullWidth * 0.16));

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new ClassTrialViewLabelProvider());

        table.setSize(tableFullWidth, 280);
        sc.setContent(table);
        viewer.setInput(viewList);
    }

    /**
     * テーブルカラムを作成する
     * カラム押下時には対応した行のデータの再抽選を行い、ビューを再描画する
     *
     * @param table テーブル
     * @param columnIndex カラム番号
     * @param text 表示文言
     * @param width 横幅
     */
    private void createColumn(Table table, int columnIndex, String text, int width){
        TableColumn column = new TableColumn(table, 0, columnIndex);
        column.setText(text);
        column.setData("index", columnIndex);
        column.setWidth(width);
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    public void setVisible(boolean visible) {

        // ページ遷移を記録
        wizard = (ClassTrialWizard)getWizard();
        wizard.setCurrentPageNum(5);

        // 初回時以外は再描画
        if (!isFirst) {
            composite.dispose();
            createControl(parent);
            parent.layout();
        }
        isFirst = false;

        setData();
        setPageComplete(true);
        super.setVisible(visible);
    }

}
