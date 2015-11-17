package dgrp.classtrial.wizard;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import dgrp.Util;

public class ClassTrialDialog extends Dialog {

    private String title;
    private String message;
    private String checkboxMessage;


    /**
     * コンストラクタ
     * @param parentShell
     */
    public ClassTrialDialog(Shell parentShell) {
        super(parentShell);
    }

    /**
     * チェックボックス付きダイアログを表示
     *
     * @param title
     * @param message
     * @param checkboxMessage
     * @return
     */
    public int open(String title, String message, String checkboxMessage) {
        this.title = title;
        this.message = message;
        this.checkboxMessage = checkboxMessage;

        return super.open();
    }


    /**
     * ダイアログ内の部品の初期化
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);

        Label messageLabel = new Label(container, SWT.NONE);
        messageLabel.setText(this.message);

        Button button = new Button(container, SWT.CHECK);
        button.setText(this.checkboxMessage);
        button.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                Button selectedButton = (Button)e.widget;

                // オン・オフを切り替えられたらプリファレンスを更新
                if(selectedButton.getSelection()){
                    Util.setPreference("isOpenCopyDialog", "false");
                } else {
                    Util.setPreference("isOpenCopyDialog", "true");
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // no operation.
            }
        });


        return container;
    }

    /**
     * ダイアログのタイトルを設定
     */
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(this.title);
    }

    @Override
    // キャンセルボタンを表示しないようにオーバーライド
    protected void createButtonsForButtonBar(Composite parent) {
        // OKボタンだけ
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }
}
