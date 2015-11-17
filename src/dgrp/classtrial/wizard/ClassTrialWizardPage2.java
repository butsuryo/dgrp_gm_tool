package dgrp.classtrial.wizard;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import dgrp.StringUtil;
import dgrp.Util;
import dgrp.classtrial.CharacterFactory;
import dgrp.classtrial.model.Cast;
import dgrp.classtrial.model.Player;
import dgrp.classtrial.model.Character;
import dgrp.classtrial.model.RoleEnum;



/**
 * ゲーム開始設定のウィザードページ2
 * 参加者を決定する
 *
 * @author butsuryo
 *
 */
public class ClassTrialWizardPage2 extends WizardPage {

    private ScrolledComposite sc;
    private TableViewer viewer;
    private ClassTrialWizardPage1 page1;
    private Composite composite;
    private Composite parent;

    private String playerNum;

    private Cast castModel;
    private Label selectedPlayerNumLabel;
    private Label castNameLabel;

    private boolean isFirst;
    private ClassTrialWizard wizard;


    /**
     * コンストラクタ
     * @param pageName
     */
    protected ClassTrialWizardPage2(String pageName) {
        super(pageName);
        setDescription("配役を確認してください。");
        setPageComplete(false);
        isFirst = true;
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        this.parent = parent;
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());

        setTitle("ゲーム開始設定(2/3) -- 配役確認");

        composite.setLayoutData(Util.createFillBothGridData());

        // 設定完了値表示エリア
        createInformationArea(composite);

        // 役職・キャラ・アイテム抽選結果確認ビュー
        createViewArea(composite);

        setControl(composite);
    }

    /**
     * 前ページまでで設定完了した値の表示エリアを作成する
     * @param composite
     */
    private void createInformationArea(Composite inputGroup){

        // 選択された参加人数
        selectedPlayerNumLabel = Util.createLabel(inputGroup, "　　　　　　　");

        // 配役
        castNameLabel = Util.createLabel(inputGroup, "　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　");

        Util.createLabel(inputGroup, StringUtil.EMPTY);
    }

    /**
     * 役職、キャラ、アイテムの抽選結果確認ビューを作成する
     * @param inputGroup
     */
    private void createViewArea(Composite inputGroup) {

        Util.createLabel(inputGroup, "参加者名、キャラクター名、役職を確認してください。\r\nヘッダーの「キャラクター」「役職」を押すとそれぞれの行の再抽選ができます。");

        sc =  new ScrolledComposite(inputGroup, SWT.H_SCROLL | SWT.V_SCROLL);
        sc.setLayout(new GridLayout());
        sc.setLayoutData(Util.createFillBothGridData());

        viewer = new TableViewer(sc, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
    }

    /**
     * ページに表示する情報をセットする
     *
     * このページ自体はWizard生成時に作られてしまうので、そのタイミングでは前画面までの選択値がすべてnull。
     * なので前画面を経てこのページに来たタイミングで値を取得＆セットする
     *
     * @param page1 1ページ目のインスタンス
     * @param page2 2ページ目のインスタンス
     */
    private void setData() {

        // 入力されたプレイヤー一覧を取得
        ArrayList<String> playerList = page1.getPlayerList();

        // 役職の抽選
        if (wizard.getBeforePageNum() == 1) {
            castModel = wizard.createCast(Integer.parseInt(playerNum), playerList);
        }

        // テーブルビューワデータ作成
        createTable();
    }

    /**
     * テーブルビューワを作成する
     *
     */
    private void createTable() {

        Table table = viewer.getTable();

        // ウィザードウィンドウの横幅を取得
        int tableFullWidth =( composite.getClientArea().width - table.getBorderWidth() * 2)-10;

        createColumn(table, 0, "参加者名", (int)(tableFullWidth * 0.13));
        createColumn(table, 1, "キャラクター", (int)(tableFullWidth * 0.25));
        createColumn(table, 2, "役職", (int)(tableFullWidth * 0.16));

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new ClassTrialViewLabelProvider());

        table.setSize(tableFullWidth, 280);
        sc.setContent(table);
        viewer.setInput(wizard.getPlayerList());
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

        // ヘッダーが選択されたら対応行を抽選し直し、再表示する
        column.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                TableColumn selectedColumn = (TableColumn) e.widget;
                int index = (Integer) selectedColumn.getData("index");

                switch(index) {

                    // キャラクター
                    case 1 :
                        // キャラクターの引き直し
                        remakeCharacter();
                        break;

                    // 役職
                    case 2 :
                        // 役職の引き直し
                        remakeRole();
                        break;

                    // それ以外は何もしない
                    default :
                        break;
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // do nothing.
            }
        });
    }

    /**
     * キャラクターを抽選し直し、ビューを再描画する
     */
    private void remakeCharacter() {

        // 現在のデータ
        ArrayList<Player> currentList = wizard.getPlayerList();

        // 新抽選キャラクター
        ArrayList<Character> characterList = CharacterFactory.createCharacterList(currentList.size(), 0);  // TODO キャラ1,2から選べるけど今んとこ固定

        // キャラクターだけ詰め直し
        for (int i=0; i<currentList.size(); i++) {
            currentList.get(i).setCharacter(characterList.get(i));
        }

        // 新リストをセットして再描画
        viewer.setInput(currentList);
    }


    /**
     * 役職を振り直し、ビューを再描画する
     */
    private void remakeRole() {

        // 現在のデータ
        ArrayList<Player> currentList = wizard.getPlayerList();

        // 役職だけをシャッフルしたデータ
        ArrayList<RoleEnum> shuffleList = castModel.getRoleEnumList();
        Collections.shuffle(shuffleList);

        // 「役職」「交換前役職」だけ詰めなおし
        for (int i=0; i<currentList.size(); i++) {
            Player currentRole = currentList.get(i);
            currentRole.setRole(shuffleList.get(i));
            currentRole.setAfterTradeRole(null);
        }

        // 新リストをセットして再描画
        viewer.setInput(currentList);
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    public void setVisible(boolean visible) {

        // ページ遷移を記録
        wizard = (ClassTrialWizard) getWizard();
        wizard.setCurrentPageNum(2);

        // 前画面までを取得
        page1 = (ClassTrialWizardPage1) wizard.getPreviousPage(this);

        // 選択された人数を取得
        playerNum = page1.getPlayerNum();

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
