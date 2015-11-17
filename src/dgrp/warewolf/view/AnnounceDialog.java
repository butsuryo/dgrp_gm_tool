package dgrp.warewolf.view;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dgrp.Util;

public class AnnounceDialog extends Dialog {

    private String announce;
    private static final int COPY_ID = -1;

    public AnnounceDialog(Shell parentShell, String announce) {
        super(parentShell);
        this.announce = announce;
    }

    // ボタンの上のエリアの作成
    protected Control createDialogArea(Composite parent) {
        Util.createEmptyLineLabel(parent);
        Util.createLabel(parent, "以下のアナウンス文を対象者に告知してください。");
        Util.createEmptyLineLabel(parent);
        Text text = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        GridData data = new GridData(
                GridData.VERTICAL_ALIGN_FILL |
                GridData.HORIZONTAL_ALIGN_FILL);
        data.widthHint = 300;
        data.heightHint = 200;
        text.setLayoutData(data);
        text.setText(announce);

        return parent;
    }

    // ボタンの作成
    protected void createButtonsForButtonBar(Composite parent) {

        // コピーボタンの作成
        createButton(parent, COPY_ID, "全文コピー", false);

        // OKボタンの作成
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }

    // ボタンが押された際のイベントについてオーバーライド
    protected void buttonPressed(int buttonId) {
        if (COPY_ID == buttonId) {
            // コピー
            Util.copyToClipboad(announce);
        } else {
            // デフォルトのOKもしくはCancelの動作
            super.buttonPressed(buttonId);
        }
    }

}
