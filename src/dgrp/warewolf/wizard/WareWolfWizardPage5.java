package dgrp.warewolf.wizard;

import java.util.ArrayList;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchActionConstants;

import dgrp.StringUtil;
import dgrp.Util;
import dgrp.warewolf.action.ItemRedrawPopupMenuAction;
import dgrp.warewolf.model.DifficultyEnum;
import dgrp.warewolf.model.Player;


/**
 * ゲーム開始設定のウィザードページ5
 * アイテムの引き直しを行う
 *
 * @author butsuryo
 */
public class WareWolfWizardPage5 extends WizardPage {

    private WareWolfWizard wizard;

    private StackLayout stack;
    private Composite inner1;
    private Composite inner2;


    private Text announceText;
    private Composite composite;
    private Composite parent;

    private TableViewer viewer;
    private ScrolledComposite sc;

    // 現在ビューワ上で選択されている行のモデル
    private Player currentSelectedItem;

    public TableViewer getViewer() {
        return viewer;
    }

    /**
     * コンストラクタ
     * @param pageName
     */
    protected WareWolfWizardPage5(String pageName) {
        super(pageName);

        String message = "";
        setDescription(message);

        setPageComplete(false);
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        this.parent = parent;
        composite =  Util.createGridComposite(parent, 0);

        setTitle("ゲーム開始設定(5/5) -- アイテム引き直し");

        stack = new StackLayout();
        composite.setLayout(stack);

        inner1 =  Util.createGridComposite(composite, 0);
        Util.createLabel(inner1, "難易度「いーじぃ」ではアイテム引き直しがありません。「次へ」でゲームを開始してください。") ;

        inner2 =  Util.createGridComposite(composite, 0);
        Util.createLabel(inner2, "①：アイテム引き直しについて、以下の告知を全体に行ってください。") ;

        // ゲーム設定アナウンス
        this.announceText = new Text(inner2,SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
        this.announceText.setLayoutData(Util.createFillBothGridData());
        String announce = "各プレイヤーに配布されたアイテムは、2枚セットで1度のみ引き直すことが出来ます。" + StringUtil.LINE_CODE;
        announce += "引き直しを希望される方は「交換します」と発言してください。新アイテムをskype個別窓にて配布します。";
        this.announceText.setText(announce);

        Util.createEmptyLineLabel(inner2);
        Util.createLabel(inner2, "②：アイテム引き直しを希望したプレイヤーの行を右クリックして「アイテム引き直し実行」メニューを選んでください。") ;

        // 役職・キャラ・アイテム抽選結果確認ビュー
        createViewArea(inner2);

        Util.createEmptyLineLabel(inner2);
        Util.createLabel(inner2, "③：希望者全員への引き直しが完了したら、「次へ」でゲームを開始してください。");


        setPageComplete(true);
        setControl(composite);
    }

    /**
     * アイテム引き直し用ビューを作成する
     * @param inputGroup
     */
    private void createViewArea(Composite inputGroup) {

        sc =  new ScrolledComposite(inputGroup, SWT.H_SCROLL | SWT.V_SCROLL);
        sc.setLayout(new GridLayout());
        sc.setLayoutData(Util.createFillBothGridData());

        viewer = new TableViewer(sc, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
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
        createColumn(table, 1, "キャラクター", (int)(tableFullWidth * 0.22));
        createColumn(table, 2, "役職", (int)(tableFullWidth * 0.13));
        createColumn(table, 3, "アイテム1", (int)(tableFullWidth * 0.18));
        createColumn(table, 4, "アイテム2", (int)(tableFullWidth * 0.18));
        createColumn(table, 5, "アイテム引き直し済", (int)(tableFullWidth * 0.20));

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new WareWolfViewLabelProvider());

        table.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                currentSelectedItem = (Player)((StructuredSelection)viewer.getSelection()).getFirstElement();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // no operation.
            }
        });

        table.setSize(tableFullWidth, 280);
        sc.setContent(table);
        viewer.setInput(viewList);

        // 右クリックメニュー作成
        this.hookContextMenu();
    }


    /**
     * TableViewerにメニューを作成
     */
    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager manager) {
                fillContextMenu(manager);
            }});
        Menu menu = menuMgr.createContextMenu(this.viewer.getControl());
        this.viewer.getControl().setMenu(menu);
    }

    /**
     * TableViewer上で右クリックされたセルに対してメニュー作成アクションを登録する
     * @param manager メニューマネージャ
     */
    private void fillContextMenu(IMenuManager manager) {

        if (!currentSelectedItem.isItemRedraw()) {
            manager.add(new ItemRedrawPopupMenuAction(this, currentSelectedItem, "アイテム引き直し実行", false));
        } else {
            manager.add(new ItemRedrawPopupMenuAction(this, currentSelectedItem, "アイテム引き直しアナウンス文を開く", false));
            manager.add(new ItemRedrawPopupMenuAction(this, currentSelectedItem, "アイテム引き直しをキャンセルする", true));
        }

        // メニューの追加させたい場所にキー値(IWorkbenchActionConstants.MB_ADDITIONS)(実際の文字列はadditions)をセット
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    /**
     * テーブルカラムを作成する
     * ヘッダカラム押下には対応しない
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

        // 前画面までを取得
        wizard = (WareWolfWizard)getWizard();
        wizard.setCurrentPageNum(5);

        if (wizard != null && DifficultyEnum.EASY == wizard.getDifficultyEnum()) {
            stack.topControl = inner1;
            composite.layout();
        } else {
            refreshStack2();
        }

        setPageComplete(true);
        super.setVisible(visible);
    }

    /**
     * stack2の表示をリフレッシュする
     */
    public void refreshStack2() {

      this.composite.dispose();
      this.createControl(this.parent);
      stack.topControl = inner2;
      this.parent.layout();

      createTable(wizard.getRoleList());
    }


}
