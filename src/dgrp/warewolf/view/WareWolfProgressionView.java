package dgrp.warewolf.view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

import dgrp.Activator;
import dgrp.StringUtil;
import dgrp.Util;
import dgrp.warewolf.WareWolfAnnounceFactory;
import dgrp.warewolf.model.DifficultyEnum;
import dgrp.warewolf.model.GameData;
import dgrp.warewolf.model.Player;
import dgrp.warewolf.model.RoleEnum;

public class WareWolfProgressionView extends ViewPart {
    public static final String ID = "progressionview";

    private Composite parent;
    private Composite composite;
    private StackLayout stack;
    private Composite innerNone;
    private Composite innerLateNight;
    private Composite innerMorning;

    private GameData gameData;

    private Combo attackTargetCombo;
    private Combo egoTargetCombo;
    private Combo monomiTargetCombo;
    private Combo attackLostItemCombo;

    private Button nextBtn;


    /**
     * This is a callback that will allow us to create the viewer and initialize it.
     */
    public void createPartControl(Composite parent) {

        this.parent = parent;
        this.composite = Util.createGridComposite(parent, 0);

        // ゲームデータ
        gameData = Activator.getGameData();

        // 基盤のformを作成
        FormToolkit toolkit = new FormToolkit(composite.getDisplay());
        Form form = toolkit.createForm(composite);
        form.setLayoutData(Util.createFillBothGridData());

        // stackを管理する全体のcompositeを作成
        Composite body = form.getBody();
        stack = new StackLayout();
        body.setLayout(stack);


        // ゲームデータがまだない時用
        innerNone = Util.createGridComposite(body, 0);
        toolkit.createLabel(innerNone, "ゲームデータがありません。");

        // ゲームデータがあるときは時間帯で表示を切り替える
        createStack(body, toolkit);

    }

    private void createStack(Composite body, FormToolkit toolkit) {

        // ゲームデータが無い場合は処理しない（前回ゲームのデータを開きっぱなしにしていたケースなど）
        if (gameData == null) {
            return;
        }

        // 深夜用
        createLateNightStack(body, toolkit);

        // 朝用
        createMorningStack(body, toolkit);

    }


