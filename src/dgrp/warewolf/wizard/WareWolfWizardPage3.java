package dgrp.warewolf.wizard;

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
import dgrp.warewolf.WareWolfCharacterFactory;
import dgrp.warewolf.WareWolfItemFactory;
import dgrp.warewolf.model.DifficultyEnum;
import dgrp.warewolf.model.Player;
import dgrp.warewolf.model.Character;


/**
 * ゲーム開始設定のウィザードページ3
 * キャラクター、役職、アイテムを決定する
 *
 * @author butsuryo
 *
 */
public class WareWolfWizardPage3 extends WizardPage {

    private WareWolfWizard wizard;

    private Label selectedPlayerNum;
    private Label selectedDifficulty;
    private Label selectedCastName;
    private TableViewer viewer;
    private WareWolfWizardPage2 page2;
    private Composite composite;
    private Composite parent;
    private ScrolledComposite sc;

    private boolean isFirst;

    /**
     * コンストラクタ
     * @param pageName
     */
    protected WareWolfWizardPage3(String pageName) {
        super(pageName);
        setPageComplete(false);
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        this.parent = parent;
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());

        setTitle("ゲーム開始設定(3/5) -- 最終確認");

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
        selectedPlayerNum = Util.createLabel(inputGroup, "　　　　　　　");

        // 選択された難易度
        selectedDifficulty = Util.createLabel(inputGroup, "　　　　　　　　　　　　");

        // 選択された配役
        selectedCastName = Util.createLabel(inputGroup, "　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　");

