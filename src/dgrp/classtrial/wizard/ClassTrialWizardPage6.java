package dgrp.classtrial.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import dgrp.Util;
import dgrp.classtrial.view.CountDownPanelContainer;



/**
 * ゲーム開始設定のウィザードページ6
 * ゲーム中のアナウンス文表示
 *
 * @author butsuryo
 *
 */
public class ClassTrialWizardPage6 extends WizardPage {


    /**
     * コンストラクタ
     * @param pageName
     */
    protected ClassTrialWizardPage6(String pageName) {
        super(pageName);
        setDescription("ゲーム開始です。進行状況に応じて以下のアナウンスを投げてください。");
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        final Composite composite = Util.createGridComposite(parent, 0);

        setTitle("ゲーム開始(3/4) -- ゲーム進行");

        // 議論時間
        Composite group1 = Util.createGridComposite(composite, 3);
        Util.createLabel(group1, "①：議論時間を入力してください。⇒");
        final Text trialMinuteText = new Text(group1, SWT.BORDER);
        trialMinuteText.setText(String.valueOf(3)); // default
        Util.createLabel(group1, "分");

        Util.createHorizotalLine(composite);

        // アナウンス
        Composite desc = createDescription(composite, "②：議論開始アナウンスをチャットに流し、タイマーボタンを押して開始してください。");
        Composite inner = Util.createGridComposite(desc, 2);
        final Text announceStartText = createCopyLinkText(inner, "皆様お揃いでしょうか。\r\nそれでは議論を開始してください。議論時間は" + trialMinuteText.getText() + "分です。");

        // タイマー
        Button btn = new Button(inner, SWT.PUSH);
        btn.setText("タイマー開始");
        btn.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                int minute = Integer.parseInt(trialMinuteText.getText());
                new CountDownPanelContainer(minute * 60);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // no operation
            }
        });

        Util.createHorizotalLine(composite);

        // 残り1分アナウンス
        Composite desc2 = createDescription(composite, "③：タイマーが残り1分になったら、残り議論時間1分アナウンスをチャットに流してください。");
        Composite inner2 = Util.createGridComposite(desc2, 2);
        createCopyLinkText(inner2, "残り1分です。");

        Util.createHorizotalLine(composite);

        // 議論終了アナウンス
        Composite desc3 = createDescription(composite, "④：タイマーが0になったら、議論終了アナウンスをチャットに流してください。");
        Composite inner3 = Util.createGridComposite(desc3, 2);
        createCopyLinkText(inner3, "議論の時間が終了になりました。以降の発言はお控えください。");

        Util.createHorizotalLine(composite);

        // 投票案内アナウンス
        Composite desc4 = createDescription(composite, "⑤：投票についての説明アナウンスをチャットに流してください。");
        Composite inner4 = Util.createGridComposite(desc4, 2);
        createCopyLinkText(inner4, "投票時間に移ります。\r\n投票したい人の名前をチャット欄に入力してお待ちください。\r\n3,2,1,「どうぞ」のタイミングでEnterキーを押し、発言をお願いします。");
        createCopyLinkText(inner4, "それでは参ります。");
        Util.createLabel(inner4, "3秒置きほどの間隔で「3」｢2｣「1」「どうぞ」をアナウンスする。");

        // 議論時間が変更された時、アナウンスの文言も動的に変更する
        trialMinuteText.addModifyListener(e->{
            announceStartText.setText("皆様お揃いでしょうか。\r\nそれでは議論を開始してください。議論時間は" + trialMinuteText.getText() + "分です。");
            composite.layout();
        });

        setControl(composite);
    }

    /**
     * テキスト部品と、テキスト内の文字列をクリップボードにコピーするリンクを生成し、テキスト部品のみ返す
     * @param inner 親コンポジット
     * @param label テキストに表示する文字列
     * @return テキスト部品
     */
    private Text createCopyLinkText(Composite inner, String label) {
      final Text text = new Text(inner, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
      text.setText(label);
      Util.createCopyLink(inner, text);
      return text;
    }

    /**
     * 親コンポジットと、それに乗る説明ラベルをセットで作成し、親コンポジットを返す
     * @param composite 親コンポジット
     * @param description 表示する説明文字列
     * @return 親コンポジット
     */
    private Composite createDescription(Composite composite, String description) {
        Composite descriptionComposite = Util.createGridComposite(composite, 0);
        Util.createLabel(descriptionComposite, description);
        return descriptionComposite;
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    public void setVisible(boolean visible) {

        // ページ遷移を記録
        ((ClassTrialWizard)getWizard()).setCurrentPageNum(6);

        setPageComplete(true);
        super.setVisible(visible);
    }

}
