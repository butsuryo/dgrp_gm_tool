package dgrp.classtrial.wizard;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import dgrp.StringUtil;
import dgrp.Util;
import dgrp.classtrial.AnnounceFactory;
import dgrp.classtrial.model.Cast;
import dgrp.classtrial.model.Player;
import dgrp.classtrial.model.RoleEnum;


/**
 * ゲーム開始設定のウィザードページ4
 * 探偵とギャンブラーの役職実行
 *
 * @author butsuryo
 *
 */
public class ClassTrialWizardPage4 extends WizardPage {

    private ClassTrialWizard wizard;
    private ArrayList<Player> playerList;
    private Cast cast;
    private boolean isFromOtherPage;

    // 可変表示部品
    private Label detectiveLabel;
    private Label gamblerLabel;
    private Combo detectiveTargetCombo;
    private Combo gamblerTargetCombo;
    private Text detectiveResultLabel;
    private Text gamblerResultLabel;

    // 不変表示部品
    private Button resetButton;
    private Text gamblerText;
    private Composite composite;


    /**
     * コンストラクタ
     * @param pageName
     */
    protected ClassTrialWizardPage4(String pageName) {
        super(pageName);
        setDescription("役職実行時間です。探偵とギャンブラーにそれぞれ以下の案内を行ってください。");
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        composite = Util.createGridComposite(parent, 0);

        setTitle("ゲーム開始(1/4) -- 役職実行");

        // 探偵
        createDetectiveArea();

        // セパレータ
        Util.createEmptyLineLabel(composite);
        Util.createHorizotalLine(composite);
        Util.createEmptyLineLabel(composite);

        // ギャンブラー
        createGamblerArea();

        setControl(composite);
    }