        Util.createLabel(inputGroup, StringUtil.EMPTY);
    }


    /**
     * 役職、キャラ、アイテムの抽選結果確認ビューを作成する
     * @param inputGroup
     */
    private void createViewArea(Composite inputGroup) {

        Util.createLabel(inputGroup, "役職、キャラクター、アイテムを確認してください。難易度いーじぃの場合はアイテムがセットされません。\r\n表のヘッダーを押すとその行の再抽選ができます。(アイテムは1と2のどちらを押しても全て再抽選になります。)");

        sc =  new ScrolledComposite(inputGroup, SWT.H_SCROLL |
                                             SWT.V_SCROLL);
        //Composite innerComposite = new Composite(inputGroup, SWT.NONE);
        sc.setLayout(new GridLayout());
        sc.setLayoutData(Util.createFillBothGridData());

        viewer = new TableViewer(sc, SWT.MULTI | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.FULL_SELECTION);
    }


    /**
     * ページに表示する情報をセットする
     *
     * このページ自体はWizard生成時に作られてしまうので、そのタイミングでは前画面までの選択値がすべてnull。
     * なので前画面を経てこのページに来たタイミングで値を取得＆セットする
     */
    private void setData() {

        // 選択された参加人数
        selectedPlayerNum.setText("参加人数：" + wizard.getPlayerNum());

        // 選択された難易度
        selectedDifficulty.setText("難易度：" + wizard.getDifficultyEnum().difficultyName);

        // 選択された配役
        selectedCastName.setText("配役：" + wizard.getCastName());

        // 入力されたプレイヤー一覧を取得
        ArrayList<String> playerList = page2.getPlayerList();

        // 役職
        ArrayList<Player> viewList = wizard.getRoleList();
        // 前画面から「次へ」でやってきた時は再抽選
        if (wizard.getBeforePageNum() == 2) {
            viewList = createViewList(playerList);

            // 再抽選後のデータを親ウィザードにセット
            wizard.setRoleList(viewList);
        }

        // テーブルビューワデータ作成
        createTable(viewList);
    }


    /**
     * テーブルビューワに表示するモデルのリストを作成する
     *
     * @param playerList 参加者のリスト
     * @return テーブルビューワに表示するモデルのリスト
     */
    private ArrayList<Player> createViewList(ArrayList<String> playerList){

        // 返却用の配列
        ArrayList<Player> viewList = new ArrayList<Player>();

        // 選択された配役
        ArrayList<Player> roleList = wizard.getCastModel().getRoleList();

        // 抽選キャラリストを取得
        ArrayList<Character> characterList = WareWolfCharacterFactory.createCharacterList(Integer.parseInt(wizard.getPlayerNum()), wizard.getUseCharacterType());

        // 参加者を抽選するためにランダムソートする
        Collections.shuffle(playerList);

        // アイテムの山札を作成
        WareWolfItemFactory.createStackItems(true);

        // 配役にキャラ、プレイヤー、アイテムを設定する
        for (int index=0; index<roleList.size(); index++) {
            Player role = roleList.get(index);

            // 役職に参加者を宛がう
            role.setPlayerName(playerList.get(index));

            // 役職にキャラを宛がう
            role.setCharacter(characterList.get(index));

            // いーじぃ以外はアイテムを抽選
            if (!DifficultyEnum.EASY.equals(wizard.getDifficultyEnum())) {
                role.setItem1(WareWolfItemFactory.getOneItem());
                role.setItem2(WareWolfItemFactory.getOneItem());
            }

            // 返却用の配列にセット
            viewList.add(role);
        }

        // 席順をランダムにするためにランダムソートする
        Collections.shuffle(viewList);
        return viewList;
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
        createColumn(table, 3, "アイテム1", (int)(tableFullWidth * 0.22));
        createColumn(table, 4, "アイテム2", (int)(tableFullWidth * 0.22));

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new WareWolfViewLabelProvider());

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

                    // アイテム
                    case 3 :
                    case 4 :
                        // アイテムの引き直し
                        remakeItem();
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
        @SuppressWarnings("unchecked")
        ArrayList<Player> currentList = (ArrayList<Player>) viewer.getInput();

        // 新データ
        ArrayList<Player> newList = new ArrayList<Player>();

        // 新抽選キャラクター
        ArrayList<Character> characterList = WareWolfCharacterFactory.createCharacterList(Integer.parseInt(wizard.getPlayerNum()), wizard.getUseCharacterType());

        for (int i=0; i<currentList.size(); i++) {
            Player role = currentList.get(i);
            // キャラクターだけ詰め直し
            role.setCharacter(characterList.get(i));
            newList.add(role);
        }

        // 新リストをセットして再描画
        setViewList(newList);
    }


    /**
     * 役職を振り直し、ビューを再描画する
     */
    private void remakeRole() {

        // 現在のデータ
        @SuppressWarnings("unchecked")
        ArrayList<Player> currentList = (ArrayList<Player>) viewer.getInput();

        // 新データ
        ArrayList<Player> newList = new ArrayList<Player>();

        // 役職一覧を取得して順番をシャッフル
        ArrayList<Player> roleList = wizard.getCastModel().getRoleList();
        Collections.shuffle(roleList);

        for (int i=0; i<currentList.size(); i++) {
            // 1行を取得
            Player role = currentList.get(i);

            // 役職だけ詰め直し
            Player newRole = new Player(roleList.get(i).getRole());
            newRole.setCharacter(role.getCharacter());
            newRole.setPlayerName(role.getPlayerName());
            newRole.setItem1(role.getItem1());
            newRole.setItem2(role.getItem2());
            newList.add(newRole);
        }

        // 新リストをセットして再描画
        setViewList(newList);
    }


    /**
     * アイテムを抽選し直し、ビューを再描画する
     */
    private void remakeItem() {

        // いーじぃはアイテム無視
        if (!DifficultyEnum.EASY.equals(wizard.getDifficultyEnum())) {

            // 現在のデータ
            @SuppressWarnings("unchecked")
            ArrayList<Player> currentList = (ArrayList<Player>) viewer.getInput();

            // 新データ
            ArrayList<Player> newList = new ArrayList<Player>();

            // アイテムの山札を再作成
            WareWolfItemFactory.createStackItems(true);

            for (int i=0; i<currentList.size(); i++) {
                Player role = currentList.get(i);
                // アイテムだけ詰め直し
                role.setItem1(WareWolfItemFactory.getOneItem());
                role.setItem2(WareWolfItemFactory.getOneItem());

                // アイテム引き直し履歴も初期化
                role.setItemRedraw(false);

                newList.add(role);
            }

            // 新リストをセットして再描画
            setViewList(newList);
        }
    }

    private void setViewList(ArrayList<Player> newList) {
        // 新しいリストで表示を更新
        viewer.setInput(newList);

        // 親ウィザードにセット
        wizard.setRoleList(newList);
    }



    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    public void setVisible(boolean visible) {

        // 現在ページと前ページを設定
        wizard = (WareWolfWizard) getWizard();
        wizard.setCurrentPageNum(3);

        page2 = (WareWolfWizardPage2)wizard.getPreviousPage(this);


        // 初回時以外は再描画
        if (!this.isFirst) {
            this.composite.dispose();
            this.createControl(this.parent);
            this.parent.layout();
        }
        this.isFirst = false;

        // 難易度によってメッセージを変える
        String message = "";

        if (DifficultyEnum.EASY == wizard.getDifficultyEnum()) {
            message = "配役を確認の上、キャラクターを決定してください。";
        }else{
            message = "配役を確認の上、キャラクター、アイテムを決定してください。";
        }
        setDescription(message);

        // アクティブになった段階で表示値をセット
        setData();
        setPageComplete(true);
        super.setVisible(visible);
    }

}
