package dgrp.warewolf.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import dgrp.Util;
import dgrp.warewolf.WareWolfAnnounceFactory;
import dgrp.warewolf.WareWolfItemFactory;
import dgrp.warewolf.model.Player;
import dgrp.warewolf.wizard.WareWolfWizardPage5;

/**
 * テーブルビューワ上で右クリックされた時のポップアップメニューのアクション
 * @author butsuryo
 *
 */
public class ItemRedrawPopupMenuAction extends Action {

    private WareWolfWizardPage5 page;
    private boolean isCancel;

    /**
     * コンストラクタ
     * メニューを定義する
     *
     * @param page ウィザードページオブジェクト
     * @param currentSelectedItem 現在選択されている行の参加者オブ弱と
     * @param isCancel キャンセルメニューかどうか
     */
    public ItemRedrawPopupMenuAction(WareWolfWizardPage5 page, Player currentSelectedItem, String menuTitle, boolean isCancel){

        this.page = page;
        this.isCancel = isCancel;

        // メニュー名設定
        this.setText(menuTitle);

        // アイコン設定
        if (!currentSelectedItem.isItemRedraw()) {
            this.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
        } else if (!isCancel){
            this.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED_DISABLED));
        } else {
            this.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_REMOVE));
        }

    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.action.Action#run()
     * メニューが選択された時の挙動
     */
    public void run() {

        // view上で選択しているオブジェクトを取得
        TableViewer viewer = page.getViewer();
        Player row = (Player)((StructuredSelection)viewer.getSelection()).getFirstElement();

        // 引き直し未実施の場合は引き直す
        if (!row.isItemRedraw()) {

            // 現在のアイテムを引き直し前アイテムにセット
            row.redrawItems(WareWolfItemFactory.getOneItem(), WareWolfItemFactory.getOneItem());
        }

        // キャンセルの場合はアイテム状況を元に戻す
        if (isCancel) {
            row.redrawItemCancel();

        } else {

            // アナウンス用文を別ダイアログで表示する
            String redrawAnnounce = WareWolfAnnounceFactory.createRedrawItemsAnnounce(row);
            Util.openAnnounceDialog(redrawAnnounce);
        }

        // ビューの表示を更新
        page.refreshStack2();
    }

}