    /**
     * 探偵への案内、探偵役職実行エリアの作成
     */
    private void createDetectiveArea() {

        // 探偵への案内
        detectiveLabel = Util.createEmptyLineLabel(composite);
        Composite detectiveAnnounceGroup = Util.createGridComposite(composite, 2);

        // コピペ用の案内文
        final Text detectiveText = new Text(detectiveAnnounceGroup,SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
        detectiveText.setText("調査対象に選びたいキャラクターを一人選んで下さい。\r\n結果は【クロ】【クロではない】のどちらかでお伝えします。\r\n※あなたの調査後、ギャンブラーが役職を交換することがあります。交換後の役職はあなたにはわかりません。");
        Util.createCopyLink(detectiveAnnounceGroup, detectiveText);

        Util.createEmptyLineLabel(composite);

        // 探偵の役職実行
        Util.createLabel(composite, "②：探偵が指定した調査対象を以下から選び、表示された結果を伝えてください。（探偵が複数人いる場合は繰り返してください）");

        Composite detectiveTalentGroup = Util.createGridComposite(composite, 4);
        Util.createLabel(detectiveTalentGroup, "調査対象：") ;

        // 対象選択コンボ（選択をトリガーに結果表示アクション実行）
        detectiveTargetCombo = new Combo(detectiveTalentGroup, SWT.READ_ONLY);
        detectiveTargetCombo.addModifyListener(e-> viewDetectiveResult());

        // 結果表示ラベル
        detectiveResultLabel = new Text(detectiveTalentGroup, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
        Util.createCopyLink(detectiveTalentGroup, detectiveResultLabel);
    }

    /**
     * ギャンブラーへの案内、ギャンブラー役職実行エリアの作成
     */
    private void createGamblerArea() {

        // ギャンブラーへの案内
        gamblerLabel = Util.createEmptyLineLabel(composite);

        Composite gamblerAnnounceGroup = Util.createGridComposite(composite, 2);

        // コピペ用の案内文
        gamblerText = new Text(gamblerAnnounceGroup,SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
        gamblerText.setText("役職を交換したいキャラクターを一人選んで下さい。\r\n交換を希望しない場合は「交換しない」とお答えください。");
        Util.createCopyLink(gamblerAnnounceGroup, gamblerText);

        Util.createEmptyLineLabel(composite);

        // ギャンブラーの役職実行
        Util.createLabel(composite, "④：ギャンブラーが交換を実行した場合、交換対象を以下から選び、表示された結果を伝えてください。");

        Composite gamblerTalentGroup = Util.createGridComposite(composite, 4);
        Util.createLabel(gamblerTalentGroup, "交換対象：") ;

        // 対象選択コンボ（選択をトリガーに結果表示アクション実行）
        gamblerTargetCombo = new Combo(gamblerTalentGroup, SWT.READ_ONLY);
        gamblerTargetCombo.addModifyListener(e-> tradeRoleAndViewGamplerResult());

        // 結果表示ラベル
        gamblerResultLabel = new Text(gamblerTalentGroup, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
        Util.createCopyLink(gamblerTalentGroup, gamblerResultLabel);

        // 交換やりなおしボタン
        resetButton = new Button(gamblerTalentGroup, SWT.PUSH);
        resetButton.setText("交換をやりなおす");
        resetButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                // ボタンを押されたらダイアログを出してリセット
                boolean isReset = Util.openConfirmDialog("交換結果のやりなおし確認", "ギャンブラーの交換処理をやりなおしますか？");
                if (isReset) {
                    resetTrade();
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // no operation
            }
        });
    }

    /**
     * プレイヤー選択リストに選択肢のプレイヤー名を表示する
     */
    private void setPlayler(){

        // 一旦リセット
        detectiveTargetCombo.removeAll();
        gamblerTargetCombo.removeAll();

        for(Player player : playerList){
            detectiveTargetCombo.add(player.getCharacter().getCharacterName());

            // ギャンブラーは自分と欠席者を除く
            if (!RoleEnum.GAMBLER.equals(player.getDefaultRole()) && !player.isAbsent()) {
                gamblerTargetCombo.add(player.getCharacter().getCharacterName());
            }
        }
        composite.layout();
    }

    /**
     * 探偵の調査結果をラベルに表示する
     */
    private void viewDetectiveResult() {

        // 入力値に変化があれば役職結果を表示
        if (detectiveTargetCombo.getSelectionIndex() >= 0) {

            // キャラクター名から調査対象のオブジェクトを取得
            String characterName = detectiveTargetCombo.getItem(detectiveTargetCombo.getSelectionIndex());
            Player targetModel = cast.getRoleByCharacterName(characterName);

            // 表示用文言を生成してセット
            detectiveResultLabel.setText(AnnounceFactory.getInvestigationResult(targetModel));
            composite.layout();
        }
    }

    /**
     * ギャンブラーの交換結果をラベルに表示し、実際の役職交換を行う。
     */
    private void tradeRoleAndViewGamplerResult() {

        // 入力値に変化があれば交換を実施し、結果を表示
        if (!isFromOtherPage && gamblerTargetCombo.getSelectionIndex() >= 0 ) {

            // キャラクター名から交換相手のオブジェクトを取得
            String targetCharacterName = gamblerTargetCombo.getItem(gamblerTargetCombo.getSelectionIndex());
            Player tradeTargetModel = cast.getRoleByCharacterName(targetCharacterName);

            // 表示用文言を生成してセット
            gamblerResultLabel.setText(AnnounceFactory.getTradeResult(tradeTargetModel));
            composite.layout();

            // 役職交換実行
            cast.tradeGambler(tradeTargetModel);

            // 選択したら、もう再交換できないようにコンボを非活性化しておく
            gamblerTargetCombo.setEnabled(false);
        }
        isFromOtherPage = false;
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    public void setVisible(boolean visible) {

        // ページ遷移を記録
        wizard = (ClassTrialWizard)getWizard();
        wizard.setCurrentPageNum(4);

        // 配役、参加者オブジェクトリストを取得
        cast = wizard.getCast();
        playerList = wizard.getPlayerList();

        if (cast != null && playerList != null) {

            // 探偵のプレイヤー名をセット
            detectiveLabel.setText("①：探偵（" + StringUtil.implode(cast.getPlayerNameList(RoleEnum.DETECTIVE, true), StringUtil.COMMA) + "）に以下の案内を行ってください。\r\n　(欠席者の場合は案内不要です。メタ推理防止のため1分程度おいてから次に進んでください。)");

            // ギャンブラーのプレイヤー名をセット
            Player gambler = cast.getRoleModelList(RoleEnum.GAMBLER, true).get(0);
            if (gambler.isAbsent()) {
                gamblerLabel.setText("③：ギャンブラーが欠席者のため、③・④の案内は不要です。メタ推理防止のため1分程度おいてから次に進んでください。)");
                setGamblerElementsEnabled(false);

            } else {
                gamblerLabel.setText("③：ギャンブラー（" + gambler.getPlayerName() + "）に以下の案内を行ってください。");
                setGamblerElementsEnabled(true);
            }
            composite.layout();

            // 探偵・ギャンブラー役職実行用のコンボボックスの中身をセット
            setPlayler();
        }

        // 後ろのもしくは前のページから戻ってきたら交換選択値を復元
        if (fromOtherPage()) {

            // 前回交換対象を復元
            if (cast.isTraded()) {
                isFromOtherPage = true;
                ArrayList<String> tradedCharacter = cast.getCharacterNameList(RoleEnum.GAMBLER, false);
                gamblerTargetCombo.setText(tradedCharacter.get(0));
                gamblerTargetCombo.setEnabled(false);
            } else {
                resetTrade();
            }
        }

        setPageComplete(true);
        super.setVisible(visible);
    }

    /**
     * ギャンブラーの交換結果を初期化する。
     * 非活性化していた選択コンボを選択可能にする。
     */
    private void resetTrade() {

        // 交換対象選択コンボを再活性化
        gamblerTargetCombo.setEnabled(true);

        // 交換結果ラベルを初期化
        gamblerResultLabel.setText(StringUtil.EMPTY);

        // リストを交換前に戻す
        cast.resetTrade();
        playerList = wizard.getPlayerList();
    }

    /**
     * ギャンブラー案内関連の部品全てをまとめて活性制御する
     * @param isEnabled true:活性化　false:非活性化
     */
    private void setGamblerElementsEnabled(boolean isEnabled) {

        // 案内文
        gamblerText.setEnabled(isEnabled);
        // 対象選択コンボ
        gamblerTargetCombo.setEnabled(isEnabled);
        // やり直しボタン
        resetButton.setEnabled(isEnabled);
        // 結果ラベル
        gamblerResultLabel.setEnabled(isEnabled);
    }

    /**
     * 前後のページから来たばかりかどうか
     * @return 前後のページから来たばかりかどうか
     */
    private boolean fromOtherPage() {
        return wizard.getBeforePageNum() == 3 ||  wizard.getBeforePageNum() == 5;
    }

}
