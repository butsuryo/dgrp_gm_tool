//package dgrp.warewolf.wizard;
//
//import java.util.ArrayList;
//
//import org.eclipse.jface.wizard.WizardPage;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.ModifyEvent;
//import org.eclipse.swt.events.ModifyListener;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.events.SelectionListener;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Combo;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Text;
//
//import dgrp.StringUtil;
//import dgrp.Util;
//import dgrp.classtrial.model.ClassTrialCastModel;
//import dgrp.classtrial.model.ClassTrialRoleBaceModel;
//import dgrp.classtrial.model.ClassTrialRoleEnum;
//import dgrp.classtrial.wizard.ClassTrialWizard;
//
//
///**
// * ゲーム開始設定のウィザードページ4
// * 探偵とギャンブラーの役職実行
// *
// * @author butsuryo
// *
// */
//public class WareWolfWizardPage6 extends WizardPage {
//
//    private WareWolfWizard wizard;
//    private ArrayList<ClassTrialRoleBaceModel> roleList;
//
//    // 可変表示部品
//    private Label detectiveLabel;
//    private Label gamblerLabel;
//    private Combo detectiveTargetCombo;
//    private Combo gamblerTargetCombo;
//    private Text detectiveResultLabel;
//    private Text gamblerResultLabel;
//
//    private Button resetButton;
//    private Text gamblerText;
//
//    private Composite composite;
//
//    /**
//     * コンストラクタ
//     * @param pageName
//     */
//    protected WareWolfWizardPage6(String pageName) {
//        super(pageName);
//        setDescription("役職実行時間です。以下の順番で各役職に案内を行ってください。");
//    }
//
//
//    /* (非 Javadoc)
//     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
//     */
//    @Override
//    public void createControl(Composite parent) {
//        this.composite = Util.createGridComposite(parent, 0);
//
//        setTitle("ゲーム開始(1/?) -- 役職実行");
//
//        // 探偵
//        this.createDetectiveArea();
//
//        // セパレータ
//        Util.createEmptyLineLabel(this.composite);
//        Util.createHorizotalLine(this.composite);
//        Util.createEmptyLineLabel(this.composite);
//
//        // ギャンブラー
//        this.createGamblerArea();
//
//        setControl(this.composite);
//    }
//
//    /**
//     * 探偵への案内、探偵役職実行エリアの作成
//     */
//    private void createDetectiveArea() {
//
//        // 探偵への案内
//        this.detectiveLabel = Util.createEmptyLineLabel(this.composite);
//        Composite detectiveAnnounceGroup = Util.createGridComposite(this.composite, 2);
//
//        // コピペ用の案内文
//        final Text detectiveText = new Text(detectiveAnnounceGroup,SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
//        detectiveText.setText("調査対象に選びたいキャラクターを一人選んで下さい。\r\n結果は【クロ】【クロではない】のどちらかでお伝えします。\r\n※あなたの調査後、ギャンブラーが役職を交換することがあります。交換後の役職はあなたにはわかりません。");
//        Util.createCopyLink(detectiveAnnounceGroup, detectiveText);
//
//        Util.createEmptyLineLabel(this.composite);
//
//
//        // 探偵の役職実行
//        Util.createLabel(this.composite, "②：探偵が指定した調査対象を以下から選び、表示された結果を伝えてください。（探偵が複数人いる場合は繰り返してください）");
//
//        Composite detectiveTalentGroup = Util.createGridComposite(this.composite, 4);
//        Util.createLabel(detectiveTalentGroup, "調査対象：") ;
//
//        // 調査対象選択ドロップダウン(入力不可)
//        this.detectiveTargetCombo = new Combo(detectiveTalentGroup, SWT.READ_ONLY);
//        this.detectiveTargetCombo.addModifyListener(new ModifyListener() {
//
//            @Override
//            public void modifyText(ModifyEvent e) {
//
//                // 入力値に変化があれば役職結果を表示
//                if (detectiveTargetCombo.getSelectionIndex() >= 0) {
//                    viewDetectiveResult();
//                }
//            }
//        });
//
//        // 調査結果表示
//        this.detectiveResultLabel = new Text(detectiveTalentGroup, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
//        Util.createCopyLink(detectiveTalentGroup, this.detectiveResultLabel);
//    }
//
//    /**
//     * ギャンブラーへの案内、ギャンブラー役職実行エリアの作成
//     */
//    private void createGamblerArea() {
//
//        // ギャンブラーへの案内
//        this.gamblerLabel = Util.createEmptyLineLabel(this.composite);
//
//        Composite gamblerAnnounceGroup = Util.createGridComposite(this.composite, 2);
//
//        // コピペ用の案内文
//        this.gamblerText = new Text(gamblerAnnounceGroup,SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
//        this.gamblerText.setText("役職を交換したいキャラクターを一人選んで下さい。\r\n交換を希望しない場合は「交換しない」とお答えください。");
//        Util.createCopyLink(gamblerAnnounceGroup, this.gamblerText);
//
//        Util.createEmptyLineLabel(this.composite);
//
//        // ギャンブラーの役職実行
//        Util.createLabel(this.composite, "④：ギャンブラーが指定した交換対象を以下から選び、表示された結果を伝えてください。");
//
//        Composite gamblerTalentGroup = Util.createGridComposite(this.composite, 4);
//        Util.createLabel(gamblerTalentGroup, "交換対象：") ;
//
//        // 交換対象選択ドロップダウン(入力不可)
//        this.gamblerTargetCombo = new Combo(gamblerTalentGroup, SWT.READ_ONLY);
//        this.gamblerTargetCombo.addModifyListener(new ModifyListener() {
//
//            @Override
//            public void modifyText(ModifyEvent e) {
//
//                // 入力値に変化があれば交換を実施し、結果を表示
//                if (gamblerTargetCombo.getSelectionIndex() >= 0) {
//                    String selectedName = gamblerTargetCombo.getItem(gamblerTargetCombo.getSelectionIndex());
//
//                    // 「誰とも交換しない」の時はなんもしなくていいんじゃないかな
//                    if (!"誰とも交換しない".equals(selectedName)) {
//                        if (Util.openConfirmDialog("確認", "交換先：" + selectedName + "でよろしいですか？")) {
//                            tradeRoleAndViewGamplerResult();
//
//                            // 選択したら、もう再交換できないようにコンボを非活性化しておく
//                            gamblerTargetCombo.setEnabled(false);
//                        }
//                    }
//                }
//            }
//        });
//
//        // 結果表示
//        this.gamblerResultLabel = new Text(gamblerTalentGroup, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
//        Util.createCopyLink(gamblerTalentGroup, this.gamblerResultLabel);
//
//        // 交換やりなおしボタン
//        this.resetButton = new Button(gamblerTalentGroup, SWT.PUSH);
//        this.resetButton.setText("交換をやりなおす");
//        this.resetButton.addSelectionListener(new SelectionListener() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                // ボタンを押されたらダイアログを出してリセット
//                boolean isReset = Util.openConfirmDialog("交換結果のやりなおし確認", "ギャンブラーの交換処理をやりなおしますか？");
//                if (isReset) {
//                    resetTrade();
//                }
//            }
//
//            @Override
//            public void widgetDefaultSelected(SelectionEvent e) {
//                // no operation
//            }
//        });
//    }
//
//    /**
//     * 探偵配役になったプレイヤーの名前をリストで取得する
//     * @return 探偵配役になったプレイヤーの名前のリスト
//     */
//    private ArrayList<String> getDetectivePlayerNameList (){
//        ArrayList<String> detectivePlayerList = new ArrayList<String>();
//
//        for (ClassTrialRoleBaceModel role : this.roleList) {
//            if (ClassTrialRoleEnum.DETECTIVE.equals(role.getCurrentRole())) {
//                detectivePlayerList.add(role.getPlayerName());
//            }
//        }
//        return detectivePlayerList;
//    }
//
//    /**
//     * ギャンブラー配役になったプレイヤーのオブジェクトを取得する
//     * @return ギャンブラー配役になったプレイヤーのオブジェクト
//     */
//    private ClassTrialRoleBaceModel getGamblerModel (){
//        for (ClassTrialRoleBaceModel role : this.roleList) {
//            if (ClassTrialRoleEnum.GAMBLER.equals(role.getCurrentRole())) {
//                return role;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * プレイヤー選択リストに選択肢のプレイヤー名を表示する
//     */
//    private void setPlayler(){
//
//        // 一旦リセット
//        this.detectiveTargetCombo.removeAll();
//        this.gamblerTargetCombo.removeAll();
//
//        for(ClassTrialRoleBaceModel role : this.roleList){
//            this.detectiveTargetCombo.add(role.getCharacter().getCharacterName());
//
//            // ギャンブラーは自分と欠席者を除く
//            if (!ClassTrialRoleEnum.GAMBLER.equals(role.getCurrentRole()) && !role.isAbsent()) {
//                this.gamblerTargetCombo.add(role.getCharacter().getCharacterName());
//            }
//        }
//
//        this.gamblerTargetCombo.add("誰とも交換しない");
//        this.composite.layout();
//    }
//
//    /**
//     * 探偵の調査結果をラベルに表示する
//     */
//    private void viewDetectiveResult() {
//
//        String characterName = this.detectiveTargetCombo.getItem(this.detectiveTargetCombo.getSelectionIndex());
//        String result = characterName + "さんは";
//
//        // キャラクター名から役職を取得
//        ClassTrialRoleBaceModel role = this.getRoleByCharacterName(characterName);
//        if (ClassTrialRoleEnum.BLACK.equals(role.getCurrentRole())) {
//            result += "【クロ】でした。";
//        } else {
//            result += "【クロではありません】でした。";
//        }
//
//        this.detectiveResultLabel.setText(result);
//        this.composite.layout();
//    }
//
//    /**
//     * キャラクター名から参加者オブジェクトを取得する
//     * @param characterName キャラクター名
//     * @return 参加者オブジェクト
//     */
//    private ClassTrialRoleBaceModel getRoleByCharacterName(String characterName) {
//
//        for (ClassTrialRoleBaceModel role : this.roleList) {
//            if (role.getCharacter().getCharacterName().equals(characterName)) {
//                return role;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * ギャンブラーの交換結果をラベルに表示し、実際の役職交換を行う。
//     */
//    private void tradeRoleAndViewGamplerResult() {
//
//        // 交換結果
//        String result = "交換の結果、あなたの役職は";
//        String targetCharacterName = this.gamblerTargetCombo.getItem(this.gamblerTargetCombo.getSelectionIndex());
//
//        // キャラクター名から役職を取得
//        ClassTrialRoleBaceModel tradeTargetModel = this.getRoleByCharacterName(targetCharacterName);
//        ClassTrialRoleEnum tradeTargetRole = tradeTargetModel.getCurrentRole();
//        result += StringUtil.createBigParenthesesStr(tradeTargetRole.roleName) + "に、" + StringUtil.LINE_CODE;
//        result += targetCharacterName + "さんの役職は【ギャンブラー(シロ陣営)】に変更されました。" + StringUtil.LINE_CODE;
//
//        if (ClassTrialRoleEnum.BLACK.equals(tradeTargetRole) || ClassTrialRoleEnum.DESPAIR.equals(tradeTargetRole)) {
//            result += StringUtil.LINE_CODE + "【クロ陣営】勝利条件：\r\n投票の結果、最多得票者が【クロ】以外である。(最多得票が【絶望】でも勝利。)" + StringUtil.LINE_CODE;
//
//            if (ClassTrialRoleEnum.BLACK.equals(tradeTargetRole)) {
//                result += "※役職交換でクロになったギャンブラーは仲間の名前を知ることができません。";
//            }
//
//        } else if (ClassTrialRoleEnum.DETECTIVE.equals(tradeTargetRole)) {
//            result += "※役職交換で探偵になったギャンブラーは、調査(役職実行)を行うことができません。";
//        }
//
//        this.gamblerResultLabel.setText(result);
//        this.composite.layout();
//
//
//        // 役職交換
//        for (ClassTrialRoleBaceModel beforeRole : this.roleList) {
//
//            // ギャンブラーのオブジェクトのRoleを交換先に変更
//            if (ClassTrialRoleEnum.GAMBLER.equals(beforeRole.getCurrentRole())) {
//                beforeRole.setAfterTradeRole(tradeTargetRole);
//            }
//        }
//
//        for (ClassTrialRoleBaceModel beforeRole : this.roleList) {
//
//            // 交換先のオブジェクトのRoleをギャンブラーに変更
//            if (tradeTargetModel.equals(beforeRole)) {
//                beforeRole.setAfterTradeRole(ClassTrialRoleEnum.GAMBLER);
//            }
//        }
//
//        // Wizardで保持しているroleListの値も更新
//        this.wizard.setRoleList(this.roleList);
//    }
//
//
//    /* (非 Javadoc)
//     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
//     * ページがアクティブになった時に通る
//     */
//    public void setVisible(boolean visible) {
//
//        // ページ遷移を記録
//        this.wizard = (ClassTrialWizard)getWizard();
//        this.wizard.setCurrentPageNum(4);
//
//        // 配役、参加者オブジェクトリストを取得
//        ClassTrialCastModel cast = this.wizard.getCast();
//        this.roleList = this.wizard.getRoleList();
//
//        if (cast !=null && this.roleList != null) {
//
//            // 探偵のプレイヤー名をセット
//            this.detectiveLabel.setText("①：探偵（" + StringUtil.implode(this.getDetectivePlayerNameList(), StringUtil.COMMA) + "）に以下の案内を行ってください。\r\n　(欠席者の場合は案内不要です。メタ推理防止のため1分程度おいてから次に進んでください。)");
//
//            // ギャンブラーのプレイヤー名をセット
//            if (this.getGamblerModel().isAbsent()) {
//                this.gamblerLabel.setText("③：ギャンブラーが欠席者のため、③・④の案内は不要です。メタ推理防止のため1分程度おいてから次に進んでください。)");
//                this.setGamblerElementsEnabled(false);
//
//            } else {
//                this.gamblerLabel.setText("③：ギャンブラー（" + this.getGamblerModel().getPlayerName() + "）に以下の案内を行ってください。");
//                this.setGamblerElementsEnabled(true);
//            }
//            this.composite.layout();
//
//            // 探偵・ギャンブラー役職実行用のコンボボックスの中身をセット
//            this.setPlayler();
//        }
//
//        // 後ろのページから戻ってきたら確認ダイアログを表示
//        if (this.wizard.getBeforePageNum() == 5) {
//
//            // 交換されている場合のみダイアログを表示
//            if (this.wizard.isTraded()) {
//                boolean isReset = Util.openConfirmDialog("【交換】初期化確認", "役職実行画面に戻ります。ギャンブラーの交換処理をやり直しますか？");
//
//                // やり直す場合
//                if (isReset) {
//                    this.resetTrade();
//                }
//
//            }
//        }
//
//        setPageComplete(true);
//        super.setVisible(visible);
//    }
//
//    /**
//     * ギャンブラーの交換結果を初期化する。
//     * 非活性化していた選択コンボを選択可能にする。
//     */
//    private void resetTrade() {
//
//
//        // 交換対象選択コンボを再活性化
//        this.gamblerTargetCombo.setEnabled(true);
//
//        // 交換結果ラベルを初期化
//        this.gamblerResultLabel.setText(StringUtil.EMPTY);
//
//        // リストを交換前に戻す
//        this.wizard.resetTrade();
//        this.roleList = this.wizard.getRoleList();
//    }
//
//    /**
//     * ギャンブラー案内関連の部品の活性制御をする
//     * @param isEnabled true:活性化　false:非活性化
//     */
//    private void setGamblerElementsEnabled(boolean isEnabled) {
//
//        // 案内文
//        this.gamblerText.setEnabled(isEnabled);
//        // 対象選択コンボ
//        this.gamblerTargetCombo.setEnabled(isEnabled);
//        // やり直しボタン
//        this.resetButton.setEnabled(isEnabled);
//        // 結果ラベル
//        this.gamblerResultLabel.setEnabled(isEnabled);
//    }
//
//}