    /**
     * 時間帯「深夜」の時に表示する内容
     * @param body 親composite
     * @param toolkit
     */
    private void createLateNightStack(Composite body, FormToolkit toolkit) {

        innerLateNight = Util.createGridComposite(body, 0);
        toolkit.createLabel(innerLateNight, "深夜");

        ArrayList<Player> list = gameData.getList();
        int day = gameData.getDay();

        // ①や②などの案内番号
        int infoNum = 1;

        // 初日深夜以外
        if (day != 0) { // TODO debug

            // 能力アナウンス（使われている場合のみ）
            if (DifficultyEnum.EXTREAM.equals(gameData.getDifficulty())) {

            }

            // アイテムカードアナウンス（使われている場合のみ）
            if (!DifficultyEnum.EASY.equals(gameData.getDifficulty())) {

            }

            // エゴ調査アナウンス（生存している場合のみ）
            if (!gameData.isRoleDead(RoleEnum.EGO)) {
                toolkit.createLabel(innerLateNight, WareWolfAnnounceFactory.createRoleAnnounceInfo(list, RoleEnum.EGO));
                String egoAnnounce = StringUtil.getNumberWithCircle(infoNum++) + "：調査先を選び、GMに伝えてください。（自分を調査することはできません。）" + StringUtil.LINE_CODE;
                toolkit.createText(innerLateNight, egoAnnounce, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);

                // 調査対象選択
                toolkit.createLabel(innerLateNight, StringUtil.getNumberWithCircle(infoNum++) + "：調査対象を選択してください。");
                // ドロップダウン(入力不可)
                egoTargetCombo = new Combo(innerLateNight, SWT.READ_ONLY|SWT.FLAT|SWT.DROP_DOWN);
                // リスト中身のセット
                setTargetListWithoutMyself(egoTargetCombo, list, gameData.getModelByRole(RoleEnum.EGO));
                // リスナーセット
                attackTargetCombo.addSelectionListener(new ProgressViewElemSelectionListener(this));


            // 生存していない場合はスキップ
            } else {
                toolkit.createLabel(innerLateNight, "アルターエゴは死亡済みのため、対象者へのアナウンスはスキップしてください。");
            }

            // モノミ護衛アナウンス（いーじぃ以外でいる時だけ）
            if (!DifficultyEnum.EASY.equals(gameData.getDifficulty())) {

                // 配役にいるときだけ案内
                if (gameData.getCast().isPossibilityMonomi()) {

                    // 欠けてない
                    if (gameData.getModelByRole(RoleEnum.MONOMI) != null) {

                        // 生きてる
                        if (!gameData.isRoleDead(RoleEnum.MONOMI)) {

                            toolkit.createLabel(innerLateNight, WareWolfAnnounceFactory.createRoleAnnounceInfo(list, RoleEnum.MONOMI));
                            String monomiAnnounce = StringUtil.getNumberWithCircle(infoNum++) + "：護衛先を選び、GMに伝えてください。（自分を護衛することはできません。）" + StringUtil.LINE_CODE;
                            toolkit.createText(innerLateNight, monomiAnnounce, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);

                            // 護衛対象選択
                            toolkit.createLabel(innerLateNight, StringUtil.getNumberWithCircle(infoNum++) + "：護衛対象を選択してください。");
                            // ドロップダウン(入力不可)
                            monomiTargetCombo = new Combo(innerLateNight, SWT.READ_ONLY|SWT.FLAT|SWT.DROP_DOWN);
                            // リスト中身のセット
                            setTargetListWithoutMyself(monomiTargetCombo, list, gameData.getModelByRole(RoleEnum.MONOMI));

                        // し、死んでるー！
                        } else {
                            toolkit.createLabel(innerLateNight, "モノミは死亡済みのため、対象者へのアナウンスはスキップしてください。");
                        }

                    // 欠けてる
                    } else {
                        toolkit.createLabel(innerLateNight, "モノミは欠けているため、対象者へのアナウンスはスキップしてください。");
                    }
                }
            }
        }

        // 襲撃アナウンス（初日の場合は少し文面が違う）
        toolkit.createLabel(innerLateNight,StringUtil.getNumberWithCircle(infoNum++) +WareWolfAnnounceFactory.createRoleAnnounceInfo(list, RoleEnum.BLACK));
        String attackAnnounce = WareWolfAnnounceFactory.createAttackAnnounce(list, day);
        toolkit.createText(innerLateNight, attackAnnounce, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);

        // 襲撃対象選択
        toolkit.createLabel(innerLateNight, StringUtil.getNumberWithCircle(infoNum++) + "：襲撃対象を選択してください。");
        // ドロップダウン(入力不可)
        attackTargetCombo = new Combo(innerLateNight, SWT.READ_ONLY|SWT.FLAT|SWT.DROP_DOWN);
        // リスト中身のセット
        setAttackTargetList(list);
        // 襲撃対象選択コンボにリスナーセット
        attackTargetCombo.addSelectionListener(new ProgressViewElemSelectionListener(this));

        // 次の時間帯へボタン
        final Button nextBtn = createNextButton(innerLateNight, toolkit);
        nextBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, true));
        nextBtn.setEnabled(false);  // 初期状態は非活性

    }

    /**
     * 襲撃対象選択リストに選択肢を表示する
     * @param list 参加者オブジェクトのリスト
     */
    private void setAttackTargetList(ArrayList<Player> list){

        // 最初に空をセット
        attackTargetCombo.add("");

        for(Player model : list){

            // 死んでるキャラは表示しない
            if (!model.isDead()) {
                attackTargetCombo.add(model.getCharacter().getCharacterName());
            }
        }

        attackTargetCombo.select(0); // 最初のアイテム(空)を選択状態にする
    }

    /**
     * 対象選択リストに選択肢を表示する（自分以外・死んだ人以外）
     * @param list 参加者オブジェクトのリスト
     */
    private void setTargetListWithoutMyself(Combo combo, ArrayList<Player> list, Player myself){

        // 最初に空をセット
        combo.add("");

        for(Player model : list){

            // 死んでるキャラ、自分自身は選択肢に表示しない
            if (!model.isDead() && !myself.equals(model)) {
                combo.add(model.getCharacter().getCharacterName());
            }
            combo.select(0); // 最初のアイテム(空)を選択状態にする
        }
    }

    /**
     * 時間帯「朝」の時に表示する内容
     * @param body 親composite
     * @param toolkit
     */
    private void createMorningStack(Composite body, FormToolkit toolkit) {

        // ①や②などの案内番号
        int infoNum = 1;

        innerMorning = Util.createGridComposite(body, 0);
        toolkit.createLabel(innerMorning, "朝");

        toolkit.createLabel(innerMorning,StringUtil.getNumberWithCircle(infoNum++) + "：朝のアナウンスを行ってください。");
        String morningAnnounce = "おはようございます。朝になりました。" + StringUtil.LINE_CODE;

        // 死体がある
        if (gameData.getVictim() != null) {
            createExistsVictimMorningArea(morningAnnounce, toolkit);

        // ない
        } else {
            createNonVictimMorningArea(morningAnnounce, toolkit);
        }

        // 次の時間帯へボタン
        createNextButton(innerMorning, toolkit);
    }

    /**
     * 死体があった朝の表示
     * @param announce アナウンス文
     * @param toolkit
     */
    private void createExistsVictimMorningArea(String announce, FormToolkit toolkit) {

        // モノミ爆死なら
        if (gameData.isMonomiGuard()) {
            announce += "昨夜、" + gameData.getAttackTarget() + "さんが襲撃されましたが、モノミの護衛により無事でした。" + StringUtil.LINE_CODE;
            announce += "モノミが身代わりになって爆死したため、ロールオープンされます。【モノミ】は" + gameData.getCharaNameByRole(RoleEnum.MONOMI) + "さんでした。" + StringUtil.LINE_CODE;
            announce += "モノミの爆死はいかなる能力・カードでも救うことはできません。" + StringUtil.LINE_CODE;
            announce += gameData.getCharaNameByRole(RoleEnum.MONOMI) + "さんは死体となり、以後ゲームに参加することはできません。";
            // TODO 塩とかも使えないの？

            toolkit.createText(innerMorning, announce, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
        } else {

            String target = gameData.getAttackTarget().getCharacter().getCharacterName();

            announce += "昨夜、" + target + "さんが襲撃され、虫の息で発見されました。" + StringUtil.LINE_CODE;

            // TODO 処理の順番
            announce += target + "さん、自分自身で襲撃を無効化することはできますか？" + StringUtil.LINE_CODE;

            // TODO あれば能力・カード処理

            announce += "では他の方の中に、能力で" + target + "さんの襲撃を無効化できる方はいらっしゃいますか？" + StringUtil.LINE_CODE;

            // TODO あれば能力処理
            // TODO プレゼントは両隣が死人だとどうなる？飛ばす？それとももらえない？
            announce += "では両隣の○○さん、" + target  + "さんの襲撃を無効化できるカードのプレゼントはありますか？" + StringUtil.LINE_CODE;

            // TODO あればカード処理

            announce += "では" + target + "さん、両隣の○○さん、襲撃死後使えるカードのプレゼントはありますか？"  + StringUtil.LINE_CODE;
            announce += target + "さんは死体となり、以後ゲームに参加することはできません。";

            // 死亡確定後、クロかエゴならロールオープン
            if (RoleEnum.BLACK.equals(gameData.getAttackTarget().getRole()) || RoleEnum.EGO.equals(gameData.getAttackTarget().getRole())) {

                announce += "ここで" + target + "さんの死亡に際するロールオープンがあります。" + StringUtil.LINE_CODE;
                announce += target + "さんの役職は" + StringUtil.createBigParenthesesStr(gameData.getAttackTarget().getRole().roleName) + "でした。" + StringUtil.LINE_CODE;

            } else {
                announce += "なお、" + target + "さんの死亡に際するロールオープンはありませんでした。";
            }
        }
    }

    /**
     * 死体がない朝の表示
     * @param announce アナウンス文
     * @param toolkit
     */
    private void createNonVictimMorningArea(String announce, FormToolkit toolkit) {

        // 初日だけアナウンスが異なる
        if (gameData.getDay() == 1) {

            // アイテムロストアナウンス
            announce += "昨夜、" + gameData.getAttackTarget() + "さんが襲撃されました。" + StringUtil.LINE_CODE;
            announce += "命に別状はなかったようですが、アイテムを一枚失ってしまったようです。" + StringUtil.LINE_CODE;
            announce += gameData.getAttackTarget() + "さんは捨てるアイテムを一枚選んでください。" + StringUtil.LINE_CODE;
            toolkit.createText(innerMorning, announce, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);

            // ロストアイテム選択コンボ
            attackLostItemCombo = new Combo(innerMorning, SWT.READ_ONLY|SWT.FLAT|SWT.DROP_DOWN);
            // リスト中身のセット
            setAttackLostItemList();
            // リスナーセット
            attackLostItemCombo.addSelectionListener(new ProgressViewElemSelectionListener(this));

        // 犠牲者なしの理由はロボ・黄金銃・幸運組のみ
        } else {
            announce += "昨夜、" + gameData.getAttackTarget() + "さんが襲撃されましたが、" + gameData.getInvalidAttackReason() + "の効果より無事でした。";
            toolkit.createText(innerMorning, announce, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
        }
    }

    /**
     * アイテム選択者コンボボックスを作成する
     * @param composite 親composite
     * @param combo 対になるアイテム選択コンボボックス
     * @return 作成したコンボボックス
     */
    private Combo createUseItemCharacterCombo(Composite composite, final Combo itemCombo) {

        final Combo combo = new Combo(composite, SWT.READ_ONLY|SWT.FLAT|SWT.DROP_DOWN);

        combo.add("");

        // 全員の名前をセット（TODO 死者は除いたりする？）
        for (Player model : gameData.getList()) {
            combo.add(model.getCharacter().getCharacterName());
        }

        combo.select(0);

        combo.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (combo.getSelectionIndex() != 0) {
                    setUseItemCombo(combo.getText(), itemCombo);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // no operation.
            }
        });

        return combo;
    }

    /**
     * 指定したキャラクターの全アイテムを、アイテム選択コンボの選択肢に追加する
     * @param useCharacterName キャラクター名
     * @param combo アイテム選択コンボボックス
     */
    private void setUseItemCombo(String useCharacterName, Combo combo) {

        combo.add("");

        Player model = gameData.getModelByCharaName(useCharacterName);

        if (model.getItem1() != null) {
            combo.add(model.getItem1().getItemName());
        }

        if (model.getItem2() != null) {
            combo.add(model.getItem2().getItemName());
        }

        combo.select(0);
    }


    /**
     * アイテム選択コンボボックスを作成する
     * @param composite 親composite
     * @return
     * @return 作成したコンボボックス
     */
    private Combo createUseItemCombo(Composite composite) {

        final Combo combo = new Combo(composite, SWT.READ_ONLY|SWT.FLAT|SWT.DROP_DOWN);
        return combo;
    }

    /**
     * 初日襲撃の捨てアイテム選択コンボの選択肢を作成する
     */
    private void setAttackLostItemList() {

        Player attackTarget = gameData.getAttackTarget();

        // 最初に空をセット
        attackLostItemCombo.add("");

        // アイテム2枚
        attackLostItemCombo.add(attackTarget.getItem1().getItemName());
        attackLostItemCombo.add(attackTarget.getItem2().getItemName());

        attackLostItemCombo.select(0); // 最初のアイテム(空)を選択状態にする
    }

    /**
     * 「次の時間帯へ進む」ボタンを作成する
     * 押下された場合、次の時間帯へ切り替えてビューを更新する
     *
     * @param inner ボタンを作成する親composite
     * @param toolkit
     * @return nextBtn 作成したボタン
     */
    private Button createNextButton(Composite inner, FormToolkit toolkit) {

        nextBtn = toolkit.createButton(inner, "次の時間帯へ進む", SWT.PUSH);
        nextBtn.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                // それぞれの選択値をゲームデータにセット
                setGameData();

                // 次の時間帯に進み、表示を切り替えてビューを更新する
                gameData.nextTimeZone();
                changeStackTop();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // no operation
            }
        });

        return nextBtn;
    }

    /**
     * コンボで選択された処理をゲームデータに反映させる
     */
    private void setGameData() {

        switch (Activator.getGameData().getCurrentTimeZone()) {
            case BRIEFING:
                break;
            case DAY:
                break;

            // 能力、カード、エゴ、モノミ、クロ
            case LATE_NIGHT:

                // 能力
                // カード

                // エゴ
                if (egoTargetCombo != null) {
                    String characterName = egoTargetCombo.getText();
                    gameData.setEgoTarget(gameData.getModelByCharaName(characterName));
                }

                // モノミ
                if (monomiTargetCombo != null) {
                    String characterName = monomiTargetCombo.getText();
                    gameData.setMonomiTarget(gameData.getModelByCharaName(characterName));
                }

                // 襲撃
                if (attackTargetCombo != null) {
                    String characterName = attackTargetCombo.getText();
                    gameData.setAttackTarget(gameData.getModelByCharaName(characterName));
                }
                break;

            case MORNING:

                // 使用されたカードの減算処理を行う

                // 死亡確定者の情報をセットする

                // クロが死んだ場合、ゲーム終了処理を行う

                break;

            case NIGHT:
                break;
            case TRIAL:
                break;
            case VOTE:
                break;
            default:
                break;
        }

    }

    /**
     * 現在の時間帯の操作が完了したかどうか判定し、完了なら「次の時間帯へ」ボタンを活性化、未完了なら非活性化する
     */
    public void judgeComplete() {

        boolean isComplete = true;

        switch (Activator.getGameData().getCurrentTimeZone()) {
            case BRIEFING:
                break;
            case DAY:
                break;

            // 能力、カード、エゴ、モノミ、クロ
            case LATE_NIGHT:

                // 能力
                // カード

                // エゴ
                if (egoTargetCombo != null && egoTargetCombo.getSelectionIndex() == 0) {
                    isComplete = false;
                }

                // モノミ
                if (monomiTargetCombo != null && monomiTargetCombo.getSelectionIndex() == 0) {
                    isComplete = false;
                }

                // 襲撃
                if (attackTargetCombo != null && attackTargetCombo.getSelectionIndex() == 0) {
                    isComplete = false;
                }
                break;

            case MORNING:

                // 初日はアイテムロストコンボの選択が必須
                if (gameData.getDay() == 1) {

                    if (attackLostItemCombo != null && attackLostItemCombo.getSelectionIndex() == 0) {
                        isComplete = false;
                    }

                // それ以外の日
                } else {

                    // 犠牲者アリの時だけ○○が必須
                    if (gameData.getVictim() != null) {

                    }
                }

                break;

            case NIGHT:
                break;
            case TRIAL:
                break;
            case VOTE:
                break;
            default:
                break;
        }

        // 次へボタンの活性化制御
        nextBtn.setEnabled(isComplete);
    }

    /**
     * 進行ビューの表示を現在の時間帯に基づいて切り替える
     */
    private void changeStackTop() {

        // 一度現在の表示を破棄し、作り直す
        this.composite.dispose();
        this.createPartControl(this.parent);

        // ゲームデータがないとき
        if (Activator.getGameData() == null) {
            stack.topControl = innerNone;

        // あるときは時間帯によって表示を切り替える
        } else {
            switch (Activator.getGameData().getCurrentTimeZone()) {
                case BRIEFING:
                    break;
                case DAY:
                    break;
                case LATE_NIGHT:
                    stack.topControl = innerLateNight;
                    break;

                case MORNING:
                    stack.topControl = innerLateNight;
                    break;

                case NIGHT:
                    break;
                case TRIAL:
                    break;
                case VOTE:
                    break;
                default:
                    break;
            }
        }
        this.parent.layout();
    }

    private void updateView() {

        // 画面の更新
        this.composite.dispose();
        this.createPartControl(this.parent);
        this.parent.layout();
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
        this.changeStackTop();
    }


}